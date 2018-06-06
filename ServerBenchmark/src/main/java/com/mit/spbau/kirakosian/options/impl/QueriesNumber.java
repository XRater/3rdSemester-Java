package com.mit.spbau.kirakosian.options.impl;

import com.mit.spbau.kirakosian.options.ParameterOptionMeta;

public class QueriesNumber implements ParameterOptionMeta {

    @Override
    public String name() {
        return "Number of queries";
    }

    @Override
    public String description() {
        return "Number of queries from each client";
    }

    @Override
    public int minValue() {
        return 0;
    }

    @Override
    public int maxValue() {
        return 100;
    }
}
