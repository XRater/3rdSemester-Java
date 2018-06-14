package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.client.ArrayClient;
import com.mit.spbau.kirakosian.servers.ServerStatsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class TestingStats implements ServerStatsListener {

    private boolean isReady;
    private int clientsProcessed;

    private List<Long> taskTimes = new ArrayList<>();

    public void clear() {
        clientsProcessed = 0;
        isReady = false;
        taskTimes.clear();
    }

    public synchronized void done() {
        isReady = true;
        notify();
    }

    public double getTaskTime() {
        return taskTimes.stream().mapToLong(e -> e).average().orElse(0);
    }

    @Override
    public void timeForTask(final long time) {
        taskTimes.add(time);
    }

    @Override
    public void fail(final Exception e) {
        e.printStackTrace();
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
