package com.hzth.myapp.jfreechart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.text.NumberFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

public class BarChartDemo1 extends ApplicationFrame {

	private static final long serialVersionUID = -3728218040798703470L;

	public BarChartDemo1(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
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

	private static JFreeChart createChart(CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createBarChart(null, "Category", null, categorydataset, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setDomainGridlinesVisible(true);

		categoryplot.setOutlineVisible(true);

		categoryplot.getRenderer().setSeriesPaint(0, Color.RED);

		((BarRenderer) categoryplot.getRenderer()).setBarPainter(new GradientBarPainter(0d, 0d, 1d));

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setAutoTickUnitSelection(false);
		numberaxis.setTickUnit(new NumberTickUnit(0.1));
		numberaxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
		numberaxis.setRange(0, 1);

		IntervalMarker intervalmarker = new IntervalMarker(0D, 0.2);
		intervalmarker.setPaint(Color.BLACK);
		// categoryplot.addRangeMarker(intervalmarker, Layer.BACKGROUND);

		IntervalMarker intervalmarker2 = new IntervalMarker(0.2, 0.4);
		intervalmarker2.setPaint(Color.BLUE);
		// categoryplot.addRangeMarker(intervalmarker2, Layer.BACKGROUND);

		IntervalMarker intervalmarker3 = new IntervalMarker(0.4, 0.6);
		intervalmarker3.setPaint(Color.CYAN);
		// categoryplot.addRangeMarker(intervalmarker3, Layer.BACKGROUND);

		IntervalMarker intervalmarker4 = new IntervalMarker(0.6, 0.8);
		intervalmarker4.setPaint(Color.GREEN);
		// categoryplot.addRangeMarker(intervalmarker4, Layer.BACKGROUND);

		IntervalMarker intervalmarker5 = new IntervalMarker(0.8, 1);
		intervalmarker5.setPaint(Color.ORANGE);
		// categoryplot.addRangeMarker(intervalmarker5, Layer.BACKGROUND);

		// String[] sv = new String[] { "A层级", "b层级", "c层级", "d层级", "e层级" };
		// SymbolAxis axis = new SymbolAxis(null, sv);
		// axis.setAutoTickUnitSelection(false);
		// categoryplot.setRangeAxis(1, axis);

		return jfreechart;
	}

	public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[]) {
		BarChartDemo1 barchartdemo1 = new BarChartDemo1("JFreeChart: BarChartDemo1.java");
		barchartdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(barchartdemo1);
		barchartdemo1.setVisible(true);
	}
}

class MyBarPainter extends GradientBarPainter {

	private static final long serialVersionUID = -6933928921437944959L;

	/** The division point between the first and second gradient regions. */
	private double g1;

	/** The division point between the second and third gradient regions. */
	private double g2;

	/** The division point between the third and fourth gradient regions. */
	private double g3;

	/**
	 * Creates a new instance.
	 */
	public MyBarPainter() {
		this(0.00, 0.00, 0.80);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param g1
	 * @param g2
	 * @param g3
	 */
	public MyBarPainter(double g1, double g2, double g3) {
		this.g1 = g1;
		this.g2 = g2;
		this.g3 = g3;
	}

	private Rectangle2D[] splitVerticalBar(RectangularShape bar, double a,
			double b, double c) {
		Rectangle2D[] result = new Rectangle2D[4];
		double x0 = bar.getMinX();
		double x1 = Math.rint(x0 + (bar.getWidth() * a));
		double x2 = Math.rint(x0 + (bar.getWidth() * b));
		double x3 = Math.rint(x0 + (bar.getWidth() * c));
		result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
				x1 - x0, bar.getHeight());
		result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1,
				bar.getHeight());
		result[2] = new Rectangle2D.Double(x2, bar.getMinY(), x3 - x2,
				bar.getHeight());
		result[3] = new Rectangle2D.Double(x3, bar.getMinY(),
				bar.getMaxX() - x3, bar.getHeight());
		return result;
	}

	private Rectangle2D[] splitHorizontalBar(RectangularShape bar, double a,
			double b, double c) {
		Rectangle2D[] result = new Rectangle2D[4];
		double y0 = bar.getMinY();
		double y1 = Math.rint(y0 + (bar.getHeight() * a));
		double y2 = Math.rint(y0 + (bar.getHeight() * b));
		double y3 = Math.rint(y0 + (bar.getHeight() * c));
		result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
				bar.getWidth(), y1 - y0);
		result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(),
				y2 - y1);
		result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(),
				y3 - y2);
		result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(),
				bar.getMaxY() - y3);
		return result;
	}

	public void paintBar(Graphics2D g2, BarRenderer renderer, int row,
			int column, RectangularShape bar, RectangleEdge base) {

		Paint itemPaint = renderer.getItemPaint(row, column);

		Color c0, c1;
		if (itemPaint instanceof Color) {
			c0 = (Color) itemPaint;
			c1 = c0.brighter();
		}
		else if (itemPaint instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) itemPaint;
			c0 = gp.getColor1();
			c1 = gp.getColor2();
		}
		else {
			c0 = Color.blue;
			c1 = Color.blue.brighter();
		}

		// as a special case, if the bar colour has alpha == 0, we draw
		// nothing.
		if (c0.getAlpha() == 0) {
			return;
		}

		if (base == RectangleEdge.TOP || base == RectangleEdge.BOTTOM) {
			Rectangle2D[] regions = splitVerticalBar(bar, this.g1, this.g2,
					this.g3);
			GradientPaint gp = new GradientPaint((float) regions[0].getMinX(),
					0.0f, c0, (float) regions[0].getMaxX(), 0.0f, Color.white);
			g2.setPaint(gp);
			g2.fill(regions[0]);

			gp = new GradientPaint((float) regions[1].getMinX(), 0.0f,
					Color.white, (float) regions[1].getMaxX(), 0.0f, c0);
			g2.setPaint(gp);
			g2.fill(regions[1]);

			gp = new GradientPaint((float) regions[2].getMinX(), 0.0f, c0,
					(float) regions[2].getMaxX(), 0.0f, c1);
			g2.setPaint(gp);
			g2.fill(regions[2]);

			gp = new GradientPaint((float) regions[3].getMinX(), 0.0f, c1,
					(float) regions[3].getMaxX(), 0.0f, c0);
			g2.setPaint(gp);
			g2.fill(regions[3]);
		}
		else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
			Rectangle2D[] regions = splitHorizontalBar(bar, this.g1, this.g2,
					this.g3);
			GradientPaint gp = new GradientPaint(0.0f,
					(float) regions[0].getMinY(), c0, 0.0f,
					(float) regions[0].getMaxX(), Color.white);
			g2.setPaint(gp);
			g2.fill(regions[0]);

			gp = new GradientPaint(0.0f, (float) regions[1].getMinY(),
					Color.white, 0.0f, (float) regions[1].getMaxY(), c0);
			g2.setPaint(gp);
			g2.fill(regions[1]);

			gp = new GradientPaint(0.0f, (float) regions[2].getMinY(), c0,
					0.0f, (float) regions[2].getMaxY(), c1);
			g2.setPaint(gp);
			g2.fill(regions[2]);

			gp = new GradientPaint(0.0f, (float) regions[3].getMinY(), c1,
					0.0f, (float) regions[3].getMaxY(), c0);
			g2.setPaint(gp);
			g2.fill(regions[3]);

		}

		// draw the outline...
		if (renderer.isDrawBarOutline()
		/* && state.getBarWidth() > renderer.BAR_OUTLINE_WIDTH_THRESHOLD */) {
			Stroke stroke = renderer.getItemOutlineStroke(row, column);
			Paint paint = renderer.getItemOutlinePaint(row, column);
			if (stroke != null && paint != null) {
				g2.setStroke(stroke);
				g2.setPaint(paint);
				g2.draw(bar);
			}
		}

	}
}