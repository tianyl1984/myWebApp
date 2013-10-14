<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>index.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
// 		document.oncontextmenu = function(event){
// 			$("#divId").html($("#divId").html() + ":oncontextmenu");
// 			return true;
// 		};
// 		document.onmouseup = function(event){
// 			$("#divId").html($("#divId").html() + ":onmouseup");
// 			return true;
// 		};
// 		document.onmousedown = function(event){
// 			$("#divId").html($("#divId").html() + ":onmousedown");
// 			return true;
// 		};
		
<%--		document.onmousemove = function(event){--%>
<%--			$("#divId").html($("#divId").html() + ":onmousemove");--%>
<%--			return true;--%>
<%--		};--%>
		function bindObserver(){
			//ie9以下不支持
			$("#divId").bind("DOMNodeInserted",function(e){
				alert($(e.target).html() + ":" + $(e.target).text());
			})
			return;
			// Firefox和Chrome早期版本中带有前缀
			var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver
			if(!MutationObserver){//IE
// 				$("#divId").bind("propertychange",function(e){
// 					alert($(e).html() + ":" + $(e).text());
// 				});
				$("#divId")[0].onpropertychange = function(e){
					alert(e);
				}
				return;
			}
			// 选择目标节点
			var target = document.querySelector('#divId');
			// 创建观察者对象
			var observer = new MutationObserver(function(mutations) {
				mutations.forEach(function(mutation) {
// 					alert(mutation.addedNodes);
					if(mutation.addedNodes != null){
						$(mutation.addedNodes).each(function(){
							alert($(this).html() + ":" + $(this).text());
						})
					}
				});    
			});
			
			// 配置观察选项:
			var config = { attributes: true, childList: true, subtree: true,characterData: true }
			// 传入目标节点和观察选项
			observer.observe(target, config);
		}
		
		function testm1(t){
			if(t < 0.3){
				$("#divId").append("<div>"+new Date().getTime() + "_</div>");
			}else if(t < 0.7){
				$("#divChild").html("");
			}else{
				$("#divChild").append("哈哈_");
			}
		}
		
		function testChrome1(){
			document.addEventListener("DOMNodeInserted", function(e) {
				console.log(e.target.innerHTML);
			}, false);
		}
		
		function testChrome2(){
			$("#divChild").append("<div>aaaaaaaa</div>");
		}
	</script>
  </head>
  <body >
	<div id="divId"><div><div id="divChild"></div></div></div>
	<input type="button" value="绑定观察者" onclick="bindObserver()">
	<input type="button" value="测试1" onclick="testm1(0.1)">
	<input type="button" value="测试2" onclick="testm1(0.5)">
	<input type="button" value="测试3" onclick="testm1(0.8)">
	<input type="button" value="测试Chrome" onclick="testChrome1()">
	<input type="button" value="测试Chrome2" onclick="testChrome2()">
  </body>
</html>