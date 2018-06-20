package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.servers.ServerStatsListener;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class StatsListener implements ServerStatsListener {

    private final List<Exception> errors = new ArrayList<>();

    private final List<Long> taskTimes = new ArrayList<>();
    private final List<Long> serverTimes = new ArrayList<>();
    private final List<Long> clientTimes = new ArrayList<>();

    public void clear() {
        taskTimes.clear();
        serverTimes.clear();
        clientTimes.clear();
        errors.clear();
    }

    public boolean anyErrorsOccurred() {
        return errors.size() != 0;
    }

    public List<Exception> getErrors() {
        return errors;
    }

    public double getTaskTime() {
        return taskTimes.stream().mapToLong(e -> e).average().orElse(0);
    }

    public double getServerTime() {
        return serverTimes.stream().mapToLong(e -> e).average().orElse(0);
    }

    public double getClientTime() {
        return clientTimes.stream().mapToLong(e -> e).average().orElse(0);
    }

    public void timeForClientOnClient(final long time) {
        clientTimes.add(time);
    }

    @Override
    public void timeForClientOnServer(final long time) {
        serverTimes.add(time);
    }

    @Override
    public void timeForTask(final long time) {
        taskTimes.add(time);
    }

    @Override
    public void fail(final Exception e) {
        errors.add(e);
    }

}
