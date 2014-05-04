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
		<hr>
		<div>没有设置</div>
		<div>
			<div>div1</div>
			<div>div2</div>
			<div>div3</div>
			<div>div4</div>
		</div>
		<hr>
		<div>div1设置position: fixed，按照浏览器的窗口进行定位，同级元素位置按div1不存在处理</div>
		<div>
			<div style="position: fixed;top: 360px;left: 50px">div1</div>
			<div>div2</div>
			<div>div3</div>
			<div>div4</div>
		</div>
		<hr>
		<div>div1设置position: relative;按照它<span style="color: #ff0000;">理应所在</span>的位置进行偏移，并且不影响同级元素位置；relative的偏移是基于对象的margin的左上侧</div>
		<div>
			<div style="position: relative;top: 10px;left: 50px">div1</div>
			<div>div2</div>
			<div>div3</div>
			<div>div4</div>
		</div>
		<hr>
		<div>div1设置position: absolute，以parent中有position为absolute或者relative时，按parent定位，没有就按body定位；同级元素位置按div1不存在处理；从padding开始的地方(即只从padding的左上角开始)进行定位</div>
		<div style="position: relative;background-color:#ffc300 ">
			<div style="position: absolute;top: 10px;left: 50px">div1</div>
			<div>div2</div>
			<div>div3</div>
			<div>div4</div>
		</div>
	</body>
</html>