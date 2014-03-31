package com.hzth.myapp.jfreechart;

import java.text.DecimalFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.TextAnchor;

public class JFreeChartUtil {

	public static void fixJfreeChart(JFreeChart chart) {
		java.awt.Font font = new java.awt.Font("宋体", java.awt.Font.BOLD, 10);
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 12));
		// 图例
		if (chart.getLegend() != null) {
			chart.getLegend().setItemFont(font);
		}
		if (chart.getPlot() instanceof CategoryPlot) {
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
}
