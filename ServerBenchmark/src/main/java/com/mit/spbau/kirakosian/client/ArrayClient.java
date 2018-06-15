package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.Utils;
import com.mit.spbau.kirakosian.servers.impl.AbstractServer;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static com.mit.spbau.kirakosian.Protocol.*;

public class ArrayClient {

    final private DataOutputStream os;
    final private DataInputStream is;
    private long time;

    public ArrayClient() throws IOException {
        Socket socket = new Socket("localhost", AbstractServer.PORT);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
    }

    public long getTime() {
        return time;
    }

    public void work(final int queries, final int size, final int delay) throws IOException {
        final long begin = System.currentTimeMillis();
        for (int i = 0; i < queries; i++) {
            final int[] array = Utils.generate(size);

            os.writeInt(NEW_QUERY);
            Utils.writeArray(os, array);
            os.flush();

            final int[] result = Utils.readArray(is);

            try {
                Thread.sleep(delay);
            } catch (final InterruptedException e) {
                // TODO
            }
        }
        os.writeInt(STOP);
        os.flush();
        final long end = System.currentTimeMillis();
        time = end - begin;
    }
}
