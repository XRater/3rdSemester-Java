package com.mit.spbau.kirakosian.options.parameters.impl;

import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;

public class ClientsNumber implements ParameterOptionMeta {

    @Override
    public String name() {
        return "Number of clients";
    }

    @Override
    public String description() {
        return "Number of clients working at the same time";
    }

    @Override
    public int minValue() {
        return 0;
    }

    @Override
    public int maxValue() {
        return 40;
    }

    @Override
    public boolean mayAlter() {
        return true;
    }
}
