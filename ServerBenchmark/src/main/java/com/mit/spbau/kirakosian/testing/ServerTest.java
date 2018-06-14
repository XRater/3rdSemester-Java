package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.connector.ApplicationConnector;
import com.mit.spbau.kirakosian.options.TestOptions;
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
        final Server server = Servers.createServer(options.serverType());
//        server.setServerActionListener(stats);
        stats.clear();

        // wait for second application
        final ApplicationConnector connector = new ApplicationConnector(options, stats);
        connector.waitForNewClient();
/*
        for (int currentValue = options.lowerBound();
             currentValue <= options.upperBound();
             currentValue += options.delta()) {

            server.init();
            connector.startTestCase();

            stats.getReady(); // waiting for test end

            server.shutDown();
            updateResult(results);
            stats.clear();
        }
*/
    }

    private void updateResult(final TestResults results) {
        //TODO
    }

}
