package com.mit.spbau.kirakosian.connector;

import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.testing.StatsListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.mit.spbau.kirakosian.connector.Protocol.*;

public class ApplicationConnector {

    public final static int APPLICATION_PORT = 8093;

    private final ServerSocket server;
    private Socket socket;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    private final TestOptions options;
    private final StatsListener stats;

    public ApplicationConnector(final TestOptions options, final StatsListener stats) throws IOException {
        this.options = options;
        this.stats = stats;
        server = new ServerSocket(APPLICATION_PORT);
    }

    public void waitForNewClient() throws IOException {
        try {
            socket = server.accept();
        } catch (IOException e) {
            try {
                server.close();
            } catch (IOException e1) {
                // ignore error during close
            }
            throw e;
        }
        processSocket(socket);
    }

    private void processSocket(final Socket socket) throws IOException {
        try {
            is = new ObjectInputStream(socket.getInputStream());
            os = new ObjectOutputStream(socket.getOutputStream());

            os.writeInt(OPTIONS);
            os.writeObject(options);
            os.flush();
        } catch (final IOException e) {
            try {
                closeAll();
            } catch (final IOException e1) {
                // ignore close exception
            }
            throw e;
        }
    }

    private void closeAll() throws IOException {
        is.close(); // try to close resources
        os.close();
        socket.close();
        server.close();
    }

    public void startTestCase() throws IOException {
        try {
            os.writeInt(START_TEST_CASE);
            os.flush();

            int signal;
            signal = is.readInt();
            while (true) {
                if (signal == END_TEST_CASE) {
                    stats.done();
                    break;
                } else if (signal == CLIENT_TIME) {
                    final long time = is.readLong();
                    stats.timeForClientOnClient(time);
                } else {
                    throw new UnexpectedProtocolMessageException();
                }
                signal = is.readInt();
            }
        } catch (final IOException e) {
            try {
                closeAll();
            } catch (final IOException e1) {
                // ignore close exception
            }
            throw e;
        }
    }

    public void close() throws IOException {
        closeAll();
    }
}
