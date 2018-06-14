package com.mit.spbau.kirakosian.servers;

public interface ServerStatsListener {

    default void done() {}

    default void timeForTask(final int time) {}

    default void timeForClientOnServer(final int time) {}
}
