<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		function a3(){
			var plus = document.getElementById('LODOP');
			if(plus == null || (typeof(plus) == "undefined")){
				var plusHtml = "<object id='LODOP' classid='clsid:2105C259-1E0C-4534-8141-A753534CB4CA' width=0 height=0>";
				plusHtml += "<embed id='LODOP_EM' type='application/x-print-lodop' width=0 height=0 pluginspage='install_lodop.exe'></embed>";
				plusHtml += "</object>";
		 		var newNode = document.createElement("div");
				newNode.innerHTML = plusHtml;
				document.body.appendChild(newNode);
			}
		    var lodop = document.getElementById('LODOP_EM');
		    lodop.PRINT_INIT("");
		    lodop.ADD_PRINT_HTM(10,10,"100%","100%",$("#aaa").html());
		    lodop.PREVIEW();
		}
	</script>
  </head>
  <body>
	<input type="button" onclick="a3()" value="打印"><br>
	<div id="aaa">
	<table>
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>邮箱</th>
				<th>地址</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach begin="0" end="50" step="1" var="index">
			<tr>
				<td>${index }</td>
				<td>张三${index }</td>
				<td>zhangsan@23.com${index }</td>
				<td>地sdf址${index }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
  </body>
</html>