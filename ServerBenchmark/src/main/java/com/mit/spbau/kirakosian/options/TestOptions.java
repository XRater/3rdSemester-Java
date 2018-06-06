package com.mit.spbau.kirakosian.options;

import com.mit.spbau.kirakosian.servers.Servers;

import java.util.HashMap;
import java.util.Map;

public class TestOptions {

    private final Map<ParameterOptionMeta, Integer> options = new HashMap<>();
    private Servers.ServerType serverType;

    private ParameterOptionMeta alteringOption;
    private int lowerBound;
    private int upperBound;
    private int delta;

    public Map<ParameterOptionMeta, Integer> options() {
        return options;
    }

    public Servers.ServerType serverType() {
        return serverType;
    }

    public void setServerType(final Servers.ServerType serverType) {
        this.serverType = serverType;
    }

    public ParameterOptionMeta alteringOption() {
        return alteringOption;
    }

    public void setAlteringOption(final ParameterOptionMeta alteringOption) {
        this.alteringOption = alteringOption;
    }

    public int lowerBound() {
        return lowerBound;
    }

    public void setLowerBound(final int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int upperBound() {
        return upperBound;
    }

    public void setUpperBound(final int upperBound) {
        this.upperBound = upperBound;
    }

    public int delta() {
        return delta;
    }

    public void setDelta(final int delta) {
        this.delta = delta;
    }

    public void setOption(final ParameterOptionMeta option, final Integer value) {
        options.put(option, value);
    }

    /**
     * The method checks if provided options are legal (for example, altering
     * property was provided).
     *
     * @return string with error message or null if data was legal.
     */
    public String validate() {
        if (alteringOption == null) {
            return "No altering property was provided";
        }
        if (lowerBound > upperBound) {
            return "Min property value is greater then max value";
        }
        if (lowerBound < alteringOption.minValue()) {
            return "Illegal max value";
        }
        if (upperBound > alteringOption.maxValue()) {
            return "Illegal in value";
        }
        if (delta <= 0) {
            return "Delta must be positive";
        }
        return null;
    }
}
