import abstractServer.AbstractBlockingSession;
import abstractServer.Server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class FTPSession extends AbstractBlockingSession {

    FTPSession(final Socket socket, final int id, final Server server) {
        super(socket, id, server);
    }

    @Override
    protected void processInput(final DataInputStream is) throws IOException {
        while (true) {
            final int query = is.readInt();
            final String path = is.readUTF();
            switch (query) {
                case 1:
                    sendList(path);
                    break;
                case 2:
                    sendFile(path);
                    break;
                case 3:
                    closeSession(null);
                    return;
                default:
                    reject();
            }
        }
    }

    private void sendList(final String path) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
             final DataOutputStream os = new DataOutputStream(bos)) {

            final File target = new File(path);
            if (!target.isDirectory()) {
                os.writeInt(0);
            } else {
                os.writeInt(Objects.requireNonNull(target.listFiles()).length);
                for (final File file : Objects.requireNonNull(target.listFiles())) {
                    os.writeUTF(file.getName());
                    os.writeBoolean(file.isDirectory());
                }
            }
            sendToClient(bos.toByteArray());

        } catch (final IOException e) {
            //TODO
        }
    }

    private void sendFile(final String path) {

    }

    private void reject() {}
}
