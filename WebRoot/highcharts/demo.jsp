<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    	<title>Title</title>
<%--     	<%@ include file="/common/jsp/htmlHeader.jsp"%> --%>
    	<script type="text/javascript" src="${path }/common/js/jquery-1.6.2.js"></script>
    	<script type="text/javascript" src="${path }/highcharts/js/highcharts.src.js"></script>
    	<script type="text/javascript">
	    	$(function () {
	    		
	            $('#container').highcharts({
	                chart: {
	                    type: 'line'
	                },
	                title: {
	                    text: 'US and USSR nuclear stockpiles'
	                },
	                credits:{
	                	enabled:false
	                },
	                xAxis: {
	                    labels: {
	                        formatter: function() {
	                            return this.value; // clean, unformatted number for year
	                        }
	                    },
	                    categories:['第1次考试','第2次考试','第3次考试','第4次考试','第5次考试','第6次考试']
	                },
	                yAxis: {
	                    title: {
	                        text: '分数'
	                    },
	                    labels: {
	                        formatter: function() {
	                            return this.value;
	                        }
	                    }
	                },
	                tooltip: {
	                    pointFormat: '{series.name} {point.y} {point.x}'
	                },
	                series: [{
	                    name: '数学',
// 	                    type: 'column',
	                    data: [ 60,70,null,90,50,60 ]
	                }, {
	                    name: '语文',
// 	                    type: 'column',
	                    data: [ 70,80,36,54,55,66 ]
	                }],
	                legend:{
	                	title:{
	                		text:'图例'
	                	}
	                }
	            });
	            
	        });
		</script>
	</head>
	<body>
		<div id="container" style="min-width: 310px;width:500px; height: 400px; margin: 0 auto"></div>
	</body>
</html>