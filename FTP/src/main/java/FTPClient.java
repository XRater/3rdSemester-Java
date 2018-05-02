import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {

    final private Socket socket;
    final private DataOutputStream os;
    final private DataInputStream is;


    public FTPClient() throws IOException {
        socket = new Socket("localhost", FTPServer.DEFAULT_PORT);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
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

    void list(String path) throws IOException {
        os.writeInt(1);
        os.writeUTF(path);
        os.flush();

        final int number = is.readInt();
        System.out.println("Total number of files: " + number);
        for (int i = 0; i < number; i++) {
            final String name = is.readUTF();
            final boolean isFolder = is.readBoolean();
            final String isFolderString = isFolder ? "is a folder." : "is not a folder.";
            System.out.println("\"" + name + "\" " + isFolderString);
        }
    }

    public static void main(String[] args) throws IOException {
        final FTPClient client = new FTPClient();
        client.list(".");
    }


}
