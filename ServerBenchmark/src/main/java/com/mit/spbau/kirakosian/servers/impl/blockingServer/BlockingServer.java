package com.mit.spbau.kirakosian.servers.impl.blockingServer;

import com.mit.spbau.kirakosian.servers.ServerStatsListener;
import com.mit.spbau.kirakosian.servers.exceptions.AbortException;
import com.mit.spbau.kirakosian.servers.impl.AbstractBlockingServer;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("WeakerAccess")
public class BlockingServer extends AbstractBlockingServer {

    private final ExecutorService pool = Executors.newFixedThreadPool(3);

    public BlockingServer() throws AbortException {
        super();
    }

    @Override
    protected void processConnection(final Socket socket) {
        new Session(socket, this);
    }

    public ExecutorService getPool() {
        return pool;
    }

    public ServerStatsListener getListener() {
        return listener;
    }
}
