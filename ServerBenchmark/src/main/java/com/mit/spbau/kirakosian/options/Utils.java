package com.mit.spbau.kirakosian.options;

import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.QueryTime;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<Class<? extends MetricMeta>, MetricMeta> metricMap = new HashMap<>();

    static {
        try {
//            registerOption(ArraySize.class);
//            registerOption(ClientsNumber.class);
//            registerOption(TimeSpace.class);
//            registerOption(QueriesNumber.class);

            registerMetric(ClientTime.class);
            registerMetric(QueryTime.class);
        } catch (final NoSuchMethodException | InvocationTargetException |
                IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Error occurred during initialization", e);
        }
    }

    private static void registerMetric(final Class<? extends MetricMeta> meta) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        metricMap.put(meta, meta.getDeclaredConstructor().newInstance());
    }

    public static MetricMeta getInstance(final Class<? extends MetricMeta> meta) {
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
}
