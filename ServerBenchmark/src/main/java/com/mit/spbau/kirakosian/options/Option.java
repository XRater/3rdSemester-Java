package com.mit.spbau.kirakosian.options;

public class Option {

    private final ParameterOptionMeta meta;
    private final int value;

    public Option(final int value, final ParameterOptionMeta meta) {
        this.value = value;
        this.meta = meta;
    }

    public ParameterOptionMeta meta() {
        return meta;
    }

    public int value() {
        return value;
    }
}
