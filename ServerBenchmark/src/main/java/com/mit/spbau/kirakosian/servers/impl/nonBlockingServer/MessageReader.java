package com.mit.spbau.kirakosian.servers.impl.nonBlockingServer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.mit.spbau.kirakosian.Utils;
import com.mit.spbau.kirakosian.connector.UnexpectedProtocolMessageException;

import java.nio.ByteBuffer;

import static com.mit.spbau.kirakosian.Protocol.NEW_QUERY;
import static com.mit.spbau.kirakosian.Protocol.STOP;

@SuppressWarnings("WeakerAccess")
public class MessageReader {

    private final ByteBuffer buffer;

    private long queryBegin;

    private int signal = -1;
    private int size = - 1;
    private int readSize = 0;
    private byte[] array;

    public MessageReader(final ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public boolean canRead() {
        if (size == -1) {
            return buffer.remaining() >= 4;
        }
        return buffer.remaining() >= 1;
    }

    public boolean completed() {
        if (signal == -1) {
            return false;
        }
        if (signal == STOP) {
            return true;
        }
        if (signal == NEW_QUERY) {
            return size != -1 && size == readSize;
        }
        throw new UnexpectedProtocolMessageException();
    }

    public void readNext() {
        if (completed()) {
            throw new RuntimeException(); // should never happen
        }
        if (signal == -1) {
            queryBegin = System.currentTimeMillis();
            signal = buffer.getInt();
            return;
        }
        if (size == -1) {
            size = buffer.getInt();
            array = new byte[size];
            return;
        }
        if (size != 0 && size == readSize) {
            throw new RuntimeException();
        }
        array[readSize++] = buffer.get();
    }

    public int getState() {
        return signal;
    }

    public int[] getArray() {
        try {
            return Utils.getArray(array);
        } catch (final InvalidProtocolBufferException e) {
            throw new RuntimeException(); // should never happen
        }
    }

    public long queryBegin() {
        return queryBegin;
    }

    public void refresh() {
        array = null;
        size = -1;
        readSize = 0;
        signal = -1;
    }

}
