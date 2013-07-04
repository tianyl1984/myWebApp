<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
	<script type="text/javascript">
		
	</script>
  </head>
  <body>
  	<s:form action="leave!save.action" method="post" theme="simple" id="leaderFormId">
  	<input type="hidden" name="id" value=${leave.id }>
	<table>
		<tr><td>申请人</td><td>${leave.applicantUser.name }</td></tr>
		<tr><td>原因</td><td>${leave.reason }</td></tr>
		<tr><td>部门经理</td><td>${leave.deptUser.name }</td></tr>
		<tr><td>部门经理意见</td><td>${leave.deptSuggestion }</td></tr>
		<tr><td>当前节点</td><td>${task.name }</td></tr>
		<tr><td>人事主管</td><td>${currentUser.name }</td></tr>
		<tr><td>意见</td><td><s:textarea name="leaderSuggestion" rows="10" cols="50"/></td></tr>
		<tr><td>结果</td><td><s:radio list="#{'0':'同意','1':'不同意'}" name="result" value="0"></s:radio></td></tr>
	</table>
	</s:form>
  </body>
</html>