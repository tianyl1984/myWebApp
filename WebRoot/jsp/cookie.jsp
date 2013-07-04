<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>cookie</title>
	<script type="text/javascript">
		function readCookie(){
			alert(document.cookie);
		}
	</script>
  </head>
  <body>
  <input type="button" value="读取Cookie" onclick="readCookie()">
  </body>
</html>