package com.mit.spbau.kirakosian.options;

import com.mit.spbau.kirakosian.ServerTest;
import com.mit.spbau.kirakosian.servers.Servers;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all chosen in GUI options. This class is used to pass these options fro, GUI to logic.
 * Every required by logic option should must be set.
 *
 * Also any valid option class must be taken correctly by logic. See {@link TestOptions#validate()}
 * method for more information.
 */
public class TestOptions {

    private final Map<Class<? extends ParameterOptionMeta>, Integer> options = new HashMap<>();
    private Servers.ServerType serverType;

    private ParameterOptionMeta alteringOption;
    private int lowerBound;
    private int upperBound;
    private int delta;

    public Map<Class<? extends ParameterOptionMeta>, Integer> options() {
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

    public void setOption(final Class<? extends ParameterOptionMeta> option, final Integer value) {
        options.put(option, value);
    }

    public Integer getOption(final Class<? extends ParameterOptionMeta> meta) {
        return options.get(meta);
    }

    /**
     * The method checks if provided options are legal (for example, altering
     * property was provided).
     *
     * @return string with error message or null if data was legal.
     */
    public String validate() {
        for (final Class<? extends ParameterOptionMeta> option : ServerTest.getRequiredOptions()) {
            if (options.get(option) == null && alteringOption.getClass() != option) {
                return "Option " + option.getSimpleName() + " was not provided";
            }
        }
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
