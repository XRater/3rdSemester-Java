package com.mit.spbau.kirakosian.servers.impl.nonBlockingServer;

import com.mit.spbau.kirakosian.Utils;
import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static com.mit.spbau.kirakosian.Protocol.NEW_QUERY;
import static com.mit.spbau.kirakosian.Protocol.STOP;

@SuppressWarnings("WeakerAccess")
public class Client {

    private static final int BUFFER_SIZE = 1000_000;

    private final @NotNull SocketChannel channel;
    private final NonBlockingServer server;

    private final ByteBuffer reading = ByteBuffer.allocate(BUFFER_SIZE);
    private final ByteBuffer writing = ByteBuffer.allocate(BUFFER_SIZE);
    MessageReader reader = new MessageReader(reading);

    private long queryBegin;
    private State state;

    public Client(@NotNull final SocketChannel channel, final NonBlockingServer server) {
        this.channel = channel;
        this.server = server;
        state = State.READING;
        writing.flip();
    }

    public void onWrite() throws IOException {
        synchronized (writing) {
            if (state != State.WRITING) {
                return;
            }
            channel.write(writing);
            processWroteInfo();
        }
    }

    public void onRead() throws IOException {
        if (state != State.READING) {
            return;
        }
        //noinspection StatementWithEmptyBody
        while (channel.read(reading) > 0) {}

        processReadInfo();
    }

    private void processReadInfo() {
        reading.flip();

        while (!reader.completed() && reader.canRead()) {
            reader.readNext();
        }

        if (reader.completed()) {
            if (reader.getState() == STOP) {
                server.unregisterRead(this);
                server.unregisterWrite(this);
            } else if (reader.getState() == NEW_QUERY) {
                queryBegin = reader.queryBegin();
                server.pool().submit(new SortTask(reader.getArray()));
            } else {
                throw new UnexpectedProtocolMessageException();
            }
            reader.refresh();
            reading.clear();
            return;
        }

        reading.compact();
    }

    private void processWroteInfo() {
        if (writing.remaining() == 0) {
            server.listener().timeForClientOnServer(System.currentTimeMillis() - queryBegin);
            state = State.READING;
        }
    }

    @NotNull
    public SocketChannel getChannel() {
        return channel;
    }

    public void sendNewMessage(final int[] array) {
        synchronized (writing) {
            final byte[] message = Utils.makeArrayMessage(array);
            writing.compact();
            writing.putInt(message.length);
            writing.put(message);
            writing.flip();
        }
    }

    private class SortTask implements Runnable {

        private final int[] innerArray;

        public SortTask(final int[] innerArray) {
            this.innerArray = innerArray;
        }

        @Override
        public void run() {
            final long begin = System.currentTimeMillis();
            Utils.sort(innerArray);
            final long end = System.currentTimeMillis();
            server.listener().timeForTask(end - begin);
            sendNewMessage(innerArray);
            state = State.WRITING;
        }
    }

    enum State {
        READING, WRITING
    }
}
