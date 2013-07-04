<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>数字化校园综合应用平台</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
<%--     <script type="text/javascript" src="${path }/aaa.js"></script> --%>
	<script type="text/javascript">
	//alert(navigator["userAgent"])
// 	alert(new Date().getYear())
	</script>
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
  <ul>
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
  </ul>
  <input type="checkbox" onclick="aaa(this)" value="aaaaa" name="bbb" id="ttt">点击<br>
  <input type="button" onclick="a3()" value="弹出层"><br>
  <input type="button" onclick="a4()" value="js测试"><br>
  <input type="button" onclick="a5()" value="word文档"><br>
  <h2 id="h2Id"></h2><br>
  <div class="box1" id="divId" boxTitle="<input style='height:20px;border:#B4B4B4 1px solid;padding:2px 5px;font-size:14px;' value='班级1' />&nbsp;&nbsp;(人数<font color='green'>30</font>)&nbsp;&nbsp;&nbsp;&nbsp;<a href='###'><span class='fm f8 mr5'>t</span>删除此班级</a>">
  	jskladfjlafsdk
  </div>
  <div id="div2"></div>
  <iframe src="" height="30px"></iframe>
  
  <div id="p2">
	  <div id="ddd1">
		  <div id="ddd5"></div>
		  <div id="ddd6"></div>
		  <div id="ddd7"></div>
	  </div>
	  <div id="ddd2"></div>
	  <div id="ddd3"></div>
	  <div id="ddd4" style="width: 300px;height: 300px;overflow: scroll;">1234</div>
	  <img alt="aaa" src="${path }/resource/aaa.png">
	  <form method="post" action="${path }/">
	  <input type="text" name="username">
	  <input type="password" name="password">
	  <input type="submit" value="提交">
	  </form>
<%-- 	  <video controls src="${path }/resource/aaa.mp4"></video> --%>
  </div>
  </body>
  	<script type="text/javascript">
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
  	
  		function a5(){
  			
  			window.location.href = "${path}/downDoc";	
  		}
  		
  		function a4(){
  			alert(11)
  			return
  			window.open("www.baidu.com")
  			//alert(cmdExecute("start www.baidu.com"))
  			//alert(cmdExecute2("start www.baidu.com"))
  			//alert(123)
//   			alert(chrome.app)
//   		alert($("#p2>div").size());
//   		alert(document.getElementById("p2").getElementsByTagName("div").length);
// 			alert($("#ddd5")[0].parentNode.innerHTML);
// 			alert(document.getElementById("ddd4").innerText)
// 			document.getElementById("ddd4").textContent = parseInt(document.getElementById("ddd4").textContent) + 1;
  		}
  		
		function aaa(obj){
			alert(obj.checked);
		}
		function a1(){
			//document.getElementById("ttt").click();
			alert($("#divId").attr("boxTitle"))
			$("#h2Id").append($("#divId").attr("boxTitle"));
		}
		function a2(){
			$("#div2").load("${path}/test.jsp");
		}
		function a3(){
			window.open("http://www.google.com.hk")
			return;
// 			$.dialog.load("${path}/upload/index.jsp",{
// 				title : 'aaa',
// 				width : 700,
// 				height : 400,
// 				id : 'afdsasdf',
// 				ok: function(){
// 			    }
// 			});

			$.dialog.load("${path}/test.jsp",{
				title : 'aaa',
				width : 700,
				height : 400,
				id : 'afdsasdf',
				ok: function(){
			    }
			});
			
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/test.jsp',
			  	success : function(data){
// 					new artDialog({content:data,width:500,height:300},function(){});
					$("#ddd4").html(data);
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