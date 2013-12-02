<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
	
		function startTest(){
			var arr = ["aaa=bbb"];
			var flag = true;
// 			while(flag){
				add(arr);
				var formData = arr.join("");
				$.ajax({
					type:"POST",
					url:"${path}/web/pojo!ajaxDemo.action",
					data:formData,
					dataType:"text",
					error:function(){
						flag = false;
						$("#result2").html("error");
					},
					success:function(data){
						if(data == null || data == ""){
							flag = false;
							$("#result2").html("error:" + data);
						}else{
							$("#result").html(data + ":" + $("#num").val());
						}
					}
				});
// 			}
		}
		
		function add(arr){
			for(var i=0;i<parseInt($("#num").val());i++){
				arr.push("&b" + i + "=c" + i);
			}
		}
	</script>
	</head>
	<body>
		<input type="text" value="1000" id="num">
		<input type="button" value="开始测试" onclick="startTest()">
		<div id="result"></div>
		<div id="result2"></div>
	</body>
</html>