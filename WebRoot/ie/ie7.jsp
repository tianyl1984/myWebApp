<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>IE7</title>
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#userAgent").html(navigator.userAgent);
		});
		
		function changeToIe8(){
			window.location.href = "${path}/ie/ie8.jsp";
		}
	</script>
  </head>
  <body>
	<button onclick="changeToIe8()">切换到ie8</button>
	<div id="userAgent"></div>
  </body>
</html>