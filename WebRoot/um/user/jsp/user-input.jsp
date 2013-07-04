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
  	<s:form action="user!save.action" method="post" theme="simple">
  	<select name="userType">
		<option value="user1" <c:if test="${userType eq 'user1'}">selected="selected"</c:if>>用户1</option>
		<option value="user2" <c:if test="${userType eq 'user2'}">selected="selected"</c:if>>用户2</option>
		<option value="user3" <c:if test="${userType eq 'user3'}">selected="selected"</c:if>>用户3</option>
	</select>
  	<s:hidden name="user.id" />
	<table>
		<tr><td>名称</td><td><s:textfield name="user.name" /></td></tr>
		<tr><td>邮箱</td><td><s:textfield name="user.email" /></td></tr>
		<tr><td>分数</td><td><s:textfield name="user.score" /></td></tr>
		<tr><td colspan="2"><input type="button" value="保存" onclick="document.forms[0].submit()" /></td></tr>
	</table>
	</s:form>
  </body>
</html>