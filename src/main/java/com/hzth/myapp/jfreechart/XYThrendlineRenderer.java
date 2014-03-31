package com.hzth.myapp.jfreechart;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

public class XYThrendlineRenderer extends XYSplineRenderer {

	private static final long serialVersionUID = -2842565678298428965L;
	List<Double> x = new ArrayList<Double>();
	List<Double> y = new ArrayList<Double>();
	/**
	 * To collect data points for later splining.
	 */
	private Vector points;

	/**
	 * Resolution of splines (number of line segments between points)
	 */
	private int precision;

	private int step = 6;

	/**
	 * Creates a new instance with the 'precision' attribute defaulting to 5.
	 */
	public XYThrendlineRenderer() {
		this(5);
	}

	/**
	 * Creates a new renderer with the specified precision.
	 * 
	 * @param precision
	 *            the number of points between data items.
	 */
	public XYThrendlineRenderer(int precision) {
		super();
		if (precision <= 0) {
			throw new IllegalArgumentException("Requires precision > 0.");
		}
		this.precision = precision;
	}

	/**
	 * Creates a new renderer with the specified precision.
	 * 
	 * @param precision
	 *            the number of points between data items.
	 */
	public XYThrendlineRenderer(int precision, int step) {
		super();
		if (precision <= 0) {
			throw new IllegalArgumentException("Requires precision > 0.");
		}
		if (step < 2) {
			throw new IllegalArgumentException("Requires step > 1");
		}
		this.step = step;
		this.precision = precision;
	}

	protected void drawPrimaryLineAsPath(XYItemRendererState state,
			Graphics2D g2, XYPlot plot, XYDataset dataset, int pass,
			int series, int item, ValueAxis domainAxis, ValueAxis rangeAxis,
			Rectangle2D dataArea) {

		RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
		RectangleEdge yAxisLocation = plot.getRangeAxisEdge();

		// get the data points
		double x1 = dataset.getXValue(series, item);
		double y1 = dataset.getYValue(series, item);
		double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
		double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);

		// collect points
		if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
			x.add(transX1);
			y.add(transY1);
		}

		int itemCount = dataset.getItemCount(series);
		if (item == itemCount - 1) {
			State s = (State) state;
			double[] aa = polyFit(x.toArray(new Double[itemCount]), y.toArray(new Double[itemCount]), itemCount, step);
			double oldx = x.get(0);
			double oldy = getY(oldx, x.toArray(new Double[itemCount]), aa, step);
			s.seriesPath.moveTo(oldx, oldy);
			for (int i = 0; i < itemCount - 1; i++) {
				double start = x.get(i);
				double end = x.get(i + 1);
				double rg = end - start;
				double stepValue = rg / precision;
				for (int j = 0; j < precision; j++) {
					double tx = start + j * stepValue;
					double ty = getY(tx, x.toArray(new Double[itemCount]), aa, step);
					s.seriesPath.lineTo(tx, ty);
				}

			}
			drawFirstPassShape(g2, pass, series, item, s.seriesPath);
			x.clear();
			y.clear();
		}
	}

	/**
	 * <p>
	 * 函数功能：最小二乘法曲线拟合
	 * </p>
	 * 
	 * @param x
	 *            实型一维数组，长度为 n 。存放给定 n 个数据点的　X　坐标
	 * @param y
	 *            实型一维数组，长度为 n 。存放给定 n 个数据点的　Y　坐标
	 * @param n
	 *            变量。给定数据点的个数
	 * @param a
	 *            实型一维数组，长度为 m 。返回 m-1　次拟合多项式的 m 个系数
	 * @param m
	 *            拟合多项式的项数，即拟合多项式的最高次数为 m-1. 要求 m<=n 且m<=20。若 m>n 或 m>20 ，则本函数自动按 m=min{n,20} 处理.
	 * @return
	 */
	private static double[] polyFit(Double x[], Double y[], int n, int m) {
		double a[] = new double[m];
		int i, j, k;
		double z, p, c, g, q = 0, d1, d2;
		double[] s = new double[20];
		double[] t = new double[20];
		double[] b = new double[20];
		double[] dt = new double[3];
		for (i = 0; i <= m - 1; i++) {
			a[i] = 0.0;
		}
		if (m > n) {
			m = n;
		}
		if (m > 20) {
			m = 20;
		}
		z = 0.0;
		for (i = 0; i <= n - 1; i++) {
			z = z + x[i] / (1.0 * n);
		}
		b[0] = 1.0;
		d1 = 1.0 * n;
		p = 0.0;
		c = 0.0;
		for (i = 0; i <= n - 1; i++) {
			p = p + (x[i] - z);
			c = c + y[i];
		}
		c = c / d1;
		p = p / d1;
		a[0] = c * b[0];
		if (m > 1) {
			t[1] = 1.0;
			t[0] = -p;
			d2 = 0.0;
			c = 0.0;
			g = 0.0;
			for (i = 0; i <= n - 1; i++) {
				q = x[i] - z - p;
				d2 = d2 + q * q;
				c = c + y[i] * q;
				g = g + (x[i] - z) * q * q;
			}
			c = c / d2;
			p = g / d2;
			q = d2 / d1;
			d1 = d2;
			a[1] = c * t[1];
			a[0] = c * t[0] + a[0];
		}
		for (j = 2; j <= m - 1; j++) {
			s[j] = t[j - 1];
			s[j - 1] = -p * t[j - 1] + t[j - 2];
			if (j >= 3)
				for (k = j - 2; k >= 1; k--) {
					s[k] = -p * t[k] + t[k - 1] - q * b[k];
				}
			s[0] = -p * t[0] - q * b[0];
			d2 = 0.0;
			c = 0.0;
			g = 0.0;
			for (i = 0; i <= n - 1; i++) {
				q = s[j];
				for (k = j - 1; k >= 0; k--) {
					q = q * (x[i] - z) + s[k];
				}
				d2 = d2 + q * q;
				c = c + y[i] * q;
				g = g + (x[i] - z) * q * q;
			}
			c = c / d2;
			p = g / d2;
			q = d2 / d1;
			d1 = d2;
			a[j] = c * s[j];
			t[j] = s[j];
			for (k = j - 1; k >= 0; k--) {
				a[k] = c * s[k] + a[k];
				b[k] = t[k];
				t[k] = s[k];
			}
		}
		dt[0] = 0.0;
		dt[1] = 0.0;
		dt[2] = 0.0;
		for (i = 0; i <= n - 1; i++) {
			q = a[m - 1];
			for (k = m - 2; k >= 0; k--) {
				q = a[k] + q * (x[i] - z);
			}
			p = q - y[i];
			if (Math.abs(p) > dt[2]) {
				dt[2] = Math.abs(p);
			}
			dt[0] = dt[0] + p * p;
			dt[1] = dt[1] + Math.abs(p);
		}
		return a;
	}

	/**
	 * <p>
	 * 对X轴数据节点球平均值
	 * </p>
	 * 
	 * @param x
	 *            存储X轴节点的数组
	 *            <p>
	 *            Date:2007-12-25 20:21 PM
	 *            </p>
	 * @author qingbao-gao
	 * @return 平均值
	 */
	private static double ave(Double[] x) {
		double ave = 0;
		double sum = 0;
		if (x != null) {
			for (int i = 0; i < x.length; i++) {
				sum += x[i];
			}
			ave = sum / x.length;
		}
		return ave;
	}

	/**
	 * <p>
	 * 由X值获得Y值
	 * </p>
	 * 
	 * @param x
	 *            当前X轴输入值,即为预测的月份
	 * @param xx
	 *            当前X轴输入值的前X数据点
	 * @param a
	 *            存储多项式系数的数组
	 * @param m
	 *            存储多项式的最高次数的数组
	 * @return 对应X轴节点值的Y轴值
	 */
	private static double getY(Double x, Double[] xx, double[] a, int m) {
		double y = 0;
		double ave = ave(xx);

		double l = 0;
		for (int i = 0; i < m; i++) {
			l = a[0];
			if (i > 0) {
				y += a[i] * Math.pow((x - ave), i);
			}
		}
		return (y + l);
	}

}