<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    	<title>Title</title>
    	<%@ include file="/common/jsp/htmlHeader.jsp"%>
    	<script type="text/javascript">

    		function start(){
    			var cor = null;
    			
    			if (window.XMLHttpRequest) {
    			    cor = new XMLHttpRequest();
    			} else {
    			    alert("Your browser does not support Cross-Origin request!");
    			    return;
    			}

    			cor.onreadystatechange = function () {
   			    	$("#result").append("state:" + cor.readyState + "<br>");
    			    if (cor.readyState == 4) {
    			    	$("#result").append(cor.responseText + "<br>");
    			    }
    			};

    			var data = 'Some fake data';
    			var url = "http://127.0.0.1:8088/myWebApp/web/pojo!corDemo.action";
    			url = "http://192.168.30.123:8088/myWebApp/web/pojo!corDemo.action";
    			cor.open('POST', url, true);
    			cor.withCredential = "true";
    			cor.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    			cor.send('data=' + data);
    		}
    		
		</script>
	</head>
	<body>
		<input type="button" value="测试跨域请求" onclick="start()">
		<div id="result"></div>
	</body>
</html>