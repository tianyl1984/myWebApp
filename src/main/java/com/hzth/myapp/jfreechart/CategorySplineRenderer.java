package com.hzth.myapp.jfreechart;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.text.TextUtilities;
import org.jfree.util.ShapeUtilities;

public class CategorySplineRenderer extends LineAndShapeRenderer {

	private static final long serialVersionUID = 9131230886133646912L;

	private List<ControlPoint> points;

	// 精度
	private int precision;

	public GeneralPath seriesPath;

	public CategorySplineRenderer() {
		this.precision = 10;
		this.points = new ArrayList<ControlPoint>();
		this.seriesPath = new GeneralPath();
	}

	public CategorySplineRenderer(int precision) {
		this.precision = precision;
		this.points = new ArrayList<ControlPoint>();
		this.seriesPath = new GeneralPath();
	}

	@Override
	public void drawDomainMarker(Graphics2D g2, CategoryPlot plot, CategoryAxis axis, CategoryMarker marker, Rectangle2D dataArea) {
		super.drawDomainMarker(g2, plot, axis, marker, dataArea);
	}

	@Override
	public void drawRangeMarker(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Marker marker, Rectangle2D dataArea) {
		if (!(marker instanceof CategoryValueMarker)) {
			super.drawRangeMarker(g2, plot, axis, marker, dataArea);
		} else {
			CategoryValueMarker cvMarker = (CategoryValueMarker) marker;
			CategoryDataset dataset = plot.getDataset(plot.getIndexOf(this));
			CategoryAxis categoryAxis = plot.getDomainAxisForDataset(plot.getIndexOf(this));

			String category = cvMarker.getCategory();
			double value = cvMarker.getValue();
			Range range = axis.getRange();

			int columnIndex = dataset.getColumnIndex(category);
			if (columnIndex < 0) {
				return;
			}
			if (!range.contains(value)) {
				return;
			}

			final Composite savedComposite = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, marker.getAlpha()));

			PlotOrientation orientation = plot.getOrientation();
			double vx = categoryAxis.getCategoryMiddle(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
			double vy = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());

			Line2D line = null;
			if (orientation == PlotOrientation.HORIZONTAL) {
				line = new Line2D.Double(vy, dataArea.getMinY(), vy, vx);
			} else if (orientation == PlotOrientation.VERTICAL) {
				line = new Line2D.Double(dataArea.getMinX(), vy, vx, vy);
			}

			g2.setPaint(marker.getPaint());
			g2.setStroke(marker.getStroke());
			g2.draw(line);

			Line2D line2 = null;
			if (orientation == PlotOrientation.HORIZONTAL) {
				line2 = new Line2D.Double(vy, dataArea.getMinY(), vy, vx);
			} else if (orientation == PlotOrientation.VERTICAL) {
				line2 = new Line2D.Double(vx, dataArea.getMaxY(), vx, vy);
			}

			g2.setPaint(marker.getPaint());
			g2.setStroke(marker.getStroke());
			g2.draw(line2);

			String label = marker.getLabel();
			if (label != null) {
				Font labelFont = marker.getLabelFont();
				g2.setFont(labelFont);
				g2.setPaint(marker.getLabelPaint());
				TextUtilities.drawAlignedString(label, g2, Double.valueOf(vx).floatValue(), Double.valueOf(vy).floatValue() - 10, marker.getLabelTextAnchor());
			}
			g2.setComposite(savedComposite);
		}
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

			if (column == dataset.getColumnCount() - 1) {// 最后一个坐标
				seriesPath.reset();
				if (points.size() > 1) {
					// we need at least two points to draw something
					ControlPoint cp0 = points.get(0);
					seriesPath.moveTo(cp0.x, cp0.y);
					if (this.points.size() == 2) {
						// we need at least 3 points to spline. Draw simple line for two points
						ControlPoint cp1 = (ControlPoint) this.points.get(1);
						seriesPath.lineTo(cp1.x, cp1.y);
					} else {
						// construct spline
						int np = this.points.size(); // number of points
						float[] d = new float[np]; // Newton form coefficients
						float[] x = new float[np]; // x-coordinates of nodes
						float y;
						float t;
						float oldy = 0;
						float oldt = 0;

						float[] a = new float[np];
						float t1;
						float t2;
						float[] h = new float[np];

						for (int i = 0; i < np; i++) {
							ControlPoint cpi = this.points.get(i);
							x[i] = cpi.x;
							d[i] = cpi.y;
						}

						for (int i = 1; i <= np - 1; i++) {
							h[i] = x[i] - x[i - 1];
						}
						float[] sub = new float[np - 1];
						float[] diag = new float[np - 1];
						float[] sup = new float[np - 1];

						for (int i = 1; i <= np - 2; i++) {
							diag[i] = (h[i] + h[i + 1]) / 3;
							sup[i] = h[i + 1] / 6;
							sub[i] = h[i] / 6;
							a[i] = (d[i + 1] - d[i]) / h[i + 1] - (d[i] - d[i - 1]) / h[i];
						}
						solveTridiag(sub, diag, sup, a, np - 2);

						// note that a[0]=a[np-1]=0
						// draw
						oldt = x[0];
						oldy = d[0];
						seriesPath.moveTo(oldt, oldy);
						for (int i = 1; i <= np - 1; i++) {
							// loop over intervals between nodes
							for (int j = 1; j <= this.precision; j++) {
								t1 = (h[i] * j) / this.precision;
								t2 = h[i] - t1;
								y = ((-a[i - 1] / 6 * (t2 + h[i]) * t1 + d[i - 1]) * t2 + (-a[i] / 6 * (t1 + h[i]) * t2 + d[i]) * t1) / h[i];
								t = x[i - 1] + t1;
								seriesPath.lineTo(t, y);
							}
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

	private void solveTridiag(float[] sub, float[] diag, float[] sup, float[] b, int n) {
		/*
		 * solve linear system with tridiagonal n by n matrix a using Gaussian elimination *without* pivoting where a(i,i-1) = sub[i] for 2<=i<=n a(i,i) = diag[i] for 1<=i<=n a(i,i+1) = sup[i] for 1<=i<=n-1 (the values sub[1], sup[n] are ignored) right hand side vector b[1:n] is overwritten with solution NOTE: 1...n is used in all arrays, 0 is unused
		 */
		int i;
		/* factorization and forward substitution */
		for (i = 2; i <= n; i++) {
			sub[i] = sub[i] / diag[i - 1];
			diag[i] = diag[i] - sub[i] * sup[i - 1];
			b[i] = b[i] - sub[i] * b[i - 1];
		}
		b[n] = b[n] / diag[n];
		for (i = n - 1; i >= 1; i--) {
			b[i] = (b[i] - sup[i] * b[i + 1]) / diag[i];
		}
	}
}
