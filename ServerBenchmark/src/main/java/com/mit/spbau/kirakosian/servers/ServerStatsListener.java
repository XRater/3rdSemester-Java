package com.mit.spbau.kirakosian.servers;

public interface ServerStatsListener {

    default void done() {}

    default void timeForTask(final long time) {}

    default void timeForClientOnServer(final long time) {}

    void fail(final Exception e);
}
