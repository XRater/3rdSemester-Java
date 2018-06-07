package com.mit.spbau.kirakosian.options.metrics;

import java.util.HashMap;
import java.util.Map;

public class MetricResult {

    private final Class<? extends MetricMeta> meta;

    private final Map<Integer, Integer> results = new HashMap<>();

    public MetricResult(final Class<? extends MetricMeta> meta) {
        this.meta = meta;
    }

    public Class<? extends MetricMeta> meta() {
        return meta;
    }

    public void addResult(final Integer parameter, final Integer value) {
        results.put(parameter, value);
    }

    public Map<Integer, Integer> results() {
        return results;
    }
}
