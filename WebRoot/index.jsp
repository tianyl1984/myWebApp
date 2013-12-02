<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
	    <title>数字化校园综合应用平台</title>
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	</head>
	<body>
		Hello World!${currentUser.name }你好！<a href="${path}/um/user!logout.action">退出</a><br>
	<input id="aaaaa" type="text" value="sadf45sfd">
	<select name="userType" onchange="submitUserTypeChange(this)">
		<option value="">请选择</option>
		<option value="user1" <c:if test="${userType eq 'user1'}">selected="selected"</c:if>>用户1</option>
		<option value="user2" <c:if test="${userType eq 'user2'}">selected="selected"</c:if>>用户2</option>
		<option value="user3" <c:if test="${userType eq 'user3'}">selected="selected"</c:if>>用户3</option>
	</select>
	<input type="button" value="test1" onclick="test(0)">
	<input type="button" value="test2" onclick="test(1)">
	<ul style="height: 500px;overflow: auto">
	  	<li><a href="${path}/um/user!login.action">用户登录</a></li>
	  	<li><a href="${path}/um/user!list.action">用户管理</a></li>
	  	<li><a href="${path}/um/group!list.action">分组管理</a></li>
	  	<li><a href="${path}/toServlet.jsp">Post提交到Servlet</a></li>
	  	<li><a href="${path}/iframe/index.jsp">iframe事件测试</a></li>
	  	<li><a href="${path}/prerender/page.jsp">预加载测试</a></li>
	  	<li><a href="${path}/jsEvent/jsEventTest.jsp">js事件</a></li>
	  	<li><a href="${path}/jsDemo/demo.jsp">js Demo</a></li>
	  	<li><a href="${path}/ie/ie8.jsp">IE</a></li>
	  	<li><a href="${path}/ie/firebug-in-ie.jsp">IE中使用firebug</a></li>
	  	<li><a href="${path}/ie/fileBug.jsp">IE可判断文件是否存在的bug</a></li>
	  	<li><a href="${path}/download">下载</a></li>
	  	<li><a href="${path}/fp/upload.jsp">上传组件</a></li>
	  	<li><a href="${path}/upload/index.jsp">上传文件</a></li>
	  	<li><a href="${path}/gridtable/demo.jsp">jgrid表格</a></li>
	  	<li><a href="${path}/gridtable/demo2.jsp">jgrid表格2</a></li>
	  	<li><a href="${path}/activiti/activiti.jsp">工作流测试</a></li>
	  	<li><a href="${path}/le/leave/jsp/index.jsp">请假工作流</a></li>
	  	<li><a href="${path}/le/dynamicLeave/jsp/index.jsp">动态表单请假工作流</a></li>
	  	<li><a href="${path}/webSocket/index.jsp">WebSocket聊天</a></li>
	  	<li><a href="${path}/cookieServlet">Cookie测试</a></li>
	  	<li><a href="${path}/jvm/index.jsp">JVM状态</a></li>
	  	<li><a href="${path}/dialog/index.jsp">弹出层</a></li>
	  	<li><a href="${path}/gleasy/demo.jsp">Gleasy</a></li>
	  	<li><a href="javascript:void(0)" onclick="propertyPlaceholderConfigurerTest()">测试Spring的PropertyPlaceholderConfigurer</a></li>
	  	<li><a href="javascript:void(0)" onclick="jsp2Html()">测试jsp生成html</a></li>
	  	<li><a href="${path}/struts2/demo.jsp">测试struts2自动组装提交值</a></li>
	  	<li><a href="${path}/pqgrid/demo.jsp">pqgrid测试</a></li>
	  	<li><a href="${path}/print/demo.jsp">打印测试</a></li>
	  	<li><a href="${path}/jqplot/demo.jsp">jqplot测试</a></li>
	  	<li><a href="${path}/validate/demo.jsp">js验证框架</a></li>
	  	<li><a href="javascript:void(0)" onclick="a3()">弹出test.jsp</a></li>
	  	<li><a href="${path}/web/pojo!exportWebToDoc.action">导出word</a></li>
	  	<li><a href="${path}/jsp/cssDemo.jsp">CSS测试</a></li>
	  	<li><a href="${path}/jsp/ajaxDemo.jsp">ajax参数个数测试</a></li>
	  	<li><a href="${path}/jsp/jqUIDemo.jsp">jquery UI demo</a></li>
	</ul>
  
  <div id="aaa">
<c:set var="row" value="0"/>
<c:set var="cell" value="0"/>
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
  </div>
  
	</body>
  	<script type="text/javascript">
  	
  		function test(type){
  			var $aaa = $("#aaa");
  			var aaa = $aaa[0];
			var t1 = 
  			if(type == 0){
  				aaa.innerHTML = "";
  				
  			}else{
  				$aaa.html("");
  			}
  		}
  	
  		function jsp2Html(){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/web/jsp2Html!test.action',
			  	success : function(data){
			  		alert(data);
			  	}
			});
  		}
  		
  		function propertyPlaceholderConfigurerTest(){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/test/propertyPlaceholderTest!test.action',
			  	success : function(data){
			  		alert(data);
			  	}
			});
  		}

		function a3(){
			$.dialog.load("${path}/test.jsp",{
				title : 'aaa',
				width : 700,
				height : 400,
				id : 'afdsasdf',
				ok: function(){
					return true;
			    }
			});
		}
		
		function submitUserTypeChange(obj){
			if(obj.value == ""){
				return;
			}
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/appServlet?userType=' + obj.value,
			  	success : function(data){
			  		window.location.href = "${path}/";
			  	}
			});
		}
		
	</script>
	<style>
		li{
			line-height: 30px;
		}
	</style>
</html>