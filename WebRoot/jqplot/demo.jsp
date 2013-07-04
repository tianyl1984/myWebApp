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
			//仪表盘图
			$.jqplot('chart2',[[13.5]],{
				title: 'Network Speed',
				seriesDefaults: {
					renderer: $.jqplot.MeterGaugeRenderer,
					rendererOptions: {
						intervals:[3,10,20],
			            intervalColors:['#66cc66', '#E7E658', '#cc6666'],
			        	label: 'aaaaaaaa'
// 						ticks:["A","B","C"]
// 						tickPositions:[0,10,30]
			        }
				}
			});
			
			//雷达
			new html5jp.graph.radar("chart3").draw([["班级",2,3,5,2,4,4.8],["年级",3,2,6,6,4,1.5]],{aCap:["语文","数学","英语","物理","化学","生物"]});
			
			//柱形分层
			new html5jp.graph.vbar("chart6").draw([["分数",2,3,5,2,4,4.8]],{x:["","语文","数学","英语","物理","化学","生物"],y:["",2,4,6,8,9],yLabel:["层级A","层级B","层级C","层级D","层级AA"],yMax:9,yMin:0,legend:false});
		});
	</script>
  </head>
  <body>
<!-- 	<div id="chart1" style="width: 600px;height:450px"></div> -->
<!-- 	<div id="chart11" style="width: 600px;height:450px"></div> -->
	<div id="chart2" style="width: 600px;height:400px;color: #000000"></div><hr>
	<div id="chart3" style="width: 600px;height:400px"></div><hr>
<!-- 	<div id="chart4" style="width: 600px;height:400px"></div> -->
<!-- 	<div id="chart5" style="width: 600px;height:400px"></div> -->
	<div id="chart6" style="width: 600px;height:400px"></div><hr>
  </body>
</html>