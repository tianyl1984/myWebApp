<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
	
		function pop(){
			$.dialog.load("${path}/dialog/demo.jsp",{
				title : '大数据',
				width : 580,
				height : 200,
				id : "aaa",
				ok : function(){
					return true;
			    },
			    cancel:function(){
			    	return true;
			    }
			});
		}
		
	</script>
  </head>
  <body>
	<input type="button" value="弹出层" onclick="pop()" >
  </body>
</html>