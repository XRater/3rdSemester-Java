package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.connector.ApplicationConnector;
import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.ServerTime;
import com.mit.spbau.kirakosian.options.metrics.impl.TaskTime;
import com.mit.spbau.kirakosian.servers.Server;
import com.mit.spbau.kirakosian.servers.Servers;
import com.mit.spbau.kirakosian.servers.exceptions.AbortException;

import java.io.IOException;
import java.util.concurrent.Executor;

class ServerTest {

    private final TestOptions options;
    private final StatsListener stats = new StatsListener();

    ServerTest(final TestOptions options) {
        this.options = options;
    }

    /**
     * This function runs test and stores information in {@link TestResults} class.
     */
    void startTest(final TestResults results) throws IOException {
        stats.clear();

        // wait for second application
        final ApplicationConnector connector = new ApplicationConnector(options, stats); // lethal error
        connector.waitForNewClient(); // lethal error
        System.out.println("Starting");

        for (int currentValue = options.lowerBound();
             currentValue <= options.upperBound();
             currentValue += options.delta()) {

            final Server server;
            try {
                server = Servers.createServer(options.serverType());
            } catch (final AbortException e) {
                // failed to create server, just skip test case
                continue;
            }
            server.setServerActionListener(stats);
            server.start();

            System.out.println("Test case " + currentValue);
            connector.startTestCase(); // lethal error

            server.shutDown();
            updateResult(results, currentValue);
            stats.clear();
        }

        System.out.println("End of testing");
    }

    private void updateResult(final TestResults results, final int value) {
        if (stats.anyErrorsOccurred()) {
            System.out.println("Test case was skipped cause errors");
            for (Exception e : stats.getErrors()) {
                e.printStackTrace();
            }
            return;
        }
        results.addPoint(TaskTime.class, value, stats.getTaskTime());
        results.addPoint(ClientTime.class, value, stats.getClientTime());
        results.addPoint(ServerTime.class, value, stats.getServerTime());
    }

}
