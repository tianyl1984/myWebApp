<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>动态表单请假申请</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		
		function deploy(){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/le/dynamicLeave!deploy.action?',
			  	success : function(data){
			  		alert(data);
			  		queryDeploy();
			  	}
			 });
		}
		
		function queryDeploy(){
			$("#deployList").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/le/dynamicLeave!ajaxGetDeploy.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td></tr>";
			  			str = str.replace("{0}",this.id);
			  			str = str.replace("{1}",this.name);
			  			str = str.replace("{2}", this.time);
			  			str = str.replace("{3}", "<input type='button' value='删除' onclick='deleteDeploy(\"" + this.id + "\")' >");
			  			$("#deployList").append(str);
			  		})
			  	}
			 });
		}
		
		function deleteDeploy(id){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/le/dynamicLeave!ajaxDeleteDeploy.action?id=' + id,
			  	success : function(data){
			  		alert(data);
			  		queryDeploy();
			  	}
			 });
		}
		
		
		function startOneInstance(){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/le/dynamicLeave!getLastProcessDefinitionId.action',
			  	success : function(data){
			  		if(data == ""){
			  			alert("请先发布流程")
			  		}else{
			  			var processDefinitionId = data;
						$.ajax({
						 	type : "POST",
						 	dataType : "json",
						  	url : '${path}/le/dynamicLeave!getStartFormData.action?processDefinitionId=' + processDefinitionId,
						  	success : function(data){
						  		var trs = [];
						  		$.each(data,function(){
						  			trs.push("<tr>" + createFieldHtml(this) + "</tr>");
						  		});
						  		$.dialog({
						  			title : "请假申请",
						  			id : "startDialog",
						  			content : "<form id='startForm'><input type='hidden' name='processDefinitionId' value='" + processDefinitionId + "'><table>" + trs.join("") + "</table></form>",
						  			ok : function(){
						  				var postData = $("#startForm").serialize();
						  				$.ajax({
						  				 	type : "POST",
						  				 	data : postData,
						  				 	dataType : "text",
						  				  	url : "${path}/le/dynamicLeave!submitStartForm.action",
						  				  	success : function(data){
						  				  		alert(data);
						  				  		art.dialog.list['startDialog'].close();
						  				  		searchAllTask();
						  				  	}
						  				 });
						  				return false;
						  			}
						  		}).show();
						  	}
						});
			  		}
			  	}
			});
		}
		
		function toAssignee(taskId){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/le/dynamicLeave!toAssignee.action?taskId=' + taskId,
			  	success : function(data){
			  		alert(data);
			  		searchAllTask();
			  	}
			});
		}
		
		function toComplate(taskId){
			$.ajax({
			 	type : "POST",
			 	dataType : "json",
			  	url : '${path}/le/dynamicLeave!getTaskFormData.action?taskId=' + taskId,
			  	success : function(data){
			  		var trs = [];
			  		$.each(data,function(){
			  			trs.push("<tr>" + createFieldHtml(this) + "</tr>");
			  		});
			  		$.dialog({
			  			title : "申请处理",
			  			id : "taskDialog",
			  			content : "<form id='taskForm'><input type='hidden' name='taskId' value='" + taskId + "'><table>" + trs.join("") + "</table></form>",
			  			ok : function(){
			  				var postData = $("#taskForm").serialize();
			  				$.ajax({
			  				 	type : "POST",
			  				 	data : postData,
			  				 	dataType : "text",
			  				  	url : "${path}/le/dynamicLeave!submitTaskForm.action",
			  				  	success : function(data){
			  				  		alert(data);
			  				  		art.dialog.list['taskDialog'].close();
			  				  		searchAllTask();
			  				  	}
			  				 });
			  				return false;
			  			}
			  		}).show();
			  	}
			});
		}
		
		function searchAllTask(){
			$("#allTask").html("");
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/le/dynamicLeave!ajaxGetAllTask.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td></tr>";
			  			var td7 = "";
			  			if(this.assignee == ""){
			  				td7 = "<input type='button' value='认领' onclick='toAssignee(\"{0}\")' />";
			  			}else{
			  				td7 = "<input type='button' value='执行' onclick='toComplate(\"{0}\")' />";
			  			}
			  			var result = replace(str,this.taskId,this.reason,this.startDate,this.suggestion,this.status,this.assignee,this.taskName,replace(td7,this.taskId));
			  			$("#allTask").append(result);
			  		});
			  	}
			});
		}
		
		function createFieldHtml(prop){
			return formFieldCreator[prop.typeName](prop);
		}
		
		var formFieldCreator = {
				string: function(prop) {
					var result = "<td width='120'>" + prop.name + "：</td>";
					if (prop.writable === true) {
						result += "<td><input autocomplete='off' type='text' id='" + prop.id + "' name='fp_" + prop.id + "' value='" + prop.value + "' />" + "</td>";
					} else {
						result += "<td>" + prop.value + "</td>";
					}
					return result;
				},
				date: function(prop) {
					var result = "<td width='120'>" + prop.name + "：</td>";
					if (prop.writable === true) {
						result += "<td><input autocomplete='off' type='date' id='" + prop.id + "' name='fp_" + prop.id + "' value='" + prop.value + "'/>" + "</td>";
					} else {
						result += "<td>" + prop.value + "</td>";
					}
					return result;
				},
				'enum': function(prop) {
					var result = "<td width='120'>" + prop.name + "：</td>";
					if (prop.writable === true) {
						result += "<td><select id='" + prop.id + "' name='fp_" + prop.id + "'>";
						if(prop.values != null){
							$.each(prop.values, function(k, v) {
								result += "<option value='" + k + "'>" + v + "</option>";
							});
						}
						result += "</select>" + "</td>";
					} else {
						result += "<td>" + prop.value + "</td>";
					}
					return result;
				},
				file: function(prop) {
					var result = "<td width='120'>" + prop.name + "：</td><td>";
					if (prop.writable === true) {
						result += "<input autocomplete='off' type='file' id='" + prop.id + "' name='fp_aaa" + prop.id + "'/>";
					} else {
						result += prop.value;
					}
					if(prop.value != ""){
						result += "<input autocomplete='off' size='5' type='text' name='fp_" + prop.id + "' value='" + prop.value + "' />";
					}
					result += "</td>"
					return result;
				}
		};
		
	</script>
  </head>
  <body>
  	<input type="button" value="主页" onclick="goUrl('${path}/')" ><br>
  	<input type="button" value="发布" onclick="deploy()"><input type="button" value="查询" onclick="queryDeploy()"><br>
  	<table>
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>name</th>
  				<th>time</th>
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="deployList"></tbody>
  	</table>
  	<hr>
  	<input type="button" value="请假申请" onclick="startOneInstance()"><input type="button" value="查询所有申请" onclick="searchAllTask()"><br>
  	<table>
  		<thead>
  			<tr>
  				<th>taskId</th>
  				<th>原因</th>
  				<th>申请时间</th>
  				<th>部门经理意见</th>
  				<th>状态</th>
  				<th>认领人</th>
  				<th>当前节点</th>
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="allTask"></tbody>
  	</table>
  </body>
</html>