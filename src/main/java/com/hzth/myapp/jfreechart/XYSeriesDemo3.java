package com.hzth.myapp.jfreechart;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class XYSeriesDemo3 extends ApplicationFrame {

	private static final long serialVersionUID = 3588501260943279540L;

	public XYSeriesDemo3(String s) {
		super(s);
		IntervalXYDataset intervalxydataset = createDataset();
		JFreeChart jfreechart = createChart(intervalxydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartpanel);
	}

	private static IntervalXYDataset createDataset() {
		XYSeries xyseries = new XYSeries("Random Data");
		xyseries.add(1.0D, 400.19999999999999D);
		xyseries.add(5D, 294.10000000000002D);
		xyseries.add(4D, 100D);
		xyseries.add(12.5D, 734.39999999999998D);
		xyseries.add(17.300000000000001D, 453.19999999999999D);
		xyseries.add(21.199999999999999D, 500.19999999999999D);
		xyseries.add(21.899999999999999D, null);
		xyseries.add(25.600000000000001D, 734.39999999999998D);
		xyseries.add(30D, 453.19999999999999D);
		XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
		return xyseriescollection;
	}

	private static JFreeChart createChart(IntervalXYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createXYBarChart("XY Series Demo 3", "X", false, "Y", intervalxydataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		NumberAxis numAxis = new NumberAxis("YYY");
		numAxis.setRange(0, 1d);
		xyplot.setRangeAxis(numAxis);

		IntervalMarker intervalmarker = new IntervalMarker(0D, 0.2);
		intervalmarker.setLabel("Target Range");
		intervalmarker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		intervalmarker.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		intervalmarker.setPaint(Color.BLACK);
		xyplot.addRangeMarker(intervalmarker, Layer.BACKGROUND);

		IntervalMarker intervalmarker2 = new IntervalMarker(0.2, 0.4);
		intervalmarker2.setLabel("Target Range");
		intervalmarker2.setLabelAnchor(RectangleAnchor.RIGHT);
		intervalmarker2.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		intervalmarker2.setPaint(Color.BLUE);
		xyplot.addRangeMarker(intervalmarker2, Layer.BACKGROUND);

		IntervalMarker intervalmarker3 = new IntervalMarker(0.4, 0.6);
		intervalmarker3.setLabel("Target Range");
		intervalmarker3.setLabelAnchor(RectangleAnchor.RIGHT);
		intervalmarker3.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		intervalmarker3.setPaint(Color.CYAN);
		xyplot.addRangeMarker(intervalmarker3, Layer.BACKGROUND);

		IntervalMarker intervalmarker4 = new IntervalMarker(0.6, 0.8);
		intervalmarker4.setLabel("Target Range");
		intervalmarker4.setLabelAnchor(RectangleAnchor.RIGHT);
		intervalmarker4.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		intervalmarker4.setPaint(Color.GREEN);
		xyplot.addRangeMarker(intervalmarker4, Layer.BACKGROUND);

		IntervalMarker intervalmarker5 = new IntervalMarker(0.8, 1);
		intervalmarker5.setLabel("Target Range");
		intervalmarker5.setLabelAnchor(RectangleAnchor.RIGHT);
		intervalmarker5.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		intervalmarker5.setPaint(Color.ORANGE);
		xyplot.addRangeMarker(intervalmarker5, Layer.BACKGROUND);

		String[] sv = new String[] { "", "A层级", "", "b层级", "", "c层级", "", "d层级", "", "e层级", "" };
		SymbolAxis axis = new SymbolAxis("aaaa", sv);
		axis.setRange(0d, 1);
		xyplot.setRangeAxis(1, axis);
		return jfreechart;
	}

	public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[]) {
		XYSeriesDemo3 xyseriesdemo3 = new XYSeriesDemo3("JFreeChart: XYSeriesDemo3.java");
		xyseriesdemo3.pack();
		RefineryUtilities.centerFrameOnScreen(xyseriesdemo3);
		xyseriesdemo3.setVisible(true);
	}
}
