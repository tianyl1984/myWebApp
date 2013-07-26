<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
		<title>pqgrid测试</title>
<%--     	<%@ include file="/common/jsp/htmlHeader.jsp"%> --%>

<%--     	<script type="text/javascript" src="${path }/common/js/jquery-1.8.3.js"></script> --%>
<%--     	<script type="text/javascript" src="${path }/common/js/jquery-ui-1.9.1.js"></script> --%>
<%--     	<script type="text/javascript" src="${path }/common/js/pqgrid/pqgrid.js"></script> --%>

		<script type="text/javascript" src="${path }/common/js/jquery-1.6.2.js"></script>
    	<script type="text/javascript" src="${path }/common/js/jquery-ui-1.8.16.custom.js"></script>
<%--     	<script type="text/javascript" src="${path }/common/js/jquery-ui-1.9.1.js"></script> --%>
    	<script type="text/javascript" src="${path }/common/js/pqgrid/pqgrid2.js"></script>
    	
    	<link rel="stylesheet" href="${path }/common/js/pqgrid/pqgrid.css" />
    	<link rel="stylesheet" href="${path }/common/css/table.css" />
    	<link rel="stylesheet" href="${path }/common/css/popupLayer/css/default.css" />
		<script type="text/javascript">
		
			function changeToGrid(){
				var tbl = $("#tab");
	            var obj = $.adapter.tableToArray($("#tabbody"));
	            var newObj = {width:700,height:400,title:"Grid From Table",resizable:true,freezeCols:1,editable:false,numberCell:false};
	            newObj.dataModel = {data:obj.data};
// 	            newObj.colModel = obj.colModel;
				newObj.colModel = [
				                   {title:"姓名"},
				                   {title:"语文",colModel:[{title:"分数",sortable:false,width:100},{title:"排名",dataType:"integer"}]},
				                   {title:"aaa",colModel:[{title:"分数",sortable:false,width:100},{title:"排名"}]},
				                   {title:"bbb"},
				                   {title:"ccc"}
				                   ];
	            $("#grid_table").pqGrid(newObj);
	            tbl.css("display", "none");
			}
			
			function changeToGrid2(){
				var tbl = $("#tab2");
	            var obj = $.adapter.tableToArray($("#tabbody2"));
	            var newObj = {width:700,height:400,title:"Grid From Table",resizable:true,freezeCols:1,editable:false,numberCell:false};
	            newObj.dataModel = {data:obj.data};
	            newObj.tdAttr = obj.tdAttr;
				newObj.colModel = [
				                   {title:"姓名"},
				                   {title:"语文",colModel:[{title:"分数",sortable:false,width:100},{title:"排名",dataType:"integer"}]},
				                   {title:"aaa",colModel:[{title:"分数",sortable:false,width:100},{title:"排名"}]},
				                   {title:"bbb"},
				                   {title:"ccc"}
				                   ];
	            $("#grid_table2").pqGrid(newObj);
	            tbl.css("display", "none");
			}
			
			
			
			$(document).ready(function(){
				changeToGrid2();
			})
		</script>
	</head>
	<body>
	<div style="display: none">
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
					<td>张三${index }</td>
					<td>分数${index }</td>
					<td>${index }</td>
					<td>分数aaa${index }</td>
					<td>aaa${index }</td>
					<td>bbb${index }</td>
					<td>ccc${index }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div id="grid_table"></div>
	</div>
	<table id="tab2">
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
		<tbody id="tabbody2">
			<tr>
				<td rowspan="3">张三${index }</td>
				<td>分数${index }</td>
				<td>${index }</td>
				<td>分数aaa${index }</td>
				<td>aaa${index }</td>
				<td>bbb${index }</td>
				<td>ccc${index }</td>
			</tr>
			<tr>
				<td>分数${index }</td>
				<td>${index }</td>
				<td>分数aaa${index }</td>
				<td>aaa${index }</td>
				<td>bbb${index }</td>
				<td>ccc${index }</td>
			</tr>
			<tr>
				<td>分数${index }</td>
				<td>${index }</td>
				<td>分数aaa${index }</td>
				<td>aaa${index }</td>
				<td>bbb${index }</td>
				<td>ccc${index }</td>
			</tr>
		</tbody>
	</table>
	<div id="grid_table2"></div>
	</body>
</html>