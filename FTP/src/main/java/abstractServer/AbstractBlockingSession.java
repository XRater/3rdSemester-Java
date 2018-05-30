package abstractServer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
    @NotNull
    private final Socket socket;
    private final Server server;

    @NotNull
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
        } catch (@NotNull final IOException e) {
            closeSession(e);
        }
    }

    protected void closeSession(@Nullable final Exception e) {
        if (e != null) {
            errors.add(e);
        }
        server.closeSession(this);
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
        // we need two block in case of error happened in one of them
        try {
            os.close();
        } catch (@NotNull final IOException e) {
            errors.add(e);
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (@NotNull final IOException e) {
            errors.add(e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean anyErrorOccurred() {
        return errors.size() != 0;
    }

    @NotNull
    @Override
    public List<Exception> getErrors() {
        return errors;
    }

    protected void sendToClient(@NotNull final byte[] buf) {
        executor.execute(() -> {
            try {
                os.write(buf);
                os.flush();
            } catch (@NotNull final IOException e) {
                closeSession(e);
            }
        });
    }

    protected void sendToClient(final long x) {
        executor.execute(() -> {
            try {
                os.writeLong(x);
                os.flush();
            } catch (@NotNull final IOException e) {
                closeSession(e);
            }
        });
    }

    @SuppressWarnings("SameParameterValue")
    protected void sendToClient(@NotNull final byte[] buf, final int offset, final int length) {
        executor.execute(() -> {
            try {
                os.write(buf, offset, length);
                os.flush();
            } catch (@NotNull final IOException e) {
                closeSession(e);
            }
        });
    }

    private void processRead() {
        try (final DataInputStream is = new DataInputStream(socket.getInputStream())) {
            processInput(is);
        } catch (@NotNull final IOException e) {
            closeSession(e);
        }
    }

    protected abstract void processInput(final DataInputStream is) throws IOException;

    protected void addTask(final Runnable task) {
        server.getPool().execute(task);
    }
}
