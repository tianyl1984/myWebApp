<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>数字化校园综合应用平台</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>

  </head>
  <body>
		<p>简单上传</p>
		<form action="${path }/uploadFileServlet" method="post" enctype="multipart/form-data"><!-- multipart/form-data application/x-www-form-urlencoded -->
			<input type="text" name="aaa" value="aaaValue">
			<input type="file" name="fileNameImg" value="">
			<input type="submit" value="提交">
		</form>
		<p>html5上传</p>
		<form action="${path}/fp/fileUpload!upload.action" method="post" enctype="multipart/form-data">
			<table width="100%">
				<tbody>
					<tr><td>名称：</td><td><input type="text" name="aaa" value="OK"/></td></tr>
					<tr><td>文件：</td><td><input type="file" id="myfile" name="myfileupload2" /><span id="resultSpan">0</span></td></tr>
					<tr><td>文件：</td><td id="td1"></td></tr>
					<tr><td colspan="2"><input type="submit" value="提交" /><input type="button" value="测试" onclick="m1()" /></td></tr>
				</tbody>
			</table>
		</form>
		<p>html5拖拽</p>
		<form action="${path}/fp/fileUpload!upload.action" method="post" enctype="multipart/form-data">
			<table width="100%">
				<tbody>
					<tr><td>名称：</td><td><input type="text" name="aaa" value="OK"/></td></tr>
					<tr>
						<td>文件：</td>
						<td>
						<div id="fileDiv" style="width: 500px;height: 50px;background: none repeat scroll 0 0 #E6EAE9">拖拽文件到此处</div>
						</td>
					</tr>
					<tr>
						<td>输出</td>
						<td>
						<textarea id="ta1" rows="10" cols="50"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
  </body>
  <script type="text/javascript">
	  function m1(){
		  var files = $("#myfile")[0].files;
		  var file = files[0];
	//	  alert("type:"+file.type)
	//	  alert("size:"+file.size)
		  for(var prop in file){
			  //alert(prop + ":" + file[prop]);
		  }
		  var formdata = new FormData();
	      formdata.append("sampleFile", file);
	      var xhr = new XMLHttpRequest();       
	      xhr.open("POST","${path}/swfUploadServlet", true);
	      xhr.upload.addEventListener("progress", function(e) {
	    	  $("#resultSpan").html(e.loaded);
	      });
	      xhr.onload = function(e) {
	        if (this.status == 200) {
	           alert(this.responseText);
	        }
	      };
	      xhr.send(formdata);
	  }
	  
	  $("#td1").html("<input type=\"file\" name=\"myfileupload3\"/>");
	  
	  $(document).ready(function(){
		  $("#fileDiv")[0].addEventListener("dragenter",function(e){
			  $("#ta1").val($("#ta1").val() + "\ndragenter")
		  },false)
		  $("#fileDiv")[0].addEventListener("dragleave",function(e){
			  $("#ta1").val($("#ta1").val() + "\ndragleave")
		  },false)
		  $("#fileDiv")[0].addEventListener("drop",function(e){
			  $("#ta1").val($("#ta1").val() + "\ndrop")
			  e.stopPropagation();   
			  e.preventDefault();
		  },false)
	  })
  </script>
</html>