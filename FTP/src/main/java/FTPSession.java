import abstractServer.AbstractBlockingSession;
import abstractServer.Server;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

class FTPSession extends AbstractBlockingSession {

    private static final int BUF_SIZE = 2048;

    FTPSession(@NotNull final Socket socket, final int id, final Server server) {
        super(socket, id, server);
    }

    @Override
    protected void processInput(@NotNull final DataInputStream is) throws IOException {
        while (true) {
            final int query = is.readInt();
            final String path = is.readUTF();
            switch (query) {
                case 1:
                    addTask(() -> sendList(path));
                    break;
                case 2:
                    addTask(() -> sendFile(path));
                    break;
                case 3:
                    closeSession(null);
                    return;
                default:
                    // do nothing, unknown command
            }
        }
    }

    private void sendList(@NotNull final String path) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(BUF_SIZE);
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

        } catch (@NotNull final IOException e) {
            closeSession(e);
        }
    }

    private void sendFile(@NotNull final String path) {
        final File target = new File(path);
        if (!target.isFile() || !target.canRead()) {
            sendToClient(0);
            return;
        }
        sendToClient(target.length());
        final byte[] buf = new byte[BUF_SIZE];
        try (final InputStream is = new FileInputStream(target)) {
            int bytesRead;
            while (true) {
                bytesRead = is.read(buf);
                if (bytesRead == -1) {
                    break;
                }
                sendToClient(buf, 0, bytesRead);
            }

        } catch (@NotNull final IOException e) {
            closeSession(e);
        }
    }
}
