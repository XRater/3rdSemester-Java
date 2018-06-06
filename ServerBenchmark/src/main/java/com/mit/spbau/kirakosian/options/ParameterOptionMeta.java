package com.mit.spbau.kirakosian.options;

public interface ParameterOptionMeta {

    String name();

    int minValue();

    int maxValue();

    String description();

    boolean mayAlter();
}
