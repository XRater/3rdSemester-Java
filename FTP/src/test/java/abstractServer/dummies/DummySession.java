package abstractServer.dummies;

import abstractServer.AbstractBlockingSession;
import abstractServer.Server;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DummySession extends AbstractBlockingSession {

    private final List<String> lines = new ArrayList<>();

    DummySession(@NotNull final Socket socket, final int id, final Server server) {
        super(socket, id, server);
    }

    @Override
    protected void processInput(@NotNull final DataInputStream is) throws IOException {
        while (true) {
            final int cmd = is.readInt();
            switch (cmd) {
                case -1:
                    closeSession(null);
                    return;
                case -2:
                    closeSession(new DummyException());
                    return;
                case 1:
                    sendNumber(42);
                    break;
                default:
                    lines.add(String.valueOf(cmd));
                    break;
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void sendNumber(final int x) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        final DataOutputStream os = new DataOutputStream(bos);

        os.writeInt(x);

        sendToClient(bos.toByteArray());
    }

    @NotNull
    public List<String> lines() {
        return lines;
    }
}
