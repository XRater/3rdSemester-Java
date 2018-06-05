package com.mit.spbau.kirakosian.options.impl;

import com.mit.spbau.kirakosian.options.ParameterOptionMeta;

public class ArraySize implements ParameterOptionMeta {

    @Override
    public String name() {
        return "Array size";
    }

    @Override
    public String description() {
        return "Size of array for each query";
    }

    @Override
    public int minValue() {
        return 0;
    }

    @Override
    public int maxValue() {
        return 1_000_000;
    }
}
