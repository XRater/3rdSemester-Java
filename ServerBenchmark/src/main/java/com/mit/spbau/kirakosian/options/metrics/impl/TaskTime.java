package com.mit.spbau.kirakosian.options.metrics.impl;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;

public class TaskTime implements MetricMeta {

    @Override
    public String name() {
        return "Task time";
    }

    @Override
    public String description() {
        return "Time for completing one task";
    }
}
