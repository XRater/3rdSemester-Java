package com.mit.spbau.kirakosian.servers;

import com.mit.spbau.kirakosian.servers.exceptions.AbortException;
import com.mit.spbau.kirakosian.servers.impl.blockingServer.BlockingServer;
import com.mit.spbau.kirakosian.servers.impl.nonBlockingServer.NonBlockingServer;
import com.mit.spbau.kirakosian.servers.impl.simpleServer.SimpleServer;

public class Servers {

    public static Server createServer(final ServerType type) throws AbortException {
        switch (type) {
            case Simple:
                return new SimpleServer();
            case Blocking:
                return new BlockingServer();
            case NonBlocking:
                return new NonBlockingServer();
        }
        throw new UnknownServerTypeException();
    }

    private static class UnknownServerTypeException extends RuntimeException {
    }

    public enum ServerType {
        Simple, Blocking, NonBlocking
    }
}
