package com.mit.spbau.kirakosian.servers;

public class Servers {

    public static Server createServer(final ServerType type) {
        switch (type) {
            case Simple:
                return null;
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
