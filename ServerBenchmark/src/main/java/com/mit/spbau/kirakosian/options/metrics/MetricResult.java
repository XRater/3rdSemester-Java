package com.mit.spbau.kirakosian.options.metrics;

import java.util.Map;
import java.util.TreeMap;

public class MetricResult {

    private final Class<? extends MetricMeta> meta;

    private final Map<Integer, Number> results = new TreeMap<>();

    public MetricResult(final Class<? extends MetricMeta> meta) {
        this.meta = meta;
    }

    public Class<? extends MetricMeta> meta() {
        return meta;
    }

    public void addResult(final Integer parameter, final Number value) {
        results.put(parameter, value);
    }

    public Map<Integer, Number> results() {
        return results;
    }
}
