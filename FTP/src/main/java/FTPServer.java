import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class FTPServer {

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
        for (FTPSession session: sessions) {
            session.close();
        }
    }

    private FTPSession createNewSession(final Socket socket, final int number) {
        final FTPSession session = new FTPSession(socket, number);
        new Thread(session::process).start();
        return session;
    }

    public synchronized void shutDown() {
        if (ceaseWorking || server == null || serverClosed) {
            return;
        }
        ceaseWorking = true;

        while (!serverClosed) {
            try {
                wait();
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    public boolean anyErrorOccurred() {
        return errors.size() != 0;
    }

    public List<IOException> getErrors() {
        return errors;
    }

    public static FTPServer init() throws IOException {
        return new FTPServer();
    }

    public static FTPServer init(final int port) throws IOException {
        return new FTPServer(port);
    }

    public static void main(String[] args) throws IOException {
        FTPServer server = init();
    }
}
