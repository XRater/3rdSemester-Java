package com.mit.spbau.kirakosian;

import com.mit.spbau.kirakosian.options.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.impl.ArraySize;
import com.mit.spbau.kirakosian.options.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.impl.TimeSpace;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerTest {

    private final static Set<Class<? extends ParameterOptionMeta>> requiredOptions = new HashSet<>();

    static {
        requiredOptions.add(ArraySize.class);
        requiredOptions.add(ClientsNumber.class);
        requiredOptions.add(TimeSpace.class);
        requiredOptions.add(QueriesNumber.class);
    }

    public static Set<Class<? extends ParameterOptionMeta>> getRequiredOptions() {
        return requiredOptions;
    }

    public static void test(final TestOptions options) {
        System.out.println(options.serverType().name());
        for (final Map.Entry<Class<? extends ParameterOptionMeta>, Integer> entry : options.options().entrySet()) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
        }
        System.out.println(options.alteringOption().name());
        System.out.println(options.lowerBound());
        System.out.println(options.upperBound());
        System.out.println(options.delta());
        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

    }

}
