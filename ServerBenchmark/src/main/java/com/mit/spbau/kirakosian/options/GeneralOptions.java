package com.mit.spbau.kirakosian.options;


import com.mit.spbau.kirakosian.options.impl.ArraySize;
import com.mit.spbau.kirakosian.options.impl.ClientsNumber;
import com.mit.spbau.kirakosian.servers.Servers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class GeneralOptions {

    private static final Set<ParameterOptionMeta> options = new HashSet<>();
    private static final Set<Servers.ServerType> serverTypes = new HashSet<>();

    static {
        serverTypes.add(Servers.ServerType.Blocking);
        serverTypes.add(Servers.ServerType.NonBlocking);

        try {
            registerOption(ArraySize.class);
            registerOption(ClientsNumber.class);
        } catch (final NoSuchMethodException | InvocationTargetException |
                IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Error occurred during initialization", e);
        }
    }

    public static void registerOption(final Class<? extends ParameterOptionMeta> clazz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        options.add(clazz.getDeclaredConstructor().newInstance());
    }

    public static Set<ParameterOptionMeta> options() {
        return options;
    }

    public static Set<Servers.ServerType> serverOptions() {
        return serverTypes;
    }

}
