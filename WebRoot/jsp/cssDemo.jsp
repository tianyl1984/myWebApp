<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    <title>Title</title>
    <script type="text/javascript" src="${path }/common/js/jquery-1.6.2.js"></script>
	<script type="text/javascript">

	</script>
	<style type="text/css">
	.app-icon {
	    background:url("${path}/common/image/app_icons.jpg") 0 0;
	    border-radius: 10px 10px 10px 10px;
	    box-shadow: 1px 1px 2px #999999;
	    display: inline-block;
	    height: 50px;
	    width: 50px;
	}
	.app-icon2 {
	    background:url("${path}/common/image/app_icons.jpg") -50px 0;
	    border-radius: 10px 10px 10px 10px;
	    box-shadow: 1px 1px 2px #999999;
	    display: inline-block;
	    height: 50px;
	    width: 50px;
	}
	.app-icon3 {
	    background:url("${path}/common/image/app_icons.jpg") -100px 0;
	    border-radius: 10px 10px 10px 10px;
	    box-shadow: 1px 1px 2px #999999;
	    display: inline-block;
	    height: 50px;
	    width: 50px;
	}
	</style>
	</head>
	<body>
		<div class="app-icon"></div><hr>
		<div class="app-icon2"></div><hr>
		<div class="app-icon3"></div><hr>
		<div>
			<div id="div1">div1</div>
			<div id="div2">div2</div>
			<div id="div3">div3</div>
			<div id="div4">div4</div>
		</div>
	</body>
</html>