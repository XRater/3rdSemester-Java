package com.mit.spbau.kirakosian.options;

import com.mit.spbau.kirakosian.testing.ServerTestInitializer;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;
import com.mit.spbau.kirakosian.servers.Servers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores all chosen in GUI options. This class is used to pass these options fro, GUI to logic.
 * Every required by logic option should must be set.
 *
 * Also any valid option class must be taken correctly by logic. See {@link TestOptions#validate()}
 * method for more information.
 */
public class TestOptions implements Serializable {

    private final Map<Class<? extends ParameterOptionMeta>, Integer> options = new HashMap<>();
    private Servers.ServerType serverType;

    private final Set<Class<? extends MetricMeta>> metrics = new HashSet<>();

    private Class<? extends ParameterOptionMeta> alteringOptionMeta;
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

    public Class<? extends ParameterOptionMeta> alteringOption() {
        return alteringOptionMeta;
    }

    public void setAlteringOptionMeta(final Class<? extends ParameterOptionMeta> alteringOptionMeta) {
        this.alteringOptionMeta = alteringOptionMeta;
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

    public void addMetric(final Class<? extends MetricMeta> metric) {
        metrics.add(metric);
    }

    public boolean requiresMetric(final Class<? extends MetricMeta> metric) {
        return metrics.contains(metric);
    }

    public Set<Class<? extends MetricMeta>> metrics() {
        return metrics;
    }

    /**
     * The method checks if provided options are legal (for example, altering
     * property was provided).
     *
     * @return string with error message or null if data was legal.
     */
    public String validate() {
        if (serverType == null) {
            return "Server type was not provided.";
        }
        if (alteringOptionMeta == null) {
            return "Altering property was not provided.";
        }
        final ParameterOptionMeta alteringOption = Utils.getOptionInstance(alteringOptionMeta);
        if (!alteringOption.mayAlter()) {
            return "Option " + alteringOptionMeta.getSimpleName() + " cannot alter.";
        }
        for (final Class<? extends ParameterOptionMeta> option : ServerTestInitializer.getRequiredOptions()) {
            if (options.get(option) == null && alteringOptionMeta != option) {
                return "Option " + option.getSimpleName() + " was not provided";
            }
        }
        for (final Class<? extends MetricMeta> metric : metrics) {
            if (!ServerTestInitializer.getSupportedMetrics().contains(metric)) {
                return "Metric " + metric.getSimpleName() + " is unsupported";
            }
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

    public void print() {
        for (final Map.Entry<Class<? extends ParameterOptionMeta>, Integer> meta : options.entrySet()) {
            System.out.println(meta.getKey().getSimpleName() + " has value: " + meta.getValue());
        }
        System.out.println(alteringOption().getSimpleName() + " is altering from "
                + lowerBound + " to " + upperBound + " with delta " + delta);
    }
}
