package com.hzth.myapp.jfreechart;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;

public class NormalDistributionRenderer extends LineAndShapeRenderer {

	private static final long serialVersionUID = 3676039161529204408L;

	public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {

		// System.out.println("row:" + row + ",column:" + column + ",pass:" + pass);
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

		// 画线
		if (pass == 0 && getItemLineVisible(row, column)) {
			if (column != 0) {
				Number previousValue = dataset.getValue(row, column - 1);
				if (previousValue != null) {
					// previous data point...
					double previous = previousValue.doubleValue();
					double x0;
					if (this.getUseSeriesOffset()) {
						x0 = domainAxis.getCategorySeriesMiddle(column - 1, dataset.getColumnCount(), visibleRow, visibleRowCount, this.getItemMargin(), dataArea, plot.getDomainAxisEdge());
					} else {
						x0 = domainAxis.getCategoryMiddle(column - 1, getColumnCount(), dataArea, plot.getDomainAxisEdge());
					}
					double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot.getRangeAxisEdge());

					Line2D line = null;
					if (orientation == PlotOrientation.HORIZONTAL) {
						line = new Line2D.Double(y0, x0, y1, x1);
					} else if (orientation == PlotOrientation.VERTICAL) {
						line = new Line2D.Double(x0, y0, x1, y1);
					}
					g2.setPaint(getItemPaint(row, column));
					g2.setStroke(getItemStroke(row, column));
					g2.draw(line);
				}
			}
		}

	}
}
