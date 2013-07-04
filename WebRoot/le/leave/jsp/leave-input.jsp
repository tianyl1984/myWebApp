<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
	<script type="text/javascript">
		
	</script>
  </head>
  <body>
  	<s:form action="leave!save.action" method="post" theme="simple" id="appFormId">
	<table>
		<tr><td>申请人</td><td>${currentUser.name }</td></tr>
		<tr><td>原因</td><td><s:textarea name="reason" rows="10" cols="50"/></td></tr>
	</table>
	</s:form>
  </body>
</html>