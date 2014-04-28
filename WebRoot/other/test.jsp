<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<c:set var="row" value="10"/>
<c:set var="cell" value="10"/>
<div>
	<table>
		<thead>
			<tr>
			<c:forEach begin="0" end="${cell }" var="index">
				<td nowrap="nowrap">头部${index }</td>
			</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach begin="0" end="${row }" var="index1">
			<tr>
			<c:forEach begin="0" end="${cell }" var="index">
				<td nowrap="nowrap">row${index1}:内容${index }</td>
			</c:forEach>
			</tr>
			</c:forEach>			
		</tbody>
	</table>
</div>
<script>
	function aaaa(){
// 		if( == '1'){
			alert(123);
// 		}
	}
</script>