<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
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