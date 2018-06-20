package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.Utils;
import com.mit.spbau.kirakosian.servers.impl.AbstractServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static com.mit.spbau.kirakosian.Protocol.*;

class ArrayClient {

    private long time;
    private final Socket socket;

    ArrayClient(final String ip) throws IOException {
        socket = new Socket(InetAddress.getByName(ip), AbstractServer.PORT);
    }

    @SuppressWarnings("WeakerAccess")
    public long getTime() {
        return time;
    }

    @SuppressWarnings("WeakerAccess")
    public void work(final int queries, final int size, final int delay) throws IOException {
        try (final DataOutputStream os = new DataOutputStream(socket.getOutputStream());
             final DataInputStream is = new DataInputStream(socket.getInputStream())) {
            final long begin = System.currentTimeMillis();
            for (int i = 0; i < queries; i++) {
                final int[] array = Utils.generate(size);

                os.writeInt(NEW_QUERY);
                Utils.writeArray(os, array);
                os.flush();

                @SuppressWarnings("unused") final int[] result = Utils.readArray(is);

                try {
                    Thread.sleep(delay);
                } catch (final InterruptedException e) {
                    // unlucky
                }
            }
            os.writeInt(STOP);
            os.flush();
            final long end = System.currentTimeMillis();
            time = end - begin;
        } catch (final IOException e) {
            try {
                socket.close();
            } catch (final IOException e1) {
                // do nothing
            }
            throw e;
        }
    }
}
