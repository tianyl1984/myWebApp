package com.hzth.myapp.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class ChartDemo {

	public static void main(String[] args) {
		// barChar1();
		// spiderWebChart();
		// multiLineChart();
		// normalDistri();
		// splineRendererDemo();
		// normalDistribution();
		// lineAndBar();
		// lineAndPoint();
		// categoryPolynomial();
		testDemo();
	}

	private static void testDemo() {

	}

	private static void categoryPolynomial() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String rowKey = "人数";
		dataset.addValue(9, rowKey, "640-620");
		dataset.addValue(16, rowKey, "620-600");
		dataset.addValue(9, rowKey, "600-580");
		dataset.addValue(12, rowKey, "580-560");
		dataset.addValue(9, rowKey, "560-540");
		dataset.addValue(12, rowKey, "540-520");
		dataset.addValue(7, rowKey, "520-500");
		dataset.addValue(16, rowKey, "500-480");
		dataset.addValue(17, rowKey, "480-460");
		dataset.addValue(15, rowKey, "460-440");
		dataset.addValue(16, rowKey, "440-420");
		dataset.addValue(8, rowKey, "420-400");
		dataset.addValue(18, rowKey, "400-380");
		dataset.addValue(4, rowKey, "380-360");
		dataset.addValue(8, rowKey, "360-340");
		dataset.addValue(7, rowKey, "340-320");
		dataset.addValue(11, rowKey, "320-300");
		dataset.addValue(10, rowKey, "300-280");
		dataset.addValue(5, rowKey, "280-260");
		dataset.addValue(2, rowKey, "260-240");
		dataset.addValue(6, rowKey, "240-220");
		dataset.addValue(2, rowKey, "220-200");
		dataset.addValue(4, rowKey, "200-180");
		dataset.addValue(5, rowKey, "180-160");
		dataset.addValue(1, rowKey, "160以下");

		CategoryPlot plot = new CategoryPlot(dataset, new CategoryAxis("分数段"), new NumberAxis("人数"), new LineAndShapeRenderer());

		plot.setDataset(1, dataset);
		plot.setRenderer(1, new CategoryPolynomialRenderer(10, 3));

		// for (int i = 2; i < 10; i++) {
		// plot.setDataset(i, dataset);
		// plot.setRenderer(i, new CategoryPolynomialRenderer(10, i + 1));
		// }

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		JFreeChart localJFreeChart = new JFreeChart("各段人数分布", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		show(localJFreeChart);
	}

	private static void lineAndPoint() {
		DefaultCategoryDataset dataset0 = new DefaultCategoryDataset();
		String rowKey = "分数";
		dataset0.addValue(0d, rowKey, "0");
		dataset0.addValue(0.2d, rowKey, "20");
		dataset0.addValue(0.51d, rowKey, "30");
		dataset0.addValue(0.6d, rowKey, "50");
		dataset0.addValue(0.8d, rowKey, "60");
		dataset0.addValue(0.9d, rowKey, "100");

		NumberAxis numberAxis = new NumberAxis("难度");
		numberAxis.setRange(0.0, 1.0);
		CategoryPlot plot = new CategoryPlot(dataset0, new CategoryAxis("aaaa"), numberAxis, new CategorySplineRenderer());

		CategoryValueMarker marker = new CategoryValueMarker("50", 0.95);
		marker.setPaint(Color.BLACK);
		marker.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 5.0f }, 0.0f));
		marker.setLabel("aaaa");
		marker.setLabelFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 12));
		plot.addRangeMarker(marker, Layer.BACKGROUND);

		JFreeChart localJFreeChart = new JFreeChart("难度图", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		show(localJFreeChart);
	}

	private static void lineAndBar() {
		DefaultCategoryDataset dataset0 = new DefaultCategoryDataset();
		String rowKey = "班级得分率";
		dataset0.addValue(NumberUtil.getRandomNum(), rowKey, "知识点0");
		dataset0.addValue(NumberUtil.getRandomNum(), rowKey, "知识点1");
		dataset0.addValue(NumberUtil.getRandomNum(), rowKey, "知识点2");
		dataset0.addValue(NumberUtil.getRandomNum(), rowKey, "知识点3");
		dataset0.addValue(NumberUtil.getRandomNum(), rowKey, "知识点4");

		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		String rowKey1 = "年级得分率";
		dataset1.addValue(NumberUtil.getRandomNum(), rowKey1, "知识点0");
		dataset1.addValue(NumberUtil.getRandomNum(), rowKey1, "知识点1");
		dataset1.addValue(NumberUtil.getRandomNum(), rowKey1, "知识点2");
		dataset1.addValue(NumberUtil.getRandomNum(), rowKey1, "知识点3");
		dataset1.addValue(NumberUtil.getRandomNum(), rowKey1, "知识点4");

		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		String rowKey2 = "年级得分率";
		dataset2.addValue(NumberUtil.getRandomNum(), rowKey2, "知识点0");
		dataset2.addValue(NumberUtil.getRandomNum(), rowKey2, "知识点1");
		dataset2.addValue(NumberUtil.getRandomNum(), rowKey2, "知识点2");
		dataset2.addValue(NumberUtil.getRandomNum(), rowKey2, "知识点3");
		dataset2.addValue(NumberUtil.getRandomNum(), rowKey2, "知识点4");

		DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
		String rowKey3_0 = "A层";
		String rowKey3_1 = "B层";
		String rowKey3_2 = "C层";
		dataset3.addValue(60d, rowKey3_0, "知识点0");
		dataset3.addValue(30d, rowKey3_1, "知识点0");
		dataset3.addValue(10d, rowKey3_2, "知识点0");
		dataset3.addValue(80d, rowKey3_0, "知识点1");
		dataset3.addValue(10d, rowKey3_1, "知识点1");
		dataset3.addValue(10d, rowKey3_2, "知识点1");
		dataset3.addValue(70d, rowKey3_0, "知识点2");
		dataset3.addValue(10d, rowKey3_1, "知识点2");
		dataset3.addValue(20d, rowKey3_2, "知识点2");
		dataset3.addValue(50d, rowKey3_0, "知识点3");
		dataset3.addValue(20d, rowKey3_1, "知识点3");
		dataset3.addValue(30d, rowKey3_2, "知识点3");
		dataset3.addValue(30d, rowKey3_0, "知识点4");
		dataset3.addValue(30d, rowKey3_1, "知识点4");
		dataset3.addValue(40d, rowKey3_2, "知识点4");

		CategoryPlot plot = new CategoryPlot(dataset0, new CategoryAxis("aaaa"), new NumberAxis("bbbb"), new LineAndShapeRenderer());
		plot.setDataset(1, dataset1);
		plot.setRenderer(1, new LineAndShapeRenderer());
		plot.setDataset(2, dataset2);
		plot.setRenderer(2, new CategorySplineRenderer());
		plot.setDataset(3, dataset3);
		// 3d堆积图
		plot.setRenderer(3, new StackedBarRenderer3D());

		// 平面堆积图
		// StackedBarRenderer stackedBarRenderer = new StackedBarRenderer();
		// stackedBarRenderer.setDrawBarOutline(false);
		// stackedBarRenderer.setShadowVisible(false);
		// stackedBarRenderer.setBaseItemLabelsVisible(true);
		// stackedBarRenderer.setBarPainter(new DottedBoxBarPainter());

		// plot.setRenderer(3, stackedBarRenderer);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
		plot.setRangeGridlinesVisible(false);

		JFreeChart localJFreeChart = new JFreeChart("知识点得分率分层图", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		show(localJFreeChart);
	}

	private static void normalDistribution() {
		NormalDistributionFunction2D localNormalDistributionFunction2D = new NormalDistributionFunction2D(0.5D, 1.0D);
		XYDataset localXYDataset = DatasetUtilities.sampleFunction2D(localNormalDistributionFunction2D, -0.5D, 0.5d, 100, "Normal");
		XYItemRenderer renderer = new CustomXYLineAndShapeRenderer(true, false);
		NumberAxis xAxis = new NumberAxis("X");
		xAxis.setAutoRangeIncludesZero(false);
		XYPlot plot0 = new XYPlot(localXYDataset, xAxis, new NumberAxis("Y"), renderer);
		JFreeChart chart = new JFreeChart("aaa", plot0);
		// JFreeChart chart = ChartFactory.createXYLineChart("Normal Distribution", "X", "Y", localXYDataset, PlotOrientation.VERTICAL, true, true, false);
		show(chart);

	}

	private static void normalDistri() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String rowKey = "人数";
		dataset.addValue(50d, rowKey, "0-30");
		dataset.addValue(15d, rowKey, "30-50");
		dataset.addValue(35d, rowKey, "50-80");
		dataset.addValue(26d, rowKey, "80-90");
		dataset.addValue(25d, rowKey, "90-100");

		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		String rowKey1 = "人数2";
		dataset1.addValue(20d, rowKey1, "0-30");
		dataset1.addValue(25d, rowKey1, "30-50");
		dataset1.addValue(75d, rowKey1, "50-80");
		dataset1.addValue(56d, rowKey1, "80-90");
		dataset1.addValue(35d, rowKey1, "90-100");

		BarRenderer barRenderer = new BarRenderer();
		barRenderer.setShadowVisible(false);
		barRenderer.setBarPainter(new StandardBarPainter());
		CategoryPlot plot = new CategoryPlot(dataset, new CategoryAxis("aaaa"), new NumberAxis("bbbb"), barRenderer);
		plot.setDataset(1, dataset1);
		NormalDistributionRenderer normalDistributionRenderer = new NormalDistributionRenderer();
		plot.setRenderer(1, normalDistributionRenderer);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		JFreeChart localJFreeChart = new JFreeChart("Overlaid XYPlot Demo 1", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		show(localJFreeChart);

	}

	private static void multiLineChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String rowKey = "人数";
		dataset.addValue(50d, rowKey, "0-30");
		dataset.addValue(15d, rowKey, "30-50");
		dataset.addValue(35d, rowKey, "50-80");
		dataset.addValue(26d, rowKey, "80-90");
		dataset.addValue(25d, rowKey, "90-100");

		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		String rowkey2 = "多项式";
		dataset2.addValue(60d, rowkey2, "0-30");
		dataset2.addValue(55d, rowkey2, "30-50");
		dataset2.addValue(45d, rowkey2, "50-80");
		dataset2.addValue(36d, rowkey2, "80-90");
		dataset2.addValue(85d, rowkey2, "90-100");

		CategoryPlot plot = new CategoryPlot(dataset, new CategoryAxis("aaaa"), new NumberAxis("bbbb"), new LineAndShapeRenderer());
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, new CategorySplineRenderer());
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		JFreeChart localJFreeChart = new JFreeChart("Overlaid XYPlot Demo 1", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		show(localJFreeChart);
	}

	private static void spiderWebChart() {
		String eclassStr = "班级";
		String gradeStr = "年级";
		String personStr = "个人";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(85d, eclassStr, "物理");
		dataset.addValue(95d, gradeStr, "物理");
		dataset.addValue(55d, personStr, "物理");
		dataset.addValue(60d, eclassStr, "化学");
		dataset.addValue(66d, gradeStr, "化学");
		dataset.addValue(85d, personStr, "化学");
		dataset.addValue(70d, eclassStr, "政治");
		dataset.addValue(80d, gradeStr, "政治");
		dataset.addValue(90d, personStr, "政治");
		dataset.addValue(60d, eclassStr, "语文");
		dataset.addValue(40d, gradeStr, "语文");
		dataset.addValue(30d, personStr, "语文");
		dataset.addValue(30d, eclassStr, "数学");
		dataset.addValue(90d, gradeStr, "数学");
		dataset.addValue(90d, personStr, "数学");
		SpiderWebPlot plot = new SpiderWebPlot(dataset);
		JFreeChart jFreeChart = new JFreeChart("雷达图", TextTitle.DEFAULT_FONT, plot, false);
		show(jFreeChart);
	}

	private static void barChar1() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String rowKey = "分数";
		dataset.addValue(0.50d, rowKey, "数学");
		dataset.addValue(0.15d, rowKey, "语文");
		dataset.addValue(-0.35d, rowKey, "英语");
		dataset.addValue(0.26d, rowKey, "化学");
		dataset.addValue(0.25d, rowKey, "政治");
		JFreeChart localJFreeChart = ChartFactory.createBarChart("title", "X轴", "y轴", dataset, PlotOrientation.VERTICAL, false, false, false);
		show(localJFreeChart);
	}

	private static void splineRendererDemo() {
		XYSeries localXYSeries1 = new XYSeries("Series 1");
		localXYSeries1.add(2.0D, 56.270000000000003D);
		localXYSeries1.add(3.0D, 41.32D);
		localXYSeries1.add(4.0D, 31.449999999999999D);
		localXYSeries1.add(5.0D, 30.050000000000001D);
		localXYSeries1.add(6.0D, 24.690000000000001D);
		localXYSeries1.add(7.0D, 19.780000000000001D);
		localXYSeries1.add(8.0D, 20.940000000000001D);
		localXYSeries1.add(9.0D, 16.73D);
		localXYSeries1.add(10.0D, 14.210000000000001D);
		localXYSeries1.add(11.0D, 12.44D);
		XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection(localXYSeries1);

		XYSeries localXYSeries2 = new XYSeries("Series 2");
		localXYSeries2.add(11.0D, 56.270000000000003D);
		localXYSeries2.add(10.0D, 41.32D);
		localXYSeries2.add(9.0D, 31.449999999999999D);
		localXYSeries2.add(8.0D, 30.050000000000001D);
		localXYSeries2.add(7.0D, 24.690000000000001D);
		localXYSeries2.add(6.0D, 19.780000000000001D);
		localXYSeries2.add(5.0D, 20.940000000000001D);
		localXYSeries2.add(4.0D, 16.73D);
		localXYSeries2.add(3.0D, 14.210000000000001D);
		localXYSeries2.add(2.0D, 12.44D);
		XYSeriesCollection localXYSeriesCollection1 = new XYSeriesCollection(localXYSeries2);

		NumberAxis localNumberAxis1 = new NumberAxis("X");
		localNumberAxis1.setAutoRangeIncludesZero(false);
		NumberAxis localNumberAxis2 = new NumberAxis("Y");
		localNumberAxis2.setAutoRangeIncludesZero(false);
		XYPlot localXYPlot = new XYPlot(localXYSeriesCollection, localNumberAxis1, localNumberAxis2, new XYLineAndShapeRenderer());
		localXYPlot.setBackgroundPaint(Color.lightGray);
		localXYPlot.setDomainGridlinePaint(Color.white);
		localXYPlot.setRangeGridlinePaint(Color.white);
		localXYPlot.setAxisOffset(new RectangleInsets(4.0D, 4.0D, 4.0D, 4.0D));

		localXYPlot.setDataset(1, localXYSeriesCollection1);
		localXYPlot.setRenderer(1, new CustomXYSplineRenderer());

		JFreeChart localJFreeChart = new JFreeChart("XYLineAndShapeRenderer", JFreeChart.DEFAULT_TITLE_FONT, localXYPlot, true);
		show(localJFreeChart);
	}

	private static void show(JFreeChart localJFreeChart) {
		JFreeChartUtil.fixJfreeChart(localJFreeChart);
		ChartPanel cp = new ChartPanel(localJFreeChart);
		cp.setPreferredSize(new Dimension(800, 500));
		ApplicationFrame frame = new ApplicationFrame("窗口标题");
		frame.setContentPane(cp);
		frame.pack();
		RefineryUtilities.centerFrameOnScreen(frame);
		frame.setVisible(true);
	}
}
