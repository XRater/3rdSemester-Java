package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.connector.ApplicationConnector;
import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;
import com.mit.spbau.kirakosian.options.TestOptions;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.mit.spbau.kirakosian.connector.Protocol.*;

public class TestingClient {

    private final AutoOptions options;
    final private ObjectOutputStream os;
    final private ObjectInputStream is;

    private final String ip;

    private TestingClient(final String ip) throws IOException, ClassNotFoundException {
        final Socket socket = new Socket(InetAddress.getByName(ip), ApplicationConnector.APPLICATION_PORT);
        this.ip = ip;
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        if (is.readInt() != OPTIONS)  {
            throw new UnexpectedProtocolMessageException();
        }
        options = new AutoOptions((TestOptions) is.readObject());
    }

    private void startTest() throws IOException {
        while (options.notFinished()) {
            System.out.println(options.arraySize() + " " + options.clients() + " " + options.queries() + " " + options.delay());
            if (is.readInt() != START_TEST_CASE) {
                throw new UnexpectedProtocolMessageException();
            }

            final Map<Thread, ArrayClient> threads = new HashMap<>();
            for (int i = 0; i < options.clients(); i++) {
                final ArrayClient client = new ArrayClient(ip);
                threads.put(new Thread(() -> {
                    try {
                        client.work(options.queries(), options.arraySize(), options.delay());
                    } catch (final IOException e) {
                        // do nothing
                    }
                }), client);
            }
            for (final Thread thread : threads.keySet()) {
                thread.start();
            }
            for (final Map.Entry<Thread, ArrayClient> entry : threads.entrySet()) {
                try {
                    entry.getKey().join();
                    os.writeInt(CLIENT_TIME);
                    os.writeLong(entry.getValue().getTime() / options.queries());
                    os.flush();
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }

            os.writeInt(END_TEST_CASE);
            os.flush();
            options.next();
        }
    }

    public static void main(final String[] args) throws IOException, ClassNotFoundException {
        final String ip;
        if (args.length == 0) {
            ip = "127.0.0.1";
        } else {
            ip = args[0];
        }
        final TestingClient client = new TestingClient(ip);
        client.startTest();
    }

}
