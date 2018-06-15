package com.mit.spbau.kirakosian.servers.impl;

import com.mit.spbau.kirakosian.servers.exceptions.AbortException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public abstract class AbstractBlockingServer extends AbstractServer {

    private final @NotNull ServerSocket server;
    private volatile boolean working;
    private Thread mainThread;

    public AbstractBlockingServer() throws AbortException {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new AbortException();
        }
    }

    protected abstract void processConnection(final Socket socket);

    @Override
    public void start() {
        working = true;
        mainThread = new Thread(this::work);
        mainThread.start();
    }

    private void work() {
        while (working) {
            final Socket socket;
            try {
                socket = server.accept();
                new Thread(() -> processConnection(socket)).start();
            } catch (final IOException e) {
                if (e instanceof SocketException && !working) {
                    break;
                }
                listener.fail(e);
                break;
            }
        }

        if (!server.isClosed()) {
            try {
                server.close();
            } catch (final IOException e) {
                listener.fail(e);
            }
        }
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
        } catch (final IOException | InterruptedException e) {
            listener.fail(e);
        }
    }
}
