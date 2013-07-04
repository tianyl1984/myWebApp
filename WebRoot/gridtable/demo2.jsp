<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
		<title>jqgrid测试</title>
    	<%@ include file="/common/jsp/htmlHeader.jsp"%>
		<script type="text/javascript">
		
			function changeToGrid(){
				var mydata = [
				  {id:"1",name:"张三1",email:"zhangsan@123.com"},
				  {id:"2",name:"张三2",email:"zhangsan@123.com"},
				  {id:"3",name:"张三3",email:"zhangsan@123.com"},
				  {id:"4",name:"张三4",email:"zhangsan@123.com"},
				  {id:"5",name:"张三5",email:"zhangsan@123.com"},
				  {id:"6",name:"张三6",email:"zhangsan@123.com"},
				  {id:"7",name:"张三7",email:"zhangsan@123.com"}
				];
				$("#grid_table").jqGrid({
					height:300,
					data:mydata,
					colNames:["id","name","email"],
					colModel:[
						{name:"id"},
						{name:"name"},
						{name:"email"}
					],
					datatype:"local"
				})
			}
			
			$(document).ready(function(){
				changeToGrid();
			})
		</script>
	</head>
	<body>
	<table id="tab">
		<thead>
			<tr>
				<th rowspan="2">姓名</th>
				<th colspan="2">语文</th>
				<th colspan="2">aaa</th>
				<th rowspan="2">bbb</th>
				<th rowspan="2">ccc</th>
			</tr>
			<tr>
				<th>分数</th>
				<th>排名</th>
				<th>分数</th>
				<th>排名</th>
			</tr>
		</thead>
		<tbody id="tabbody">
		<c:forEach begin="1" end="500" var="index">
			<tr>
				<td>张三${index }</td><td>分数${index }</td><td>${index }</td><td>分数aaa${index }</td><td>aaa${index }</td><td>bbb${index }</td><td>ccc${index }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table id="grid_table"></table>
	</body>
</html>