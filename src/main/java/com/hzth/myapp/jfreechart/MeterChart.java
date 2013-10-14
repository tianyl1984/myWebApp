package com.hzth.myapp.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class MeterChart extends ApplicationFrame {

	private static final long serialVersionUID = 7720829045230034607L;
	private static DefaultValueDataset dataset;

	public MeterChart(String title) {
		super(title);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	// 调试用的图形显示以及调用输出的方法
	public static JPanel createDemoPanel() {
		dataset = new DefaultValueDataset(155d);
		JFreeChart jfreechart = createChart(dataset);
		outputJPG(jfreechart);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		return chartpanel;
	}

	// 创建仪表图
	private static JFreeChart createChart(ValueDataset valuedataset) {
		MeterPlot meterplot = new MeterPlot(valuedataset);
		meterplot.setUnits("分");
		meterplot.setRange(new Range(0.0D, 500D));
		meterplot.addInterval(new MeterInterval("未上线", new Range(0.0D, 350D), Color.lightGray, new BasicStroke(2.0F), new Color(0x45, 0x72, 0xA7)));
		meterplot.addInterval(new MeterInterval("一本线", new Range(350D, 400D), Color.lightGray, new BasicStroke(2.0F), Color.PINK));
		meterplot.addInterval(new MeterInterval("二本线", new Range(400D, 460D), Color.lightGray, new BasicStroke(2.0F), new Color(255, 0, 0, 128)));
		meterplot.addInterval(new MeterInterval("三本线", new Range(460D, 500D), Color.lightGray, new BasicStroke(2.0F), new Color(255, 0, 0, 128)));
		meterplot.setNeedlePaint(Color.darkGray);
		meterplot.setDialBackgroundPaint(Color.white);
		meterplot.setDialOutlinePaint(Color.gray);
		meterplot.setDialShape(DialShape.CHORD);
		meterplot.setMeterAngle(180);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font(" Dialog ", 1, 10));
		meterplot.setTickLabelPaint(Color.darkGray);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.lightGray);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font(" Dialog ", 1, 14));
		JFreeChart jfreechart = new JFreeChart(" Meter Chart 1 ", JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
		return jfreechart;
	}

	// 把图片输出到本地目录
	public static void outputJPG(JFreeChart chart) {
		// try {
		// File fp = new File("c:\test.jpg");
		// ChartUtilities.saveChartAsJPEG(fp, chart, 300, 300);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static void main(String args[]) {
		MeterChart meterchartdemo1 = new MeterChart(" Meter Chart Demo 1 ");
		meterchartdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(meterchartdemo1);
		meterchartdemo1.setVisible(true);
	}
}
