package com.mit.spbau.kirakosian.options.metrics.impl;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;

public class ClientTime implements MetricMeta {

    @Override
    public String name() {
        return "Client time";
    }

    @Override
    public String description() {
        return "Time elapsed between sending task and getting response";
    }

    @Override
    public String shortName() {
        return "C";
    }
}
