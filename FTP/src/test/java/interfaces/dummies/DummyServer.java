package interfaces.dummies;

import interfaces.AbstractBlockingServer;
import interfaces.Session;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DummyServer extends AbstractBlockingServer {

    public final static int DEFAULT_PORT = 9925;

    private List<DummySession> sessionList = new ArrayList<>();

    protected DummyServer(final int port) throws IOException {
        super(port);
    }

    @Override
    protected Session newSession(final Socket socket, final int number) {
        DummySession session = new DummySession(socket, number, this);
        sessionList.add(session);
        return session;
    }

    public List<DummySession> session() {
        return sessionList;
    }

    public static DummyServer init() throws IOException {
        return new DummyServer(DEFAULT_PORT);
    }

}
