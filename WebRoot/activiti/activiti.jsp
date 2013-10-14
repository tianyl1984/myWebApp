<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>index.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		function deploy(){
			$.ajax({
			 	type: "POST",
			  	url: '${path}/fr/financialReport!deploy.action?',
			  	success: function(data){
			  		alert(data);
			  	}
			 });
		}
		function startOneInstance(){
			$.ajax({
			 	type: "POST",
			 	dataType : "text",
			  	url: '${path}/fr/financialReport!startOneInstance.action?',
			  	success: function(data){
			  		alert(data);
			  	}
			 });
		}
		function searchTask(str){
			$("#" + str).empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/fr/financialReport!getTasks.action?groupType=' + str,
			  	success: function(data){
			  		$.each(data,function(){
			  			var str1 = "<tr><td>" + this.id + "</td><td>"+ this.name +"</td><td>" + this.owner + "</td><td>" + this.assignee + "</td>";
			  			str1 += "<td>" + this.parentTaskId+ "</td><td>" + this.processDefinitionId + "</td>";
			  			str1 += "<td>" + this.processInstanceId + "</td><td>" + this.taskDefinitionKey + "</td>";
			  			str1 += "<td>" + this.priority + "</td><td><input type='button' value='认领' onclick='claim(" + this.id + ")'/></td></tr>";
			  			$("#" + str).append(str1);
			  		})
			  	}
			 });
		}
		function getAllTasks(){
			$("#all").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/fr/financialReport!getAllTasks.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str1 = "<tr><td>" + this.id + "</td><td>"+ this.name +"</td><td>" + this.owner + "</td><td>" + this.assignee + "</td>";
			  			str1 += "<td>" + this.parentTaskId+ "</td><td>" + this.processDefinitionId + "</td>";
			  			str1 += "<td>" + this.processInstanceId + "</td><td>" + this.taskDefinitionKey + "</td>";
			  			str1 += "<td>" + this.priority + "</td></tr>";
			  			$("#all").append(str1);
			  		})
			  	}
			 });
		}
		function claim(taskId){
			$.ajax({
			 	type: "POST",
			 	dataType : "text",
			  	url: '${path}/fr/financialReport!claim.action?taskId=' + taskId,
			  	success: function(data){
			  		alert(data)
			  	}
			 });
		}
		function searchTaskAssignee(){
			$("#assignee").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/fr/financialReport!searchTaskAssignee.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str1 = "<tr><td>" + this.id + "</td><td>"+ this.name +"</td><td>" + this.owner + "</td><td>" + this.assignee + "</td>";
			  			str1 += "<td>" + this.parentTaskId+ "</td><td>" + this.processDefinitionId + "</td>";
			  			str1 += "<td>" + this.processInstanceId + "</td><td>" + this.taskDefinitionKey + "</td>";
			  			str1 += "<td>" + this.priority + "</td><td><input type='button' value='完成' onclick='complate(" + this.id + ")'/></td></tr>";
			  			$("#assignee").append(str1);
			  		})
			  	}
			 });
		}
		function complate(taskId){
			$.ajax({
			 	type: "POST",
			 	dataType : "text",
			  	url: '${path}/fr/financialReport!complate.action?taskId=' + taskId,
			  	success: function(data){
			  		alert(data)
			  	}
			 });
		}
	</script>
  </head>
  <body>
  	<input type="button" value="发布" onclick="deploy()"><br>
  	<input type="button" value="启动一个任务" onclick="startOneInstance()"><br>
  	<input type="button" value="查询accountancy组的任务" onclick="searchTask('accountancy')"><br>
  	<hr>
  	<table summary="accountancy的任务">
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>Name</th>
  				<th>Owner</th>
  				<th>Assignee</th>
  				<th>ParentTaskId</th>
  				<th>ProcessDefinitionId</th>
  				<th>ProcessInstanceId</th>
  				<th>TaskDefinitionKey</th>
  				<th>Priority</th>
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="accountancy"></tbody>
  	</table>
  	<input type="button" value="查询张三认领的任务" onclick="searchTaskAssignee()"><br>
  	<hr>
  	<table>
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>Name</th>
  				<th>Owner</th>
  				<th>Assignee</th>
  				<th>ParentTaskId</th>
  				<th>ProcessDefinitionId</th>
  				<th>ProcessInstanceId</th>
  				<th>TaskDefinitionKey</th>
  				<th>Priority</th>
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="assignee"></tbody>
  	</table>
  	<input type="button" value="查询management组的任务" onclick="searchTask('management')"><br>
  	<hr>
  	<table summary="management的任务">
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>Name</th>
  				<th>Owner</th>
  				<th>Assignee</th>
  				<th>ParentTaskId</th>
  				<th>ProcessDefinitionId</th>
  				<th>ProcessInstanceId</th>
  				<th>TaskDefinitionKey</th>
  				<th>Priority</th>
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="management"></tbody>
  	</table>
  	<input type="button" value="查询所有的任务" onclick="getAllTasks()"><br>
  	<hr>
  	<table summary="所有的任务">
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>Name</th>
  				<th>Owner</th>
  				<th>Assignee</th>
  				<th>ParentTaskId</th>
  				<th>ProcessDefinitionId</th>
  				<th>ProcessInstanceId</th>
  				<th>TaskDefinitionKey</th>
  				<th>Priority</th>
  			</tr>
  		</thead>
  		<tbody id="all"></tbody>
  	</table>
  </body>
</html>