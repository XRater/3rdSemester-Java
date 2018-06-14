package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.parameters.impl.ArraySize;
import com.mit.spbau.kirakosian.options.parameters.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.Delay;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class AutoOptions {

    private final Map<Class<? extends ParameterOptionMeta>, Integer> map = new HashMap<>();
    private final Class<? extends ParameterOptionMeta> altering;
    private final int delta;
    private final int max;

    public AutoOptions(final TestOptions options) {
        delta = options.delta();
        max = options.upperBound();
        map.putAll(options.options());
        altering = options.alteringOption();
        map.put(altering, options.lowerBound());
    }

    public void next() {
        final int value = map.get(altering);
        map.put(altering, value + delta);
    }

    public boolean notFinished() {
        return map.get(altering) <= max;
    }

    public int clients() {
        return map.get(ClientsNumber.class);
    }

    public int queries() {
        return map.get(QueriesNumber.class);
    }

    public int arraySize() {
        return map.get(ArraySize.class);
    }

    public int delay() {
        return map.get(Delay.class);
    }
}
