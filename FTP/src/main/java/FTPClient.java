import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;

/**
 * This class provides methods to work with FTPServer. These are:
 *
 * {@link FTPClient#list(String)} -- prints all files in the target directory.
 *
 * {@link FTPClient#get(String, String)} -- downloads and saves file from the
 * server to the target file.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class FTPClient {

    private final static int BUF_SIZE = 2048;

    @NotNull
    final private DataOutputStream os;
    @NotNull
    final private DataInputStream is;

    /**
     * Creates new client, that connected to the FTPServer.
     *
     * @param url servers url.
     * @param port port to connect.
     * @throws IOException if any IOException occurred.
     */
    public FTPClient(final String url, final int port) throws IOException {
        final Socket socket = new Socket(url, port);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
    }

    /**
     * Creates new client, that connected to the FTPServer.
     *
     * @param url servers url.
     * @throws IOException if any IOException occurred.
     */
    public FTPClient(final String url) throws IOException {
        this(url, FTPServer.DEFAULT_PORT);
    }

    /**
     * Prints list of all files (including folders), inside the source folder.
     *
     * Also for every file prints whether the file is a directory or not.
     *
     * @param source target folder.
     * @throws IOException if any IOException occurred.
     */
    public void list(@NotNull final String source) throws IOException {
        os.writeInt(1);
        os.writeUTF(source);
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

    /**
     * Downloads file from the server to the file with specified name. If file with
     * the given name already exists, FileAlreadyExistsException will be thrown.
     *
     * @param source target file.
     * @param destination path, to save file.
     * @throws IOException if any IOException occurred.
     */
    public void get(@NotNull final String source, @NotNull final String destination) throws IOException {
        os.writeInt(2);
        os.writeUTF(source);
        os.flush();

        final File file = new File(destination);
        if (!file.createNewFile()) {
            throw new FileAlreadyExistsException();
        }
        final byte[] buf = new byte[BUF_SIZE];
        try (final FileOutputStream os = new FileOutputStream(file)) {
            final long size = is.readLong();
            IOUtils.copyLarge(is, os, 0, size);
        }
    }

    private class FileAlreadyExistsException extends RuntimeException {}
}
