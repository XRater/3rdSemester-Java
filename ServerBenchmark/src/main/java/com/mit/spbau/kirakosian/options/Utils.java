package com.mit.spbau.kirakosian.options;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.ServerTime;
import com.mit.spbau.kirakosian.options.metrics.impl.TaskTime;
import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.parameters.impl.ArraySize;
import com.mit.spbau.kirakosian.options.parameters.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.Delay;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<Class<? extends MetricMeta>, MetricMeta> metricMap = new HashMap<>();
    private static final Map<Class<? extends ParameterOptionMeta>, ParameterOptionMeta> optionsMap = new HashMap<>();

    static {
        try {
            registerOption(ArraySize.class);
            registerOption(ClientsNumber.class);
            registerOption(Delay.class);
            registerOption(QueriesNumber.class);

            registerMetric(ClientTime.class);
            registerMetric(ServerTime.class);
            registerMetric(TaskTime.class);
        } catch (final NoSuchMethodException | InvocationTargetException |
                IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Error occurred during initialization", e);
        }
    }

    private static void registerOption(final Class<? extends ParameterOptionMeta> meta) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        optionsMap.put(meta, meta.getDeclaredConstructor().newInstance());
    }

    private static void registerMetric(final Class<? extends MetricMeta> meta) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        metricMap.put(meta, meta.getDeclaredConstructor().newInstance());
    }

    public static MetricMeta getMetricInstance(final Class<? extends MetricMeta> meta) {
        if (metricMap.containsKey(meta)) {
            return metricMap.get(meta);
        } else {
            final MetricMeta instance;
            try {
                instance = meta.getConstructor().newInstance();
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(); // should never happen
            }
            metricMap.put(meta, instance);
            return instance;
        }
    }

    public static ParameterOptionMeta getOptionInstance(final Class<? extends ParameterOptionMeta> meta) {
        if (optionsMap.containsKey(meta)) {
            return optionsMap.get(meta);
        } else {
            final ParameterOptionMeta instance;
            try {
                instance = meta.getConstructor().newInstance();
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(); // should never happen
            }
            optionsMap.put(meta, instance);
            return instance;
        }
    }

}
