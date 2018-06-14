package com.mit.spbau.kirakosian.options.parameters.impl;

import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;

public class ArraySize implements ParameterOptionMeta {

    @Override
    public String name() {
        return "Array arraySize";
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
        return 10000;
    }

    @Override
    public boolean mayAlter() {
        return true;
    }
}
