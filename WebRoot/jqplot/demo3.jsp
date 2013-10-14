<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
    <%@ include file="/jqplot/jqplotHeader.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			var dataObj = {};
			dataObj.datas = [[10,20,50,50.4,56.7]];
			dataObj.series = [{label:"成绩"}];
			dataObj.category = ["第一次月考","第2次月考","第3次月考","第4次月考","第5次月考"];
			$.jqplot("chart1", dataObj.datas, {
				seriesDefaults:{
				    renderer:$.jqplot.BarRenderer,
				    pointLabels: { show: true },
				    rendererOptions: {fillToZero: true}
				},
				series:dataObj.series,
				legend: {
				    show: true,
				    placement: 'outsideGrid'
				},
				axes: {
				    xaxis: {
						renderer: $.jqplot.CategoryAxisRenderer,
						ticks: dataObj.category,
						tickRenderer: $.jqplot.CanvasAxisTickRenderer,
						tickOptions: {angle: 30}
				    },
				    yaxis: {
						min: 0,
						max: null
				    }
				}
			});
			
			
			var line1 = [['第1次月考', 40], ['第2次月考', 90.5], ['第3次月考', 15], ['第4次月考', 59], ['第5次月考', 70.6]];
			var line2 = [['第1次月考', 28], ['第2次月考', 98.9], ['第3次月考', 54], ['第4次月考', 47], ['第5次月考', 47]];
			var line3 = [['第1次月考', 68], ['第2次月考', 28.9], ['第3次月考', 44], ['第4次月考', 47], ['第5次月考', 87]];
			$.jqplot('chart11', [line1, line2, line3], {
				legend: {
				    show: true,
				    placement: 'insideGrid'
				},
				series:[{renderer:$.jqplot.BarRenderer,label:"成绩"}, {renderer:$.jqplot.LineRenderer,label:"年级平均"}, {renderer:$.jqplot.LineRenderer,label:"班级平均"}],
				axesDefaults: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle: 30
					}
				},
				axes: {
					xaxis: {
						renderer: $.jqplot.CategoryAxisRenderer
					},
					yaxis: {
						autoscale:true
					}
				}
			});
			
			//仪表盘图
			$.jqplot('chart2',[[13.5]],{
				title: 'Network Speed',
				seriesDefaults: {
					renderer: $.jqplot.MeterGaugeRenderer,
					rendererOptions: {
						intervals:[3,10,20],
			            intervalColors:['#66cc66', '#E7E658', '#cc6666'],
			        	label: ''
// 						ticks:["A","B","C"],
// 						tickPositions:[0,10,30]
			        }
				}
			});
			
			//雷达
			new html5jp.graph.radar("chart3").draw([["班级",2,3,5,2,4,4.8],["年级",3,2,6,6,4,1.5]],{aCap:["语文","数学","英语","物理","化学","生物"]});
			
			$.jqplot("chart4", [[["第一次月考",30],["第2次月考",20],["第3次月考",50],["第4次月考",50.4],["第5次月考",100]]], {
				seriesDefaults:{
				    renderer:$.jqplot.BarRenderer,
				    pointLabels: { show: true },
				    rendererOptions: {
				    	
				    }
				},
				series:[{label:"成绩"}],
				legend: {
				    show: true,
				    placement: 'outsideGrid'
				},
				axes: {
				    xaxis: {
						renderer: $.jqplot.CategoryAxisRenderer
				    }
				}
			});
			
			
			var l2 = [10, 10, 10, 10, 10];
			var l3 = [10, 10, 10, 10, 10];
			var l4 = [10, 10, 10, 10, 10];
// 			 var l3 = [20, 20, 20, 20, 20];
// 			 var l4 = [30, 30, 30, 30, 30];    
			 
			$.jqplot('chart5',[l2, l3, l4],{
				stackSeries: true,
				showMarker: false,
				seriesDefaults: {
					fill: true
				},
				axes: {
					xaxis: {
						renderer: $.jqplot.CategoryAxisRenderer,
						ticks: ["Mon", "Tue", "Wed", "Thr", "Fri"]
					},
					yaxis: {
						min: 0,
						max: 30
					}
				}
			});
			
			//柱形分层
			new html5jp.graph.vbar("chart6").draw([["分数",2,3,5,2,4,4.8]],{x:["","语文","数学","英语","物理","化学","生物"],barShape:"line",legend:false});
		});
	</script>
  </head>
  <body>
	<div id="chart1" style="width: 600px;height:450px"></div>
	<div id="chart11" style="width: 600px;height:450px"></div>
	<div id="chart2" style="width: 600px;height:400px"></div>
	<div>
		<canvas width="400" height="300" id="chart3"></canvas>
	</div>
	<div id="chart4" style="width: 600px;height:400px"></div>
	<div id="chart5" style="width: 600px;height:400px"></div>
	<div>
		<canvas width="400" height="300" id="chart6"></canvas>
	</div>
  </body>
</html>