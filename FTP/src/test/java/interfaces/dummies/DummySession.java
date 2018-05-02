package interfaces.dummies;

import interfaces.AbstractBlockingSession;
import interfaces.Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DummySession extends AbstractBlockingSession {

    private final List<String> lines = new ArrayList<>();

    protected DummySession(final Socket socket, final int id, final Server server) {
        super(socket, id, server);
    }

    @Override
    protected void processLine(final String line) {
        lines.add(line);
    }

    public List<String> lines() {
        return lines;
    }
}
