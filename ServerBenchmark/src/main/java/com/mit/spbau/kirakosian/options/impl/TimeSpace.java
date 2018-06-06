package com.mit.spbau.kirakosian.options.impl;

import com.mit.spbau.kirakosian.options.ParameterOptionMeta;

public class TimeSpace implements ParameterOptionMeta {

    @Override
    public String name() {
        return "Time space between queries";
    }

    @Override
    public String description() {
        return "Time before responce from server and new query";
    }

    @Override
    public int minValue() {
        return 0;
    }

    @Override
    public int maxValue() {
        return 10_000;
    }
}
