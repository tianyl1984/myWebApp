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
	<table>
		<thead>
			<tr><th>序号</th><th>名称</th><th>代码</th><th>用户</th><th>操作</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${groups}" var="group" varStatus="groupIndex">
			<tr>
				<td>${groupIndex.index+1}</td>
				<td>${group.name}</td>
				<td>${group.code}</td>
				<td>
					<c:forEach items="${group.users }" var="user">
						${user.name }&nbsp;&nbsp;
					</c:forEach>
				</td>
				<td>
					<input type="button" value="修改" onclick="goUrl('${path}/um/group!input.action?id=${group.id}')" />
					<input type="button" value="删除" onclick="goUrl('${path}/um/group!delete.action?id=${group.id}')" />
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<table><tr><td><input type="button" value="添加" onclick="goUrl('${path}/um/group!input.action')" /><input type="button" value="主页" onclick="goUrl('${path}/')" /></td></tr></table>
  </body>
</html>