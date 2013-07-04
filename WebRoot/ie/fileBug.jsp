<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		function test(){
			var file = "c:\\export.xml";
			var text = '<?xml version="1.0" ?><!DOCTYPE anything SYSTEM "res://'+file+'">';
			if(window.ActiveXObject){
				var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = true;
				try{
					xmlDoc.loadXML(text);
					if (xmlDoc.parseError.errorCode != 0) {
						if(Math.abs(xmlDoc.parseError.errorCode) == 2147024894){
							alert(file + '不存在');
						}
						if(Math.abs(xmlDoc.parseError.errorCode) == 2147024703){
							alert(file + '存在');
						}
					} else {
						alert('No Error');
					}
				}catch(e){
					alert(e);
				}
			}else{
				alert("IE Only!")
			}
		}
	</script>
  </head>
  <body>
	<input type="button" onclick="test()" value="test"><br>
  </body>
</html>