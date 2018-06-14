package com.mit.spbau.kirakosian.servers.impl.simpleServer;

import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;
import com.mit.spbau.kirakosian.servers.impl.AbstractBlockingServer;
import com.mit.spbau.kirakosian.Utils;

import java.io.*;
import java.net.Socket;

import static com.mit.spbau.kirakosian.Protocol.NEW_QUERY;
import static com.mit.spbau.kirakosian.Protocol.STOP;

public class SimpleServer extends AbstractBlockingServer {

    public SimpleServer() throws IOException {
        super();
    }

    @Override
    protected void processConnection(final Socket socket) {
        try (final DataOutputStream os = new DataOutputStream(socket.getOutputStream());
             final DataInputStream is = new DataInputStream(socket.getInputStream())) {

            int signal;
            signal = is.readInt();
            while (signal != STOP) {
                if (signal != NEW_QUERY) {
                    throw new UnexpectedProtocolMessageException();
                }
                final long queryBegin = System.currentTimeMillis();
                final int[] array = Utils.readArray(is);

                final long sortBegin = System.currentTimeMillis();
                Utils.sort(array);
                final long sortEnd = System.currentTimeMillis();

                Utils.writeArray(os, array);
                os.flush();
                final long queryEnd = System.currentTimeMillis();

                listener.timeForTask(sortEnd - sortBegin);
                listener.timeForClientOnServer(queryEnd - queryBegin);

                signal = is.readInt();
            }

        } catch (final IOException e) {
            try {
                socket.close();
            } catch (final IOException e1) {
                listener.fail(e1);
            }
            listener.fail(e);
        }
    }

}
