<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>user.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		function goUrl(url){
			location.href = url;
		}
	</script>
  </head>
  <body>
  	<form action="${path}/um/user!list.action" method="post">
  		<select name="userType" onchange="document.forms[0].submit()">
  			<option value="user1" <c:if test="${userType eq 'user1'}">selected="selected"</c:if>>用户1</option>
  			<option value="user2" <c:if test="${userType eq 'user2'}">selected="selected"</c:if>>用户2</option>
  			<option value="user3" <c:if test="${userType eq 'user3'}">selected="selected"</c:if>>用户3</option>
  		</select>
  	</form>
	<table>
		<thead>
			<tr><th>序号</th><th>名称</th><th>邮箱</th><th>操作</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user" varStatus="userIndex">
			<tr>
				<td>${userIndex.index+1}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>
					<input type="button" value="修改" onclick="goUrl('${path}/um/user!input.action?id=${user.id}')" />
					<input type="button" value="删除" onclick="goUrl('${path}/um/user!delete.action?id=${user.id}')" />
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<table><tr><td><input type="button" value="添加" onclick="goUrl('${path}/um/user!input.action')" /><input type="button" value="主页" onclick="goUrl('${path}/')" /></td></tr></table>
  </body>
</html>