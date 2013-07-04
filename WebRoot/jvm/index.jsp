<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Jvm状态</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		function getJvmInfo(){
			$.getJSON("${path}/jvm/info!getJvmInfo.action",function(data){
				$("#maxMemory").html(data.maxMemory);
				$("#totalMemory").html(data.totalMemory);
				$("#freeMemory").html(data.freeMemory);
				$("#thread").html(data.thread);
				setTimeout(getJvmInfo,2000);
			})
		}
		getJvmInfo();
	</script>
  </head>
  <body>
	最大可用内存：<span id="maxMemory"></span>M<br>
	已使用内存：<span id="totalMemory"></span>M<br>
	空闲内存：<span id="freeMemory"></span>M<br>
	线程数：<span id="thread"></span><br>
  </body>
</html>