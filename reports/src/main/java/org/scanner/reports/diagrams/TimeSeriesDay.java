package org.scanner.reports.diagrams;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.text.SimpleDateFormat;

public class TimeSeriesDay extends ApplicationFrame {
    private static final long serialVersionUID = 1L;
    static String TITLE = "Курс валюты, цена нефти марки Brent";

    public TimeSeriesDay(final String title) {
        super(title);
        final XYDataset dataset = Dataset.createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 480));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);
    }

    public static JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Валюта, нефть", null, null,
                dataset, true, true, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(232, 232, 232));
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYSplineRenderer spline = new XYSplineRenderer();
        spline.setSeriesShapesVisible (0, false);
        plot.setRenderers(new XYItemRenderer[] {spline});

        // Скрытие осевых линий
        ValueAxis vaxis = plot.getDomainAxis();
        vaxis.setAxisLineVisible(false);
        vaxis = plot.getRangeAxis();
        vaxis.setAxisLineVisible(false);

        plot.getRenderer().setSeriesPaint(2, new Color(64, 255, 64));
        // Определение временной оси
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        // Формат отображения осевых меток
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM"));
        return chart;
    }
}
