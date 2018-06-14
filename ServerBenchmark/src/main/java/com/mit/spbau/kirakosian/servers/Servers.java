package com.mit.spbau.kirakosian.servers;

import com.mit.spbau.kirakosian.servers.impl.simpleServer.SimpleServer;

import java.io.IOException;

public class Servers {

    public static Server createServer(final ServerType type) throws IOException {
        switch (type) {
            case Simple:
                return new SimpleServer();
            case Blocking:
                return null;
            case NonBlocking:
                return null;
        }
        throw new UnknownServerTypeException();
    }

    private static class UnknownServerTypeException extends RuntimeException {
    }

    public enum ServerType {
        Simple, Blocking, NonBlocking
    }
}
