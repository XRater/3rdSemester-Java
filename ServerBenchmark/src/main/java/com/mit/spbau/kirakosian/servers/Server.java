package com.mit.spbau.kirakosian.servers;

import com.mit.spbau.kirakosian.servers.exceptions.AbortException;

/**
 * Server interface. Might have different architectures.
 */
public interface Server {

    void start();

    /**
     * Turns off the server. It is required, that any implementation releases

     * every port, used by server.
     */
    void shutDown();

    void setServerActionListener(ServerStatsListener actionListener);

}
