package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.connector.ApplicationConnector;
import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;
import com.mit.spbau.kirakosian.options.TestOptions;

import java.io.*;
import java.net.Socket;

import static com.mit.spbau.kirakosian.connector.Protocol.*;

public class Client {

    private final TestOptions options;
    final private ObjectOutputStream os;
    final private ObjectInputStream is;

    private Client() throws IOException, ClassNotFoundException {
        final Socket socket = new Socket("localhost", ApplicationConnector.APPLICATION_PORT);
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        if (is.readInt() != OPTIONS)  {
            throw new UnexpectedProtocolMessageException();
        }
        options = (TestOptions) is.readObject();
        options.print();
    }

    private void startTest() throws IOException {
        for (int currentValue = options.lowerBound();
                currentValue <= options.upperBound();
                currentValue += options.delta()) {

            if (is.readInt() != START_TEST_CASE) {
                throw new UnexpectedProtocolMessageException();
            }

            // one test here

           os.writeInt(END_TEST_CASE);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.startTest();
    }

}
