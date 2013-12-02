<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
    <script type="text/javascript" src="${path }/common/js/jquery-ui-1.8.16.custom.js"></script>
	<script type="text/javascript">
	
		function startTest(){
			$("#result").timer();
		}
		
		function endTest(){
			$("#result").timer("stop");
		}
		
		$(document).ready(function(){
			(function($){
				$.widget("demo.timer",{
					options:{
						startTime:new Date()
					},
					_create:function(){
						console.log("_create");
						//dom操作，绑定事件
					},
					_init:function(){
						console.log("_init");
						//执行默认动作
						this.start();
					},
					start:function(){
						var that = this.element;
						var interval = setInterval(function(){
							$(that).html(new Date().toLocaleTimeString());
						},1000);
						this.options.interval = interval;
						aa = this;
					},
					stop:function(){
						console.log("stop");
						clearInterval(this.options.interval);
						$(this.element).html(this.options.startTime.toLocaleTimeString() + "-->" + new Date().toLocaleTimeString());
					},
					destroy:function(){
						
					}
				});
			})(jQuery);
		})
		
	</script>
	</head>
	<body>
		<input type="button" value="开始" onclick="startTest()">
		<input type="button" value="结束" onclick="endTest()">
		<div id="result"></div>
		<div id="result2"></div>
	</body>
</html>