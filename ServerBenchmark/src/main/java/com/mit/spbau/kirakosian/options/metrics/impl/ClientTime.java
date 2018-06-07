package com.mit.spbau.kirakosian.options.metrics.impl;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;

public class ClientTime implements MetricMeta {

    //TODO
    @Override
    public String name() {
        return "Time of one client";
    }

    @Override
    public String description() {
        return "Time of processing one client";
    }
}
