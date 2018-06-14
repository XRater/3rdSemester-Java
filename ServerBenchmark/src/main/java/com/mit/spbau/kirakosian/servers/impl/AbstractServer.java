package com.mit.spbau.kirakosian.servers.impl;

import com.mit.spbau.kirakosian.servers.Server;
import com.mit.spbau.kirakosian.servers.ServerStatsListener;

public abstract class AbstractServer implements Server {

    public static final int PORT = 8099;
    protected ServerStatsListener listener;

    @Override
    public void setServerActionListener(final ServerStatsListener actionListener) {
        listener = actionListener;
    }
}
