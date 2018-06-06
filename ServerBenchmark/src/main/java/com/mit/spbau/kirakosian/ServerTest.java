package com.mit.spbau.kirakosian;

import com.mit.spbau.kirakosian.options.ParameterOptionMeta;
import com.mit.spbau.kirakosian.servers.Servers;

import java.util.Map;

public class ServerTest {

    public static void test(final Servers.ServerType serverType, final Map<ParameterOptionMeta, Integer> options) {
        System.out.println(serverType.name());
        for (final Map.Entry<ParameterOptionMeta, Integer> entry : options.entrySet()) {
            System.out.println(entry.getKey().name() + " " + entry.getValue());
        }
        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

}
