import org.jetbrains.annotations.NotNull;

import java.net.Socket;

public class FTPSession implements Comparable<FTPSession> {

    private final int id;
    private final Socket socket;

    FTPSession(final Socket socket, final int id) {
        this.socket = socket;
        this.id = id;
    }

    void process() {

    }

    @Override
    public int compareTo(@NotNull final FTPSession o) {
        return id - o.id;
    }

    public void close() {

    }
}
