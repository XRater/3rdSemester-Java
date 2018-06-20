package com.mit.spbau.kirakosian.options.parameters;

public interface ParameterOptionMeta {

    String name();

    int minValue();

    int maxValue();

    String description();

    boolean mayAlter();

    String shortName();
}
