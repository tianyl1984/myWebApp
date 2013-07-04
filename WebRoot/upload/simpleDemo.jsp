<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title></title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
	
	</script>
  </head>
  <body>
	<form action="${path }/uploadFileServlet" method="post" enctype="multipart/form-data"><!-- multipart/form-data application/x-www-form-urlencoded -->
		<input type="text" name="aaa" value="aaaValue">
		<input type="file" name="fileNameImg" value="">
		<input type="submit" value="提交">
	</form>
  </body>
</html>