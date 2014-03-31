package com.hzth.myapp.jfreechart;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.ShapeUtilities;

public class CategoryPolynomialRenderer extends LineAndShapeRenderer {

	private static final long serialVersionUID = 7253505421705865366L;
	private List<ControlPoint> points;

	// 精度
	private int precision;

	private int step;

	public GeneralPath seriesPath;

	public CategoryPolynomialRenderer() {
		this.precision = 10;
		this.step = 10;
		this.points = new ArrayList<ControlPoint>();
		this.seriesPath = new GeneralPath();
	}

	public CategoryPolynomialRenderer(int precision) {
		this.precision = precision;
		this.points = new ArrayList<ControlPoint>();
		this.seriesPath = new GeneralPath();
	}

	public CategoryPolynomialRenderer(int precision, int step) {
		this.precision = precision;
		this.step = step;
		this.points = new ArrayList<ControlPoint>();
		this.seriesPath = new GeneralPath();
	}

	public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {

		// do nothing if item is not visible
		if (!getItemVisible(row, column)) {
			return;
		}

		// do nothing if both the line and shape are not visible
		if (!getItemLineVisible(row, column) && !getItemShapeVisible(row, column)) {
			return;
		}

		// nothing is drawn for null...
		Number v = dataset.getValue(row, column);
		if (v == null) {
			return;
		}

		int visibleRow = state.getVisibleSeriesIndex(row);
		if (visibleRow < 0) {
			return;
		}
		int visibleRowCount = state.getVisibleSeriesCount();

		PlotOrientation orientation = plot.getOrientation();

		// current data point...
		double x1;
		if (this.getUseSeriesOffset()) {
			x1 = domainAxis.getCategorySeriesMiddle(column, dataset.getColumnCount(), visibleRow, visibleRowCount, this.getItemMargin(), dataArea, plot.getDomainAxisEdge());
		} else {
			x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
		}
		double value = v.doubleValue();
		double y1 = rangeAxis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());

		if (pass == 0 && getItemLineVisible(row, column)) {

			// 保存坐标点
			ControlPoint cp = new ControlPoint(x1, y1);
			if (!points.contains(cp)) {
				points.add(cp);
			}
			int itemCount = dataset.getColumnCount();
			if (column == dataset.getColumnCount() - 1) {// 最后一个坐标
				seriesPath.reset();
				if (points.size() > 1) {
					List<Double> x = new ArrayList<Double>();
					List<Double> y = new ArrayList<Double>();
					for (ControlPoint point : points) {
						x.add(Float.valueOf(point.x).doubleValue());
						y.add(Float.valueOf(point.y).doubleValue());
					}

					double[] aa = polyFit(x.toArray(new Double[itemCount]), y.toArray(new Double[itemCount]), itemCount, step);
					double oldx = x.get(0);
					double oldy = getY(oldx, x.toArray(new Double[itemCount]), aa, step);
					seriesPath.moveTo(oldx, oldy);
					for (int i = 0; i < itemCount - 1; i++) {
						double start = x.get(i);
						double end = x.get(i + 1);
						double rg = end - start;
						double stepValue = rg / precision;
						for (int j = 0; j < precision; j++) {
							double tx = start + j * stepValue;
							double ty = getY(tx, x.toArray(new Double[itemCount]), aa, step);
							seriesPath.lineTo(tx, ty);
						}

					}
					// draw path
					g2.setPaint(getItemPaint(row, column));
					g2.setStroke(getItemStroke(row, column));
					g2.draw(seriesPath);

					points.clear();
				}
			}

		}

		// 画点上边的方块
		if (pass == 1) {
			Shape shape = getItemShape(row, column);
			if (orientation == PlotOrientation.HORIZONTAL) {
				shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
			} else if (orientation == PlotOrientation.VERTICAL) {
				shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
			}

			if (getItemShapeVisible(row, column)) {
				if (getItemShapeFilled(row, column)) {
					if (this.getUseFillPaint()) {
						g2.setPaint(getItemFillPaint(row, column));
					} else {
						g2.setPaint(getItemPaint(row, column));
					}
					g2.fill(shape);
				}
				if (this.getDrawOutlines()) {
					if (this.getUseOutlinePaint()) {
						g2.setPaint(getItemOutlinePaint(row, column));
					} else {
						g2.setPaint(getItemPaint(row, column));
					}
					g2.setStroke(getItemOutlineStroke(row, column));
					g2.draw(shape);
				}
			}

			// draw the item label if there is one...
			if (isItemLabelVisible(row, column)) {
				if (orientation == PlotOrientation.HORIZONTAL) {
					drawItemLabel(g2, orientation, dataset, row, column, y1, x1, (value < 0.0));
				} else if (orientation == PlotOrientation.VERTICAL) {
					drawItemLabel(g2, orientation, dataset, row, column, x1, y1, (value < 0.0));
				}
			}

			// submit the current data point as a crosshair candidate
			int datasetIndex = plot.indexOf(dataset);
			updateCrosshairValues(state.getCrosshairState(), dataset.getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, x1, y1, orientation);

			// add an item entity, if this information is being collected
			EntityCollection entities = state.getEntityCollection();
			if (entities != null) {
				addItemEntity(entities, dataset, row, column, shape);
			}
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
