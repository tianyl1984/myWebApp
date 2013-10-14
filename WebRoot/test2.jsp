<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>数字化校园综合应用平台</title>
<%--     <%@ include file="/common/jsp/htmlHeader.jsp"%> --%>
  </head>
  <body>
  <ul style="height: 300px;overflow: auto">
  	<li><a href="${path}/um/user!login.action">用户登录</a></li>
  	<li><a href="${path}/um/user!list.action">用户管理</a></li>
  	<li><a href="${path}/um/group!list.action">分组管理</a></li>
  	<li><a href="#20130912084411704660646220830445">锚点测试</a></li>
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
  	<li><a href="javascript:void(0)" onclick="alert(1)">测试Spring的PropertyPlaceholderConfigurer</a></li>
  	<li><a href="javascript:void(0)" onclick="jsp2Html()">测试jsp生成html</a></li>
  	<li><a href="${path}/struts2/demo.jsp">测试struts2自动组装提交值</a></li>
  	<li><a href="${path}/pqgrid/demo.jsp">pqgrid测试</a></li>
  	<li><a href="${path}/print/demo.jsp">打印测试</a></li>
  	<li><a href="${path}/jqplot/demo.jsp">jqplot测试</a></li>
  	<li><a href="${path}/validate/demo.jsp">js验证框架</a></li>
  </ul>
  <div style="height: 100px;overflow: auto">
  <a href="#20130912084411704660646220830445">锚点测试</a>
  <div style="height: 200px"></div>
	<table>
	  	<tr>
	  		<td>aaaa</td>
	  		<td></td>
	  		<td>
			  <a name="20130912084411704660646220830445"></a>
	  		</td>
	  	</tr>
  	</table>
  </div>
  </body>
	<style>
		li{
			line-height: 30px;
		}
	</style>
</html>