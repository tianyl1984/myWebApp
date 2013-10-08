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
    	<script type="text/javascript" src="${path }/common/js/pqgrid/com.ue.pqgrid.js"></script>
    	
    	<link rel="stylesheet" href="${path }/common/js/pqgrid/pqgrid2.css" />
    	<link rel="stylesheet" href="${path }/common/css/table.css" />
<%--     	<link rel="stylesheet" href="${path }/common/css/popupLayer/css/default.css" /> --%>
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
// 				var tbl = $("#tab2");
// 	            var obj = $.adapter.tableToArray($("#tabbody2"));
// 	            var newObj = {width:700,height:400,title:"Grid From Table",resizable:true,freezeCols:1,editable:false,numberCell:false};
// 	            newObj.dataModel = {data:obj.data};
// 	            newObj.tdAttr = obj.tdAttr;
// 				newObj.colModel = [
// 				                   {title:"姓名"},
// 				                   {title:"语文",colModel:[{title:"分数",sortable:false,width:100},{title:"排名",dataType:"integer"}]},
// 				                   {title:"aaa",colModel:[{title:"分数",sortable:false,width:100},{title:"排名"}]},
// 				                   {title:"bbb"},
// 				                   {title:"ccc"}
// 				                   ];
// 	            $("#grid_table2").pqGrid(newObj);
// 	            tbl.css("display", "none");
	            $("#grid_table2").height($("#tab2").height());
				$("#grid_table2").width($("#tab2").width());
	            $("#grid_table2").height(150);
				$("#grid_table2").width(657);
// 				console.log($("#grid_table2").width())
	            com.ue.pqgrid.changeTable("tab2","grid_table2");
			}
			
			function changeToGrid3(){
// 				alert($("#tab3").width() + ":" + $("#tab3").height());
				$("#grid_table3").width($("#tab3").width());
				$("#grid_table3").height($("#tab3").height());
				$("#grid_table3").width(500);
				$("#grid_table3").height(200);
				com.ue.pqgrid.changeTable("tab3","grid_table3");
			}
			
			$(document).ready(function(){
// 				changeToGrid2();
				changeToGrid3();
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
	<table id="tab22" freezeCols="2" style="display: none">
		<thead>
			<tr>
				<th rowspan="2">姓名</th>
				<th rowspan="2">班级班级班级班级</th>
				<th colspan="2">语文</th>
				<th colspan="2">aaa</th>
				<th rowspan="2">bbb</th>
				<th rowspan="2">ccc</th>
				<th rowspan="2">ddd</th>
				<th rowspan="2">eee</th>
				<th rowspan="2">fff</th>
				<th rowspan="2">ggg</th>
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
				<td>张三${index }</td>
				<td>1班</td>
				<td>分数${index }</td>
				<td>${index }</td>
				<td>分数aaa${index }</td>
				<td>aaa${index }</td>
				<td>bbb${index }</td>
				<td>ccc${index }</td>
				<td>ddd${index }</td>
				<td>eee${index }</td>
				<td>fff${index }</td>
				<td>ggg${index }</td>
			</tr>
			<tr>
				<td>张三2</td>
				<td>1班</td>
				<td>分数${index }</td>
				<td>${index }</td>
				<td>分数aaa${index }</td>
				<td>aaa${index }</td>
				<td>bbb${index }</td>
				<td>ccc${index }</td>
				<td>ddd${index }</td>
				<td>eee${index }</td>
				<td>fff${index }</td>
				<td>ggg${index }</td>
			</tr>
			<tr>
				<td colspan="2">张三3</td>
				<td>分数${index }</td>
				<td>${index }</td>
				<td>分数aaa${index }</td>
				<td>aaa${index }</td>
				<td>bbb${index }</td>
				<td>ccc${index }</td>
				<td>ddd${index }</td>
				<td>eee${index }</td>
				<td>fff${index }</td>
				<td>ggg${index }</td>
			</tr>
		</tbody>
	</table>
	<div id="grid_table2"></div>
	<table id="tab3">
		<thead>
			<tr>
				<th>Header1</th>
				<th colspan="2">Header2</th>
				<th>Header4</th>
			</tr>
			<tr>
				<th>Header11</th>
				<th>Header22</th>
				<th>Header33</th>
				<th>Header44</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
			</tr>
			<tr>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
			</tr>
			<tr>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
			</tr>
		</tbody>
	</table>
	<div id="grid_table3"></div>
<table freezecols="1" id="tab2" style="display: none">
		<thead>
			<tr>
				<th rowspan="2">姓名</th>
				<th rowspan="2">优秀率</th>
				<th rowspan="2">及格率</th>
				<th rowspan="2">平均分</th>
				<th colspan="3">年级排名</th>
				<th colspan="4">a排名</th>
				<th colspan="4">b排名</th>
			</tr>
			<tr>
				<th>及格率</th>
				<th>优秀率</th>
				<th>平均分</th>
				<th>及格率</th>
				<th>优秀率</th>
				<th>平均分</th>
				<th>达标情况</th>
				<th>及格率</th>
				<th>优秀率</th>
				<th>平均分</th>
				<th>达标情况</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>教师47</td>
				<td>16.98%</td>
				<td>40.25%</td>
				<td>48.1</td>
				<td>1</td>
				<td>1</td>
				<td>1</td>
				<td>
					<span>1</span>
				</td>
				<td>
					<span>1</span>
				</td>
				<td>
					<span>1</span>
				</td>
				<td>0项未达标</td>
				<td>
					<span>1</span>
				</td>
				<td>
					<span>1</span>
				</td>
				<td>
					<span>1</span>
				</td>
				<td>0项未达标</td>
			</tr>
		</tbody>
	</table>
	</body>
</html>