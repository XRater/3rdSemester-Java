package com.mit.spbau.kirakosian.options.parameters.impl;

import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;

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
        return 1;
    }

    @Override
    public int maxValue() {
        return 53;
    }

    @Override
    public boolean mayAlter() {
        return false;
    }

    @Override
    public String shortName() {
        return "Q";
    }
}
