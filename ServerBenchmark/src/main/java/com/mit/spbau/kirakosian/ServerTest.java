package com.mit.spbau.kirakosian;

import com.mit.spbau.kirakosian.options.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.TestOptions;

import java.util.Map;

public class ServerTest {

    public static void test(final TestOptions options) {
        System.out.println(options.serverType().name());
        for (final Map.Entry<ParameterOptionMeta, Integer> entry : options.options().entrySet()) {
            System.out.println(entry.getKey().name() + " " + entry.getValue());
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
