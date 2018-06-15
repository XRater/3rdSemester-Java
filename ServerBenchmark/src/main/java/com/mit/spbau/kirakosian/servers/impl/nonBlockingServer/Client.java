package com.mit.spbau.kirakosian.servers.impl.nonBlockingServer;

import org.jetbrains.annotations.NotNull;

import java.nio.channels.SocketChannel;

public class Client {

    private final @NotNull SocketChannel channel;
    private final NonBlockingServer server;

    public Client(@NotNull final SocketChannel channel, final NonBlockingServer server) {
        this.channel = channel;
        this.server = server;
    }

    public void onWrite() {

    }

    public void onRead() {

    }

    @NotNull
    public SocketChannel getChannel() {
        return channel;
    }
}
