package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.servers.ServerStatsListener;

public class TestingStats implements ServerStatsListener {

    private boolean isReady;
    private int clientsProcessed;

    public void clear() {
        clientsProcessed = 0;
        isReady = false;
    }

    public synchronized void done() {
        isReady = true;
        notify();
    }

    // blocking method to wait until stats are ready
    public synchronized void getReady() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                // do noting
            }
        }
    }
}
