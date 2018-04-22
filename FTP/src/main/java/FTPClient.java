import java.io.IOException;
import java.net.Socket;

public class FTPClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", FTPServer.DEFAULT_PORT);
    }

}
