<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title></title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
	
	</script>
  </head>
  <body>
	<form action="${path }/uploadFileServlet" method="post" enctype="multipart/form-data"><!-- multipart/form-data application/x-www-form-urlencoded -->
		<input type="text" name="aaa" value="aaaValue">
		<input type="file" name="fileNameImg" value="">
		<input type="submit" value="提交">
	</form>
	<hr>
	<form action="http://p5.dfs.kuaipan.cn:8080/cdlnode/1/fileops/upload_file" method="post" enctype="multipart/form-data">
		<input type="text" name="oauth_nonce" value="UhE1NOzdIq">
		<input type="text" name="oauth_timestamp" value="1405040216">
		<input type="text" name="oauth_consumer_key" value="xclLBmV2xaVe4Qe6">
		<input type="text" name="oauth_signature_method" value="HMAC-SHA1">
		<input type="text" name="oauth_version" value="1.0">
		<input type="text" name="oauth_token" value="04384ab27d316d6f6708d0f1">
		<input type="text" name="overwrite" value="true">
		<input type="text" name="root" value="app_folder">
		<input type="text" name="path" value="/a.txt">
		<input type="text" name="oauth_signature" value="oehw1Eof7fQ0QmtDe9Bei1HaPp4%3D">
		<input type="file" name="file" value="">
		<input type="submit" value="提交">
	</form>
	<hr>
	<form id="f1" action="" method="post" enctype="multipart/form-data"><!-- multipart/form-data application/x-www-form-urlencoded -->
		<input id="url" type="text"><input type="button" value="设置" onclick="$('#f1').attr('action',$('#url').val())">
		<input type="file" name="file" value="">
		<input type="submit" value="提交">
	</form>
  </body>
</html>