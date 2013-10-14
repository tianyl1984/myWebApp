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
		
		function aaa(){
			$.post("${path}/um/user!save.action", {
				"user.name" : "aaaa",
				"user.email" : "aaaabbbb"
				}, 
				function(data) {
					$("#dataDiv").load("${path}/um/user!list.action");
					
				}
			)
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
  	<input type="button" onclick="aaa()" value="test">
  		<table>
		<thead>
			<tr><th>序号</th><th>名称</th><th>邮箱</th><th>操作</th></tr>
		</thead>
		<tbody id="dataDiv">
		</tbody>
		</table>
		<table><tr><td><input type="button" value="添加" onclick="goUrl('${path}/um/user!input.action')" /></td></tr></table>
	<table><tr><td><input type="button" value="添加2" onclick="aaa()" /></td></tr></table>
  </body>
  <script type="text/javascript">
  	$("#dataDiv").load("${path}/um/user!list.action");
  </script>
</html>