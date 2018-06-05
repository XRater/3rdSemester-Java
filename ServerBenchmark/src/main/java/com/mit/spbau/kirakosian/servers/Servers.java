package com.mit.spbau.kirakosian.servers;

public class Servers {

    public static Server init(final ServerType type) {
        switch (type) {
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
        Blocking, NonBlocking
    }
}
