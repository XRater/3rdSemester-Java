package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.connector.ApplicationConnector;
import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.metrics.impl.TaskTime;
import com.mit.spbau.kirakosian.servers.Server;
import com.mit.spbau.kirakosian.servers.Servers;

import java.io.IOException;

class ServerTest {

    private final TestOptions options;
    private final TestingStats stats = new TestingStats();

    ServerTest(final TestOptions options) {
        this.options = options;
    }

    /**
     * This function runs test and stores information in {@link TestResults} class.
     */
    void startTest(final TestResults results) throws IOException {
        // set up server
//        server.setServerActionListener(stats);
        stats.clear();

        // wait for second application
        final ApplicationConnector connector = new ApplicationConnector(options, stats);
        connector.waitForNewClient();
        System.out.println("Starting");

        for (int currentValue = options.lowerBound();
             currentValue <= options.upperBound();
             currentValue += options.delta()) {

            final Server server = Servers.createServer(options.serverType());
            server.setServerActionListener(stats);
            server.start();

            System.out.println("Test case " + currentValue);
            connector.startTestCase();

            server.shutDown();
            updateResult(results, currentValue);
            stats.clear();
        }

        System.out.println("End of testing");
    }

    private void updateResult(final TestResults results, final int value) {
        results.addPoint(TaskTime.class, value, (int) stats.getTaskTime());
    }

}
