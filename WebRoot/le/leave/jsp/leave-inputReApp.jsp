<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
	<script type="text/javascript">
		
	</script>
  </head>
  <body>
  	<s:form action="leave!save.action" method="post" theme="simple" id="inputReAppFormId">
  	<input type="hidden" name="id" value=${leave.id }>
  	<input type="hidden" name="result" id="resultId">
	<table>
		<tr><td>申请人</td><td>${leave.applicantUser.name }</td></tr>
		<tr><td>原因</td><td><textarea name="reason" rows="10" cols="50">${leave.reason }</textarea></td></tr>
		<tr><td>部门经理</td><td>${leave.deptUser.name }</td></tr>
		<tr><td>部门经理意见</td><td>${leave.deptSuggestion }</td></tr>
		<tr><td>当前节点</td><td>${task.name }</td></tr>
		<tr><td>人事主管</td><td>${currentUser.name }</td></tr>
		<tr><td>人事主管意见</td><td>${leave.leaderSuggestion }</td></tr>
	</table>
	</s:form>
  </body>
</html>