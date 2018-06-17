package com.mit.spbau.kirakosian.controller;

import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.options.Utils;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.MetricResult;
import com.mit.spbau.kirakosian.options.metrics.impl.ClientTime;
import com.mit.spbau.kirakosian.options.metrics.impl.ServerTime;
import com.mit.spbau.kirakosian.options.metrics.impl.TaskTime;
import com.mit.spbau.kirakosian.options.parameters.impl.ArraySize;
import com.mit.spbau.kirakosian.options.parameters.impl.ClientsNumber;
import com.mit.spbau.kirakosian.options.parameters.impl.Delay;
import com.mit.spbau.kirakosian.options.parameters.impl.QueriesNumber;
import com.mit.spbau.kirakosian.testing.TestResults;

import java.io.*;
import java.util.Map;

class ResultsWriter {

    static void saveResults(final TestResults results) {
        final String name = getName(results);
        final File file = new File("results/logs/" + name);
        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        final TestOptions options = results.options();
        //noinspection TryWithIdenticalCatches
        try (final Writer writer = new PrintWriter(file)) {
            writer.write("Server type: " + options.serverType().name());
            writer.write("Array size: " + options.getOption(ArraySize.class) + "\n");
            writer.write("Clients number: " + options.getOption(ClientsNumber.class) + "\n");
            writer.write("Delay: " + options.getOption(Delay.class) + "\n");
            writer.write("Queries number: " + options.getOption(QueriesNumber.class) + "\n");

            writeAlteringMetric(writer, options);

            writeResultsForMetric(writer, results, TaskTime.class);
            writeResultsForMetric(writer, results, ClientTime.class);
            writeResultsForMetric(writer, results, ServerTime.class);
        } catch (final FileNotFoundException e) {
            // should never happen
        } catch (final IOException e) {
            // just ignore
        }
    }

    private static void writeAlteringMetric(final Writer writer, final TestOptions options) throws IOException {
        writer.write("Altering option: " + options.alteringOption().getSimpleName() + "\n");
        writer.write(options.lowerBound() + " " + options.upperBound() + " " + options.delta() + "\n");
    }

    private static void writeResultsForMetric(final Writer writer,
                                              final TestResults results,
                                              final Class<? extends MetricMeta> meta) throws IOException {
        writer.write(meta.getSimpleName() + "\n");
        final MetricResult metricResult = results.getResultsForMetric(meta);
        writer.write(metricResult.results().size() + "\n");
        for (final Map.Entry<Integer, Number> entry : metricResult.results().entrySet()) {
            writer.write(entry.getKey() + " " + entry.getValue() + "\n");
        }
    }

    private static String getName(final TestResults results) {
        return results.options().serverType() + "_" + results.options().alteringOption().getSimpleName() + ".txt";
    }

}
