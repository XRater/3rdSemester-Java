package com.mit.spbau.kirakosian.options.metrics.impl;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;

public class ServerTime implements MetricMeta {

    @Override
    public String name() {
        return "Server time";
    }

    @Override
    public String description() {
        return "Time elapsed between getting task and sending answer";
    }

    @Override
    public String shortName() {
        return "S";
    }
}
