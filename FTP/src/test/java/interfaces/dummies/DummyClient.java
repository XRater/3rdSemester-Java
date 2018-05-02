package interfaces.dummies;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class DummyClient {

    public DummyClient() throws IOException {
        final Socket socket = new Socket("localhost", DummyServer.DEFAULT_PORT);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write("Hello!\n");
        writer.flush();
    }

}
