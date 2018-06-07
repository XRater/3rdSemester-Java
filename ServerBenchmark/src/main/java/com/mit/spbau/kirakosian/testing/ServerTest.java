package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;

class ServerTest {

    private final TestOptions options;

    ServerTest(final TestOptions options) {
        this.options = options;
    }

    void startTest(final TestResults results) {
        int c = 1;
        for (final Class<? extends MetricMeta> meta : options.metrics()) {
            for (int i = 0; i < 10; i++) {
                results.addPoint(meta, i, i * c);
            }
            c++;
        }
    }
}
