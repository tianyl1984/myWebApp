<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>Title</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
    <script type="text/javascript" src="${path }/common/js/validate/jquery.validationEngine.js"></script>
    <script type="text/javascript" src="${path }/common/js/validate/languages/jquery.validationEngine-zh_CN.js"></script>
    <script type="text/javascript" src="${path }/common/js/validate/contrib/other-validations.js"></script>
    <style type="text/css">
    	.formErrorContent{
    		color: #CC0000;
    	}
    </style>
	<script type="text/javascript">
		function validation(){
			$('#form1').reloadValidationEngine();
			$("#result").html("");
			if($('#form1').validationEngine("validate")){
				$("#result").html("通过");
			}else{
				$("#result").html("未通过");
			}
		}
		
		function add(){
			var id = "id" + new Date().getTime();
			var html = "<tr><td>名称add：</td><td><input id='" + id + "' type='text' name='adaa" + id + "' value='' class='validate[required]'></td></tr>";
			$("#tab").prepend(html);
		}
		
		$(document).ready(function(){
// 			$('#form1').validationEngine();
		})
	</script>
  </head>
  <body>
  	<div id="result"></div>
  	<form id="form1">
	<table id="tab">
		<tr>
			<td>名称：</td>
			<td>
				<input id="aaa" type="text" name="aaa" value="" class="validate[required]">
			</td>
		</tr>
		<tr>
			<td>名称2：</td>
			<td>
				<input id="aaa2" type="text" name="aaa2" value="">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="test1" onclick="validation()">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="添加行" onclick="add()">
			</td>
		</tr>
	</table>
	</form>
  </body>
</html>