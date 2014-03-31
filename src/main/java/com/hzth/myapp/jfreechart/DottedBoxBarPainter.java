package com.hzth.myapp.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.RectangularShape;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.ui.RectangleEdge;

public class DottedBoxBarPainter extends StandardBarPainter {

	private static final long serialVersionUID = -3737113375418231473L;

	public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
		// 白色的柱
		Color color = new Color(0xff, 0xff, 0xff, 255);
		g2.setPaint(color);
		g2.fill(bar);

		// 虚线框
		Stroke stroke = renderer.getItemOutlineStroke(row, column);
		stroke = new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 5.0f }, 0.0f);
		Paint paint = renderer.getItemOutlinePaint(row, column);
		if (stroke != null && paint != null) {
			g2.setStroke(stroke);
			g2.setPaint(paint);
			g2.draw(bar);
		}

	}

	@Override
	public int hashCode() {
		return super.hashCode() + 1;
	}
}
