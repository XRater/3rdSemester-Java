package com.mit.spbau.kirakosian.testing;

import com.mit.spbau.kirakosian.controller.Controller;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.ServerTime;
import com.mit.spbau.kirakosian.options.metrics.impl.TaskTime;
import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.parameters.impl.ArraySize;
import com.mit.spbau.kirakosian.options.parameters.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.QueriesNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.Delay;
import com.mit.spbau.kirakosian.servers.Servers;

import java.io.IOException;
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
        requiredOptions.add(Delay.class);
        requiredOptions.add(QueriesNumber.class);

        supportedMetrics.add(TaskTime.class);
        supportedMetrics.add(ServerTime.class);
        supportedMetrics.add(ClientTime.class);
    }

    public static Set<Class<? extends ParameterOptionMeta>> getRequiredOptions() {
        return requiredOptions;
    }

    public static Set<Class<? extends MetricMeta>> getSupportedMetrics() {
        return supportedMetrics;
    }

    public static void startTest(final TestOptions options) {
        options.setServerType(Servers.ServerType.Simple);
        options.print();
        final ServerTest serverTest = new ServerTest(options);
        final TestResults results = new TestResults(options);
        try {
            serverTest.startTest(results);
        } catch (final IOException e) {
            e.printStackTrace();
            Controller.cancel();
        }

        Controller.calculationsCompleted(results);
    }

    public static void startTestDebug(final TestOptions options) {
        options.setServerType(Servers.ServerType.Simple);
        options.print();
        ServerTest serverTest = new ServerTest(options);
        TestResults results = new TestResults(options);
        try {
            serverTest.startTest(results);
        } catch (final IOException e) {
            e.printStackTrace();
            Controller.cancel();
        }

        Controller.calculationsCompletedDebug(results);

        options.setServerType(Servers.ServerType.Blocking);
        options.print();
        serverTest = new ServerTest(options);
        results = new TestResults(options);
        try {
            serverTest.startTest(results);
        } catch (final IOException e) {
            e.printStackTrace();
            Controller.cancel();
        }

        Controller.calculationsCompletedDebug(results);

        options.setServerType(Servers.ServerType.NonBlocking);
        options.print();
        serverTest = new ServerTest(options);
        results = new TestResults(options);
        try {
            serverTest.startTest(results);
        } catch (final IOException e) {
            e.printStackTrace();
            Controller.cancel();
        }

        Controller.calculationsCompletedDebug(results);

    }

    public static void main(final String[] args) {
        final TestOptions options = new TestOptions();
        options.setAlteringOptionMeta(ArraySize.class);
        options.setOption(QueriesNumber.class, 25);
        options.setOption(ArraySize.class, 5000);
        options.setOption(ClientsNumber.class, 25);
        options.setOption(Delay.class, 200);
        options.setDelta(1000);
        options.setLowerBound(1000);
        options.setUpperBound(10000);
        options.setServerType(Servers.ServerType.Simple);

        options.addMetric(TaskTime.class);
        options.addMetric(ClientTime.class);
        options.addMetric(ServerTime.class);

        String error = options.validate();
        if (error != null) {
            System.out.println(error);
            return;
        }

        startTestDebug(options);

        options.setAlteringOptionMeta(ClientsNumber.class);
        options.setDelta(10);
        options.setLowerBound(10);
        options.setUpperBound(100);

        error = options.validate();
        if (error != null) {
            System.out.println(error);
            return;
        }

        startTestDebug(options);

        options.setAlteringOptionMeta(Delay.class);
        options.setDelta(100);
        options.setLowerBound(0);
        options.setUpperBound(900);

        error = options.validate();
        if (error != null) {
            System.out.println(error);
            return;
        }

        startTestDebug(options);

    }
}
