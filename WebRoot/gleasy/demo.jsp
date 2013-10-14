<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/gleasy/header.jsp"%>
	<script type="text/javascript">
		function showUser(){
			com.gleasy.os.SystemInfo.getCurrentUser(function(user){
				alert(user);
			},true);
		}
		
		function event1(){
			window.application.sendNotify("eventId1","eventId1");
		}
		
		function event2(){
			window.application.sendNotify("eventId2","eventId2");
		}
		
		function test(){
			var param = {};
			param.message = "您当前的客户端版本太低,";
			param.confirmMessage = "建议立即升级至最新版。";
			param.okBtn = true;
			param.okBtnText = "下载Gleasy客户端";
			param.cancelBtn = true;
        	param.cancelBtnText = "不用了,谢谢";
        	param.icon = "prompt";//icon={confirm,fail,success,prompt,warning}
        	//param.system = true;
        	param.callback = function(obj){
        		if(obj.result){
        			//window.application.sendNotify(Constants.command.os.openUrl,Constants.config.clientPrefix+"/platform/softwares/client/download.html");
        		}
        	}
			window.application.sendNotify(Constants.command.system.alert,param);
		}
	</script>
  </head>
  <body>
	<input type="button" value="日志" onclick="$().log('log1234')" ><br>
	<input type="button" value="获取当前用户" onclick="showUser()" ><br>
	<input type="button" value="Event1()" onclick="event1()" ><br>
	<input type="button" value="Event2()" onclick="event2()" ><br>
	<input type="button" value="测试" onclick="test()" ><br>
	<div id="testDiv">测试区域</div>
  </body>
</html>