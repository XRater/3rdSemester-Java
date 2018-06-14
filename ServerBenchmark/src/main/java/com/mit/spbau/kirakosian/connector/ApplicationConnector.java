package com.mit.spbau.kirakosian.connector;

import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.testing.TestingStats;

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
    private final TestingStats stats;

    public ApplicationConnector(final TestOptions options, final TestingStats stats) throws IOException {
        this.options = options;
        this.stats = stats;
        server = new ServerSocket(APPLICATION_PORT);
    }

    public void waitForNewClient() throws IOException {
        socket = server.accept();
        processSocket(socket);
    }

    private void processSocket(final Socket socket) throws IOException {
        is = new ObjectInputStream(socket.getInputStream());
        os = new ObjectOutputStream(socket.getOutputStream());

        os.writeInt(OPTIONS);
        os.writeObject(options);
    }

    public void startTestCase() throws IOException {
        os.writeInt(START_TEST_CASE);

        int signal;
        signal = is.readInt();
        if (signal == END_TEST_CASE) {
            stats.done();
        } else if (signal == CLIENT_TIME) {

        } else {
            throw new UnexpectedProtocolMessageException();
        }
    }
}
