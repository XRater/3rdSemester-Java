package interfaces;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.List;
import java.util.function.Consumer;

/**
 * This interface provides session methods to work with one exact client.
 */
public interface Session extends Comparable<Session>, Closeable {

    /**
     * Id is required to implement comparable method, therefore server might
     * be able to store sessions in set.
     *
     * @return session id
     */
    int id();

    /**
     * The method must be used to close all resources, such as sockets, Readers e.t.c.
     */
    @Override
    void close();

    @Override
    default int compareTo(@NotNull final Session o) {
        return id() - o.id();
    }

    /**
     * Starts to process the session. For example, this method might start reading
     * and writing threads.
     */
    void run();

    /**
     * This method checks if any error occurred.
     *
     * @return true if any error occurred during work and false otherwise.
     */
    default boolean anyErrorOccurred() {
        return false;
    }

    /**
     * Method to track all errors, occurred during work.
     *
     * @return list of occurred errors.
     */
    default List<Exception> getErrors() {
        return List.of();
    }
}
