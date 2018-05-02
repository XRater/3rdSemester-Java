package interfaces;

/**
 * Server interface. Might have different architectures.
 */
public interface Server {

    /**
     * Turns off the server. It is required, that any implementation releases

     * every port, used by server.
     */
    void shutDown();

    /**
     * Call this method to close one exact session.
     */
    void closeSession(Session session);
}
