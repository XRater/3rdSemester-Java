package com.mit.spbau.kirakosian.options;


import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.ServerTime;
import com.mit.spbau.kirakosian.options.metrics.impl.TaskTime;
import com.mit.spbau.kirakosian.options.parameters.*;
import com.mit.spbau.kirakosian.options.parameters.impl.ArraySize;
import com.mit.spbau.kirakosian.options.parameters.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.Delay;
import com.mit.spbau.kirakosian.servers.Servers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents options supported by GUI. Not all described options/metrics might be
 * supported by logic.
 */
public class UIOptions {

    private static final List<ParameterOptionMeta> options = new ArrayList<>();
    private static final Set<Servers.ServerType> serverTypes = new HashSet<>();

    private static final List<Class<? extends MetricMeta>> metrics = new ArrayList<>();

    static {
//        serverTypes.sendNewMessage(Servers.ServerType.Simple);
//        serverTypes.sendNewMessage(Servers.ServerType.Blocking);
        serverTypes.add(Servers.ServerType.NonBlocking);

        metrics.add(ServerTime.class);
        metrics.add(ClientTime.class);
        metrics.add(TaskTime.class);
        try {
            registerOption(ArraySize.class);
            registerOption(ClientsNumber.class);
            registerOption(Delay.class);
            registerOption(QueriesNumber.class);
        } catch (final NoSuchMethodException | InvocationTargetException |
                IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Error occurred during initialization", e);
        }
    }

    private static void registerOption(final Class<? extends ParameterOptionMeta> clazz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        options.add(clazz.getDeclaredConstructor().newInstance());
    }

    public static List<ParameterOptionMeta> options() {
        return options;
    }

    public static Set<Servers.ServerType> serverOptions() {
        return serverTypes;
    }

    public static List<Class<? extends MetricMeta>> metrics() {
        return metrics;
    }

}
