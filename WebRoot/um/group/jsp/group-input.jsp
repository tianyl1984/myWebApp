<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>user-input.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		
	</script>
  </head>
  <body>
  	<s:form action="group!save.action" method="post" theme="simple">
  	<s:hidden name="group.id" />
	<table>
		<tr><td>名称</td><td><s:textfield name="group.name" /></td></tr>
		<tr><td>代码</td><td><s:textfield name="group.code" /></td></tr>
		<tr><td>用户</td>
		<td>
			<s:checkboxlist list="#request.users" name="userIds" listKey="id" listValue="name"/>
		</td></tr>
		<tr><td colspan="2"><input type="button" value="保存" onclick="document.forms[0].submit()" /></td></tr>
	</table>
	</s:form>
  </body>
</html>