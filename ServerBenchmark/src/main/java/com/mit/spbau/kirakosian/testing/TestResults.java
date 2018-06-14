package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.MetricResult;

import java.util.HashMap;
import java.util.Map;

public class TestResults {

    private final Map<Class<? extends MetricMeta>, MetricResult> results = new HashMap<>();

    private final TestOptions options;

    public TestResults(final TestOptions options) {
        this.options = options;
        for (final Class<? extends MetricMeta> meta : options.metrics()) {
            results.put(meta, new MetricResult(meta));
        }
    }

    public TestOptions options() {
        return options;
    }

    public void addPoint(final Class<? extends MetricMeta> meta, final Integer parameter, final Integer value) {
        if (results.containsKey(meta)) {
            results.get(meta).addResult(parameter, value);
        }
    }

    public MetricResult getResultsForMetric(final Class<? extends MetricMeta> meta) {
        return results.get(meta);
    }
}
