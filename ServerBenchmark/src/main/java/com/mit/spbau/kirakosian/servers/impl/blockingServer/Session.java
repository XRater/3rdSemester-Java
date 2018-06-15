package com.mit.spbau.kirakosian.servers.impl.blockingServer;

import com.mit.spbau.kirakosian.Utils;
import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;
import com.mit.spbau.kirakosian.servers.ServerStatsListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.mit.spbau.kirakosian.Protocol.NEW_QUERY;
import static com.mit.spbau.kirakosian.Protocol.STOP;

class Session {

    private final Executor writer = Executors.newSingleThreadExecutor();
    private final Executor pool;
    private final ServerStatsListener listener;

    private final Socket socket;
    private DataOutputStream os;

    Session(final Socket socket, final BlockingServer server) {
        pool = server.getPool();
        this.socket = socket;
        listener = server.getListener();
        try {
            os = new DataOutputStream(socket.getOutputStream());
        } catch (final IOException e) {
            try {
                socket.close();
            } catch (final IOException e1) {
                listener.fail(e1);
            }
            listener.fail(e);
        }
        new Thread(this::processReading).start();
    }

    private void processReading() {
        try (final DataInputStream is = new DataInputStream(socket.getInputStream())) {
            int signal;
            signal = is.readInt();
            while (signal != STOP) {
                if (signal != NEW_QUERY) {
                    throw new UnexpectedProtocolMessageException();
                }

                final long queryBegin = System.currentTimeMillis();
                final int[] array = Utils.readArray(is);
                pool.execute(new SortTask(array, queryBegin));

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

    private class SortTask implements Runnable {

        private final long queryBegin;
        private final int[] array;

        SortTask(final int[] array, final long queryBegin) {
            this.queryBegin = queryBegin;
            this.array = array;
        }

        @Override
        public void run() {
            final long begin = System.currentTimeMillis();
            Utils.sort(array);
            final long end = System.currentTimeMillis();
            listener.timeForTask(end - begin);
            writer.execute(new SendBackTask(array, queryBegin));
        }
    }

    private class SendBackTask implements Runnable {

        private final long queryBegin;
        private final int[] array;

        SendBackTask(final int[] array, final long queryBegin) {
            this.queryBegin = queryBegin;
            this.array = array;
        }


        @Override
        public void run() {
            try {
                Utils.writeArray(os, array);
                final long queryEnd = System.currentTimeMillis();
                listener.timeForClientOnServer(queryEnd - queryBegin);
            } catch (final IOException e) {
                listener.fail(e);
            }
        }
    }
}
