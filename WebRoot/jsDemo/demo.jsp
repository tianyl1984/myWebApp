<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    <title>JS Demo</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
	
		var data = [];
		var t;
		
		function showCpuChart(){
			pulse();
		}
		
		function pulse(){
			if(data.length >= 20){
				alert(data);
				return;
			}
			t && data.push(Date.now() - t);
			t = Date.now();
			setTimeout(pulse,500);
		}
	</script>
	</head>
	<body>
		<input type="button" value="测试cpu变化" onclick="showCpuChart()">
	</body>
</html>