package com.hzth.myapp.jfreechart;

import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class LineChartDemo extends ApplicationFrame {

	private static final long serialVersionUID = -3435628613325171544L;

	public static void main(String[] args) {
		LineChartDemo demo = new LineChartDemo("test");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

	public LineChartDemo(String title) {
		super(title);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		fixJfreeChart(jfreechart);
		return new ChartPanel(jfreechart);
	}

	private static JFreeChart createChart(CategoryDataset categorydataset) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		data.addValue(0, "名次", "考试1");
		data.addValue(0, "名次", "考试2");
		data.addValue(0, "名次", "考试3");
		data.addValue(0, "名次", "考试4");
		// data.addValue(1, "名次", "考试1");
		// data.addValue(5, "名次", "考试2");
		// data.addValue(10, "名次", "考试3");
		// data.addValue(6, "名次", "考试4");
		JFreeChart chart = ChartFactory.createLineChart("班级排名趋势", "考试", "排名", data, PlotOrientation.VERTICAL, false, false, false);
		// chart.getCategoryPlot().getRangeAxis().setLowerMargin(0.3);
		// chart.getCategoryPlot().getRangeAxis().setUpperMargin(0.3);
		ValueAxis valueAxis = chart.getCategoryPlot().getRangeAxis();
		if (valueAxis instanceof NumberAxis) {
			NumberAxis numAxis = (NumberAxis) valueAxis;
			numAxis.setNumberFormatOverride(new DecimalFormat("0.00"));
		}
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
		NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
		numberAxis.setInverted(true);
		return chart;
	}

	private static CategoryDataset createDataset() {
		String s = "First";
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(0.53D, s, "中文");
		defaultcategorydataset.addValue(0.24D, s, "Category 2");
		defaultcategorydataset.addValue(0.83D, s, "Category 3");
		defaultcategorydataset.addValue(0.54D, s, "Category 4");
		defaultcategorydataset.addValue(0.25D, s, "Category 5");
		return defaultcategorydataset;
	}

	public static void fixJfreeChart(JFreeChart chart) {
		java.awt.Font font = new java.awt.Font("宋体", java.awt.Font.BOLD, 10);
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 12));

		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(font);
		domainAxis.setTickLabelFont(font);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(font);
		rangeAxis.setTickLabelFont(font);

		// 图中显示折线的值
		CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER));
		StandardCategoryItemLabelGenerator scilg = new StandardCategoryItemLabelGenerator();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(scilg.getLabelFormat(), new DecimalFormat("#.##")));
		renderer.setBaseItemLabelFont(font);
	}
}
