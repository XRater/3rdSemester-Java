package abstractServer.dummies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class DummyClient {

    final Socket socket;
    final DataOutputStream os;
    final DataInputStream is;

    public DummyClient() throws IOException {
        socket = new Socket("localhost", DummyServer.DEFAULT_PORT);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
    }

    public void sendCommands(final int... commands) throws IOException {
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
