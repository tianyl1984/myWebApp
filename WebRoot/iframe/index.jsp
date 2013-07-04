<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>index.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		
	</script>
  </head>
  <body>
  iframe外边的内容
  <iframe src="iframe.jsp" id="aaa" name="aaa" onreadystatechange="alert('')"></iframe>

  </body>
</html>