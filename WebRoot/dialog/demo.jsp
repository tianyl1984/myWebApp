<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">

	</script>
  </head>
  <body>
  完成<br>
	<c:forEach begin="0" end="10000">
		<input type="text" name="aaa">
	</c:forEach>
  </body>
</html>