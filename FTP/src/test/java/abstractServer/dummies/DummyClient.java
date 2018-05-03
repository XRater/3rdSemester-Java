package abstractServer.dummies;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DummyClient {

    @NotNull
    private final DataOutputStream os;
    @NotNull
    private final DataInputStream is;

    public DummyClient() throws IOException {
        final @NotNull Socket socket = new Socket("localhost", DummyServer.DEFAULT_PORT);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
    }

    public void sendCommands(@NotNull final int... commands) throws IOException {
        for (final int cmd : commands) {
            os.writeInt(cmd);
            os.flush();
        }
    }

    public int sendAnswerIntCommand() throws IOException {
        os.writeInt(1);
        os.flush();

        return is.readInt();
    }

}
