package interfaces;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Abstract class to process one client connection.
 *
 * By default creates threads to process reading and writing. Every task from the client will
 * be added to the ThreadPool.
 *
 * Also this class takes socket to work with as an input parameter, therefore this class
 * must close the socket when it is not needed anymore (on {@link Session#close()} method call).
 */
public abstract class AbstractBlockingSession implements Session {

    private final int id;
    private final Socket socket;
    private final Server server;

    private final Executor executor;
    private DataOutputStream os;

    private final List<Exception> errors = new ArrayList<>();

    protected AbstractBlockingSession(final Socket socket, final int id, final Server server) {
        this.socket = socket;
        this.id = id;
        this.server = server;
        executor = Executors.newSingleThreadExecutor();

        try {
            os = new DataOutputStream(socket.getOutputStream());
        } catch (final IOException e) {
            errors.add(e);
            server.closeSession(this);
        }
    }

    // all docs inherited from Session interface
    @Override
    public void run() {
        new Thread(this::processRead).start();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void close() {
        // we need two blocks in case any exception occurred during close method call.
        try {
            os.close();
        } catch (final IOException e) {
            errors.add(e);
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (final IOException e) {
            errors.add(e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean anyErrorOccurred() {
        return errors.size() != 0;
    }

    @Override
    public List<Exception> getErrors() {
        return errors;
    }

    public void sendLineToClient(final String line) {
        executor.execute(() -> {
            try {
                os.writeChars(line + "\n");
                os.flush();
            } catch (final IOException e) {
                errors.add(e);
                server.closeSession(this);
            }
        });
    }

    public void sendToClient(final byte[] buf) {
        executor.execute(() -> {
            try {
                os.write(buf);
                os.flush();
            } catch (final IOException e) {
                errors.add(e);
                server.closeSession(this);
            }
        });
    }

    private void processRead() {
        try (final Scanner scanner = new Scanner(socket.getInputStream())) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                processLine(line);
                System.out.println(line);
            }
        } catch (final IOException e) {
            errors.add(e);
            server.closeSession(this);
        }
    }

    protected abstract void processLine(final String line);
}
