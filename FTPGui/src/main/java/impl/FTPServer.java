package impl;

import abstractServer.AbstractBlockingServer;
import abstractServer.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

/**
 * impl.FTPServer class. Has factory init method and shutDown method. Also it is possible
 * to track mistakes, that happened during server work.
 * <p>
 * Server may accept a lot of clients and work with them in distinct thread by {@link FTPSession}
 * class. In fact all possibilities of server are described in {@link FTPSession}.
 * <p>
 * Server will shut down if any error occurred during it's work (not just in one created session).
 **/
@SuppressWarnings("unused")
public class FTPServer extends AbstractBlockingServer {

    @SuppressWarnings("WeakerAccess")
    public final static int DEFAULT_PORT = 9996;

    private FTPServer(final int port) throws IOException {
        super(port);
    }

    private FTPServer() throws IOException {
        this(DEFAULT_PORT);
    }

    @NotNull
    @Override
    protected Session newSession(final Socket socket, final int number) {
        return new FTPSession(socket, number, this);
    }

    /**
     * Creates new impl.FTPServer on {@link FTPServer#DEFAULT_PORT} port.
     *
     * @return new impl.FTPServer on default port.
     * @throws IOException if IOException happened while creating new server.
     */
    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    public static FTPServer init() throws IOException {
        return new FTPServer();
    }

    /**
     * Creates new impl.FTPServer on target port.
     *
     * @param port port to create server on.
     * @return new impl.FTPServer on default port.
     * @throws IOException if IOException happened while creating new server.
     */
    public static FTPServer init(final int port) throws IOException {
        return new FTPServer(port);
    }

    public static void main(final String[] args) throws IOException {
        init();
    }
}
