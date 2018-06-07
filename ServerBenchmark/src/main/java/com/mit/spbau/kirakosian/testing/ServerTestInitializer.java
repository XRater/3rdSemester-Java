package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.controller.Controller;
import com.mit.spbau.kirakosian.options.UIOptions;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.QueryTime;
import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.parameters.impl.ArraySize;
import com.mit.spbau.kirakosian.options.parameters.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.TimeSpace;

import java.util.HashSet;
import java.util.Set;

public class ServerTestInitializer {

    // this two fields must correspond with ServerTest class (actually, this class might have
    // different implementations)
    private final static Set<Class<? extends ParameterOptionMeta>> requiredOptions = new HashSet<>();
    private final static Set<Class<? extends MetricMeta>> supportedMetrics = new HashSet<>();

    static {
        requiredOptions.add(ArraySize.class);
        requiredOptions.add(ClientsNumber.class);
        requiredOptions.add(TimeSpace.class);
        requiredOptions.add(QueriesNumber.class);

        supportedMetrics.add(QueryTime.class);
        supportedMetrics.add(ClientTime.class);
    }

    public static Set<Class<? extends ParameterOptionMeta>> getRequiredOptions() {
        return requiredOptions;
    }

    public static Set<Class<? extends MetricMeta>> getSupportedMetrics() {
        return supportedMetrics;
    }

    public static void startTest(final TestOptions options) {
        final ServerTest serverTest = new ServerTest(options);
        final TestResults results = new TestResults(options);
        serverTest.startTest(results);

        Controller.calculationsCompleted(results);
    }
}
