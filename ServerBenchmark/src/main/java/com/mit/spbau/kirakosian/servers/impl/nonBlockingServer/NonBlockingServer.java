package com.mit.spbau.kirakosian.servers.impl.nonBlockingServer;

import com.mit.spbau.kirakosian.servers.exceptions.AbortException;
import com.mit.spbau.kirakosian.servers.impl.AbstractServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NonBlockingServer extends AbstractServer {

    private volatile boolean working = true;
    private final ServerSocketChannel server;

    private Thread mainThread;
    private Thread readingThread;
    private Thread writingThread;

    public NonBlockingServer() throws AbortException {
        try {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(PORT));
        } catch (final IOException e) {
            throw new AbortException();
        }
    }

    @Override
    public void start() {
        working = true;
        mainThread = new Thread(this::work);
        readingThread = new Thread(this::processRead);
        writingThread = new Thread(this::processWrite);
        mainThread.start();
        readingThread.start();
        writingThread.start();
    }

    private void work() {
        while (working) {
            try {
                final SocketChannel socket = server.accept();
                processConnection(socket);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        if (!server.isOpen()) {
            try {
                server.close();
            } catch (final IOException e) {
                listener.fail(e);
            }
        }
    }

    private void processConnection(final SocketChannel socket) {

    }

    private void processRead() {
    }

    private void processWrite() {
    }

    @Override
    public synchronized void shutDown() {
        if (!working) {
            return;
        }
        working = false;
        try {
            server.close();
            mainThread.join();
            readingThread.join();
            writingThread.join();
        } catch (final IOException | InterruptedException e) {
            listener.fail(e);
        }
    }
}
