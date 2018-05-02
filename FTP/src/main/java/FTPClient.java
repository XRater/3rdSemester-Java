import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {

    Socket socket = new Socket("localhost", FTPServer.DEFAULT_PORT);

    public FTPClient() throws IOException {
        new Thread(this::processRead).start();
        new Thread(this::processWrite).start();
    }

    void processRead() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    void processWrite() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write("Hello\n");
            writer.flush();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writer.write("wow" + Integer.toString(i) + "\n");
                writer.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        FTPClient client = new FTPClient();
    }


}
