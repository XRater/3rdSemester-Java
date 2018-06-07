package com.mit.spbau.kirakosian.ui.scenes;

import com.mit.spbau.kirakosian.options.Utils;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.metrics.MetricResult;
import com.mit.spbau.kirakosian.testing.TestResults;
import com.mit.spbau.kirakosian.ui.Scene;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Map;

public class ResultsScene extends Scene {

    public void acceptResults(final TestResults results) {
        final Class<? extends MetricMeta> meta = results.options().metrics().iterator().next();
        System.out.println(meta.getSimpleName());
        JFreeChart xyLineChart = ChartFactory.createXYLineChart("My Chart",
                "Years","Number of Schools",
                makeDataset(results),
                PlotOrientation.VERTICAL,
                true,true,false);
        add(new ChartPanel(xyLineChart));

    }

    private XYDataset makeDataset(final TestResults results) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        for (final Class<? extends MetricMeta> meta : results.options().metrics()) {
            final String name = Utils.getInstance(meta).name();
            final XYSeries series = new XYSeries(name);

            final MetricResult result = results.getResultsForMetric(meta);
            for (final Map.Entry<Integer, Integer> entry : result.results().entrySet()) {
                series.add(entry.getKey(), entry.getValue());
            }

            dataset.addSeries(series);
        }
        return dataset;
    }
}
