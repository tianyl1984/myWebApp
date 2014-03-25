<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    	<title>Title</title>
    	<%@ include file="/common/jsp/htmlHeader.jsp"%>
    	<script type="text/javascript">
    		
    		var ws = null;
    		
    		function sendMsg(){
    			if(ws != null){
    				ws.send($("#ipt").val());
    				$("#ipt").val("");
    			}
    			resetBtn();
    		}
    		
    		function closeWs(){
    			if(ws != null){
    				ws.close();
    				ws = null;
    			}
    			resetBtn();
    		}
    		
    		function start(){
    			ws = new WebSocket("ws://" + window.location.host + "${path}/ws/echo");
    			
    			ws.onmessage = function(evt){
    				$("#result").append("收到消息:" + evt.data + "<br>");
    			}
    			
    			ws.onclose = function(evt){
    				alert("close");
    				resetBtn();
    			}
    			
    			ws.onopen = function(evt){
    				$("#result").append("Open WebSocket!<br>");
    			}
    			resetBtn();
    		}
    		
    		function resetBtn(){
    			$("#open")[0].disabled = (ws != null);
    			$("#send")[0].disabled = (ws == null);
    			$("#close")[0].disabled = (ws == null);
    		}
    		
    		$(document).ready(function(){
    			resetBtn();
    		})
    		
		</script>
	</head>
	<body>
		<input type="text" id="ipt">
		<input id="open" type="button" onclick="start()" value="连接">
		<input id="send" type="button" onclick="sendMsg()" value="发送">
		<input id="close" type="button" onclick="closeWs()" value="断开">
		<div id="result"></div>
	</body>
</html>