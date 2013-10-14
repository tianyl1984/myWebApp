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
// 			$('#form1').reloadValidationEngine();
			$("#result").html("");
			if($('#form1').validationEngine("validate")){
				$("#result").html("通过");
			}else{
				$("#result").html("未通过");
			}
		}
		
		function add(){
			var id = "id" + new Date().getTime();
			var html = "<tr id='tr" + id + "'><td>名称add：</td><td><input id='" + id + "' type='text' name='adaa" + id + "' value='' class='validate[required]'></td><td><input type='button' value='删除' onclick=del('tr" + id + "')></td></tr>";
			$("#tab").prepend(html);
			$('#form1').validationEngine();
		}
		
		function del(trId){
			$("#" + trId).remove();
			$('#form1').validationEngine('updatePromptsPosition');
		}
		
		$(document).ready(function(){
			$('#form1').validationEngine();
			$('#form2').validationEngine();
// 			$('#form1').validationEngine();
		})
	</script>
  </head>
  <body>
  	<div id="result"></div>
  	<form id="form1">
	<table id="tab">
		<tr id="aaatr">
			<td>必填名称：</td>
			<td>
				<input id="aaa" type="text" name="aaa" value="" class="validate[required]" errorMsg="* 必须要填写">
<!-- 				<input type="text" class="validate[optional,ajax[ajaxItemRepeatCall]] inputStyle w155" value="" maxlength="50" validateurl="/dc/cm/car!ajaxCheckCarNum.action?id=" name="model.carNum" id="20131010164201478153459458871903_carNum"> -->
			</td>
			<td>
				<input type="button" value="删除" onclick="del('aaatr')">
			</td>
		</tr>
		<tr id="aaatr2">
			<td>必填数字：</td>
			<td>
				<input id="aaa3" type="text" name="aaa3" value="" class="validate[required,custom[float1],max[50]]" errorMsg="* 必须要填写数字">
			</td>
			<td>
				<input type="button" value="删除" onclick="del('aaatr2')">
			</td>
		</tr>
		<tr id="aaa2tr">
			<td>非必填名称：</td>
			<td>
				<input id="aaa2" type="text" name="aaa2" value="">
			</td>
			<td>
				<input type="button" value="删除" onclick="del('aaa2tr')">
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<input type="radio" value="0" name="sex" id="r1" class="validate[required]"><label for="r1">男</label>
				<input type="radio" value="1" name="sex" id="r2" class="validate[required]"><label for="r2">女</label>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<input type="button" value="验证" onclick="validation()">
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<input type="button" value="添加行" onclick="add()">
			</td>
		</tr>
	</table>
	</form>
  	<form id="form2">
	<table id="tab2">
		<tr>
			<td>ajax校验：</td>
			<td>
				<input type="text" class="validate[optional,ajax[ajaxItemRepeatCall]] inputStyle w155" value="" maxlength="50" validateurl="${path }/appServlet" name="carNum" id="carNum">
			</td>
		</tr>
		
		<tr>
			<td colspan="3">
				<input type="button" value="验证" onclick="validation()">
			</td>
		</tr>
	</table>
	</form>
  </body>
</html>