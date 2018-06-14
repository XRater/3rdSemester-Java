package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.connector.ApplicationConnector;
import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;
import com.mit.spbau.kirakosian.options.TestOptions;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.mit.spbau.kirakosian.connector.Protocol.*;

public class TestingClient {

    private final AutoOptions options;
    final private ObjectOutputStream os;
    final private ObjectInputStream is;

    private TestingClient() throws IOException, ClassNotFoundException {
        final Socket socket = new Socket("localhost", ApplicationConnector.APPLICATION_PORT);
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

            final List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < options.clients(); i++) {
                final ArrayClient client = new ArrayClient();
                threads.add(new Thread(() -> {
                    try {
                        client.work(options.queries(), options.arraySize(), options.delay());
                    } catch (final IOException e) {
                        // do nothing
                    }
                }));
            }
            for (final Thread thread : threads) {
                thread.start();
            }
            for (final Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            os.writeInt(END_TEST_CASE);
            os.flush();
            options.next();
        }
    }

    public static void main(final String[] args) throws IOException, ClassNotFoundException {
        final TestingClient client = new TestingClient();
        client.startTest();
    }

}
