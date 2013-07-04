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
  	<s:form action="user!saveLoginUser.action" method="post" theme="simple">
  	<ul>
  		<c:forEach items="${users }" var="user">
  		<li><input type="radio" name="userId" value="${user.id }" id="${user.id }"><label for="${user.id }">${user.name }</label></li>
  		</c:forEach>
  	</ul>
  	<input type="button" value="登录" onclick="document.forms[0].submit()">
	</s:form>
  </body>
</html>