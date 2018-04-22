import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * FTPServer class. Has factory init method and shutDown method. Also it is possible
 * to track mistakes, that happened during server work.
 *
 * Server may accept a lot of clients and work with them in distinct thread by {@link FTPSession}
 * class. In fact all possibilities of server are described in {@link FTPSession}.
 *
 * Server will shut down if any error occurred during it's work (not just in one created session).
 **/
@SuppressWarnings("unused")
public class FTPServer {

    @SuppressWarnings("WeakerAccess")
    public final static int DEFAULT_PORT = 9095;

    private final ServerSocket server;
    private final int port;
    private volatile boolean ceaseWorking;
    private volatile boolean serverClosed;

    private final Set<FTPSession> sessions = new TreeSet<>();
    private int sessionsProcessed = 0;
    private final List<IOException> errors = new ArrayList<>();

    private FTPServer(final int port) throws IOException {
        server = new ServerSocket(port);
        this.port = port;
        new Thread(this::work).start();
    }

    private FTPServer() throws IOException {
        this(DEFAULT_PORT);
    }

    private void work() {
        System.out.println("Server was initialized on port " + port);

        // working until error or shutdown
        while (!ceaseWorking) {
            final Socket socket;
            try {
                socket = server.accept();
            } catch (final IOException e) {
                errors.add(e); // We are going to shutdown if any error occurred
                break;
            }
            System.out.println("New client connected on port: " + socket.getPort());
            sessions.add(createNewSession(socket, sessionsProcessed++));
        }
        System.out.println("Server was shut down");

        // closing current connections
        closeSessions();

        // shutting down server
        try {
            server.close();
        } catch (final IOException e) {
            errors.add(e); // store all exceptions
        }

        // Notify called strictly after serverClosed field was set to true,
        // therefore if shutDown method passed throw serverClosed check
        // then this block was not completed yet. This means, that notify will
        // be accepted by shutDown method's thread and we will not get dead lock.
        synchronized (this) {
            serverClosed = true;
            notify();
        }
    }

    private void closeSessions() {
        for (final FTPSession session: sessions) {
            session.close();
        }
    }

    private FTPSession createNewSession(final Socket socket, final int number) {
        final FTPSession session = new FTPSession(socket, number);
        new Thread(session::process).start();
        return session;
    }

    /**
     * The method shuts down server.
     *
     * Server will not accept connections anymore after this method was executed.
     * Every present session will be closed.
     */
    public synchronized void shutDown() {
        if (ceaseWorking || server == null || serverClosed) {
            return;
        }
        ceaseWorking = true;

        while (!serverClosed) {
            try {
                wait();
            } catch (final InterruptedException e) {
                // do nothing
            }
        }
    }

    /**
     * This method checks if any error occurred.
     *
     * @return true if any error occurred during work and false otherwise.
     */
    public boolean anyErrorOccurred() {
        return errors.size() != 0;
    }

    /**
     * Method to track all errors, occurred during work.
     *
     * @return list of occurred errors.
     */
    public List<IOException> getErrors() {
        return errors;
    }

    /**
     * Creates new FTPServer on {@link FTPServer#DEFAULT_PORT} port.
     *
     * @return new FTPServer on default port.
     * @throws IOException if IOException happened while creating new server.
     */
    @SuppressWarnings("WeakerAccess")
    public static FTPServer init() throws IOException {
        return new FTPServer();
    }

    /**
     * Creates new FTPServer on target port.
     *
     * @param port port to create server on.
     * @return new FTPServer on default port.
     * @throws IOException if IOException happened while creating new server.
     */
    public static FTPServer init(final int port) throws IOException {
        return new FTPServer(port);
    }

    public static void main(final String[] args) throws IOException {
        final FTPServer server = init();
    }
}
