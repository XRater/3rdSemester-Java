package abstractServer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("unused")
public abstract class AbstractBlockingServer implements Server {

    @NotNull
    private final ServerSocket server;
    private final int port;

    @NotNull
    private final Thread mainThread;

    private volatile boolean ceaseWorking;
    private volatile boolean serverClosed;

    private final Set<Session> sessions = new TreeSet<>();
    private int sessionsProcessed = 0;
    private final List<Exception> errors = new ArrayList<>();

    protected AbstractBlockingServer(final int port) throws IOException {
        server = new ServerSocket(port);
        this.port = port;
        mainThread = new Thread(this::work);
        mainThread.start();
    }

    /**
     * The method shuts down server.
     * <p>
     * Server will not accept connections anymore after this method was executed.
     * Every present session will be closed.
     *
     * This is blocking method. You may interrupt this method for your own risk.
     * In that case, it is not guaranteed that every port will be released by the time
     * of the method end.
     */
    @Override
    public synchronized void shutDown() {
        if (ceaseWorking || serverClosed) {
            return;
        }
        ceaseWorking = true;
        try {
            server.close();
            mainThread.join();
        } catch (@NotNull final IOException e) {
            errors.add(e);
        } catch (@NotNull final InterruptedException e) { // If we do not want to wait for shutdown
            // do nothing
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
    @NotNull
    public List<Exception> getErrors() {
        return errors;
    }

    private void work() {
        System.out.println("Server was initialized on port " + port);

        // working until error or shutdown
        while (!ceaseWorking) {
            final Socket socket;
            try {
                socket = server.accept(); // must be closed by session
                System.out.println("New connection on port "  + socket.getPort());
                final Session session = newSession(socket, sessionsProcessed++);
                handleSession(session);
            } catch (@NotNull final IOException e) {
                    if (e instanceof SocketException && ceaseWorking) {
                        break; // server was turned off
                    }
                    errors.add(e); // Shutdown if any error occurred
                    break;
                }
            }


        // shutting down server if error happened
        if (!server.isClosed()) {
            try {
                server.close();
            } catch (@NotNull final IOException e) {
                errors.add(e); // store all exceptions
            }
        }

        // closing current connections
        closeSessions();

        serverClosed = true;
        System.out.println("Server was shut down");
    }

    @Override
    public void closeSession(@NotNull final Session session) {
        sessions.remove(session);
        session.close();
    }

    protected abstract Session newSession(final Socket socket, int number);

    private void closeSessions() {
        for (final Session session : sessions) {
            session.close();
        }
    }

    private void handleSession(@NotNull final Session session) {
        sessions.add(session);
        session.run(); // starts session
    }
}







