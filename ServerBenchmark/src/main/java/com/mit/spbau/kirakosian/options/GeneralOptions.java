package com.mit.spbau.kirakosian.options;


import com.mit.spbau.kirakosian.options.impl.ArraySize;
import com.mit.spbau.kirakosian.options.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.impl.TimeSpace;
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
public class GeneralOptions {

    private static final List<ParameterOptionMeta> options = new ArrayList<>();
    private static final Set<Servers.ServerType> serverTypes = new HashSet<>();

    static {
        serverTypes.add(Servers.ServerType.Blocking);
        serverTypes.add(Servers.ServerType.NonBlocking);

        try {
            registerOption(ArraySize.class);
            registerOption(ClientsNumber.class);
            registerOption(TimeSpace.class);
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

}
