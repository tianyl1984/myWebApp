package com.hzth.myapp.jfreechart;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.LineUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

public class CustomXYLineAndShapeRenderer extends XYLineAndShapeRenderer {

	private static final long serialVersionUID = -1216257043630585734L;

	public CustomXYLineAndShapeRenderer(boolean lines, boolean shapes) {
		super(lines, shapes);
	}

	@Override
	public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
		System.out.println("series:" + series + " item:" + item);

		// do nothing if item is not visible
		if (!getItemVisible(series, item)) {
			return;
		}

		// first pass draws the background (lines, for instance)
		if (isLinePass(pass)) {
			if (getItemLineVisible(series, item)) {
				if (getDrawSeriesLineAsPath()) {
					drawPrimaryLineAsPath(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
				} else {
					drawPrimaryLine(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
				}
			}
		} else if (isItemPass(pass)) {// second pass adds shapes where the items are ..
			// setup for collecting optional entity info...
			EntityCollection entities = null;
			if (info != null) {
				entities = info.getOwner().getEntityCollection();
			}
			drawSecondaryPass(g2, plot, dataset, pass, series, item, domainAxis, dataArea, rangeAxis, crosshairState, entities);
		}
	}

	public void drawPrimaryLine(XYItemRendererState state, Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, ValueAxis rangeAxis, Rectangle2D dataArea) {
		if (item == 0) {
			return;
		}

		// get the data point...
		double x1 = dataset.getXValue(series, item);
		double y1 = dataset.getYValue(series, item);
		if (Double.isNaN(y1) || Double.isNaN(x1)) {
			return;
		}

		double x0 = dataset.getXValue(series, item - 1);
		double y0 = dataset.getYValue(series, item - 1);
		if (Double.isNaN(y0) || Double.isNaN(x0)) {
			return;
		}

		RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
		RectangleEdge yAxisLocation = plot.getRangeAxisEdge();

		double transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
		double transY0 = rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);

		double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
		double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);

		// only draw if we have good values
		if (Double.isNaN(transX0) || Double.isNaN(transY0) || Double.isNaN(transX1) || Double.isNaN(transY1)) {
			return;
		}

		PlotOrientation orientation = plot.getOrientation();
		boolean visible = false;
		if (orientation == PlotOrientation.HORIZONTAL) {
			state.workingLine.setLine(transY0, transX0, transY1, transX1);
		} else if (orientation == PlotOrientation.VERTICAL) {
			state.workingLine.setLine(transX0, transY0, transX1, transY1);
		}
		visible = LineUtilities.clipLine(state.workingLine, dataArea);
		if (visible) {
			drawFirstPassShape(g2, pass, series, item, state.workingLine);
		}
	}
}
