import interfaces.AbstractBlockingSession;
import interfaces.Server;

import java.net.Socket;

public class FTPSession extends AbstractBlockingSession {

    FTPSession(final Socket socket, final int id, final Server server) {
        super(socket, id, server);
        System.out.println("FTP:" + socket.isClosed());
    }

    @Override
    protected void processLine(final String line) {
        final String[] tokens = line.split(" ");
        if (tokens.length != 2) {
            reject(line);
            return;
        }
        final int query;
        final String path = tokens[1];
        try {
            query = Integer.parseInt(tokens[0]);
        } catch (final NumberFormatException e) {
            reject(line);
            return;
        }
        switch (query) {
            case 1:
                sendList(path);
                break;
            case 2:
                sendFile(path);
                break;
            default:
                reject(line);
        }
    }

    private void sendList(final String path) {

    }

    private void sendFile(final String path) {

    }

    private void reject(final String line) {
        sendLineToClient("Invalid query: \"" + line + "\"");
    }
}
