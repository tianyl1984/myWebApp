<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>WebSocket聊天</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
    <script type="text/javascript" src="${path }/webSocket/jquery.atmosphere.js"></script>
	<script type="text/javascript" src="${path }/webSocket/application.js"></script>
	<script type="text/javascript">
	
	</script>
  </head>
  <body>
	<div id="header"><h3>Atmosphere WebSocket ONLY Chat</h3></div>
	<div id="detect"><h3>Detecting what the browser and server are supporting</h3></div>
	<div id="content"></div>
	<div>
	    <span id="status">Connecting...</span>
	    <input type="text" id="input" disabled="disabled"/>
	</div>
  </body>
 	<style>
        * {
            font-family: tahoma;
            font-size: 12px;
            padding: 0px;
            margin: 0px;
        }

        p {
            line-height: 18px;
        }

        div {
            width: 500px;
            margin-left: auto;
            margin-right: auto;
        }

        #detect {
            padding: 5px;
            background: #ffc0cb;
            border-radius: 5px;
            border: 1px solid #CCC;
            margin-top: 10px;
        }

        #content {
            padding: 5px;
            background: #ddd;
            border-radius: 5px;
            border: 1px solid #CCC;
            margin-top: 10px;
        }

        #header {
            padding: 5px;
            background: #f5deb3;
            border-radius: 5px;
            border: 1px solid #CCC;
            margin-top: 10px;
        }

        #input {
            border-radius: 2px;
            border: 1px solid #ccc;
            margin-top: 10px;
            padding: 5px;
            width: 400px;
        }

        #status {
            width: 88px;
            display: block;
            float: left;
            margin-top: 15px;
        }
    </style>
</html>