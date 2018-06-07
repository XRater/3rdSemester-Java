package com.mit.spbau.kirakosian.options.metrics.impl;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;

public class QueryTime implements MetricMeta {

    //TODO
    @Override
    public String name() {
        return "Query time";
    }

    @Override
    public String description() {
        return "Time required for query evaluation";
    }
}
