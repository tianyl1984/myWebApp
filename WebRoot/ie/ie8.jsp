<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>IE8</title>
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#userAgent").html(navigator.userAgent);
		});
		
		function changeToIe7(){
			window.location.href = "${path}/ie/ie7.jsp";
		}
	</script>
  </head>
  <body>
	<button onclick="changeToIe7()">切换到ie7</button>
	<div id="userAgent"></div>
  </body>
</html>