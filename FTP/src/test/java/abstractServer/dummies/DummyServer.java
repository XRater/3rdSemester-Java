package abstractServer.dummies;

import abstractServer.AbstractBlockingServer;
import abstractServer.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DummyServer extends AbstractBlockingServer {

    @SuppressWarnings("WeakerAccess")
    public final static int DEFAULT_PORT = 9925;

    @NotNull
    private final List<DummySession> sessionList = new ArrayList<>();

    private DummyServer(final int port) throws IOException {
        super(port);
    }

    @NotNull
    @Override
    protected Session newSession(final Socket socket, final int number) {
        final DummySession session = new DummySession(socket, number, this);
        sessionList.add(session);
        return session;
    }

    @NotNull
    public List<DummySession> session() {
        return sessionList;
    }

    public static DummyServer init() throws IOException {
        return new DummyServer(DEFAULT_PORT);
    }


}
