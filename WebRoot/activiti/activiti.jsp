<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>index.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
    <script type="text/javascript" src="${path }/activiti/workflow.js"></script>
	<script type="text/javascript">
	
		function deploy(){
			$.ajax({
			 	type: "POST",
			  	url: '${path}/fr/financialReport!deploy.action?',
			  	dataType : "text",
			  	success: function(data){
			  		alert(data);
			  		searchDeploy();
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
			  			str1 += "<td>" + this.priority + "</td><td><input type='button' value='认领' onclick=claim(\'" + this.id + "\')></td></tr>";
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
			  			str1 += "<td>" + this.priority + "</td>";
			  			str1 += "<td><input type='button' value='认领' onclick=claim(\'" + this.id + "\')></td></tr>";
			  			$("#all").append(str1);
			  		})
			  	}
			 });
		}
		
		//认领任务
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
			  			var str1 = "<tr><td><input type='button' value='查看流程图' onclick=graphTrace('" + this.processInstanceId + "')>";
			  			str1 += "<input type='button' value='查看流程图2' onclick=graphTrace2('" + this.processInstanceId + "')>";
			  			str1 += "<input type='button' value='完成' onclick=complate('" + this.id + "')>";
			  			str1 += "</td>";
			  			str1 += "<td>" + this.id + "</td><td>"+ this.name +"</td><td>" + this.owner + "</td><td>" + this.assignee + "</td>";
			  			str1 += "<td>" + this.parentTaskId+ "</td><td>" + this.processDefinitionId + "</td>";
			  			str1 += "<td>" + this.processInstanceId + "</td><td>" + this.taskDefinitionKey + "</td>";
			  			str1 += "<td>" + this.priority + "</td></tr>";
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
		
		function graphTrace2(pid){
			
			art.dialog({
			    title: '流程图',
			    content: "<img src='${path}/fr/financialReport!graphTrace2.action?pid=" + pid + "&_=" + new Date() + "'>",
			    lock : true,
			    height : 500,
			    width: 800,
			    ok: function(){
			        
			    },
			    cancel:function(){
			    	
			    }
			});
			
		}
		
		function showDiagram(){
			
			art.dialog({
			    title: '流程图',
			    content: "<img src='${path}/fr/financialReport!showDiagram.action?_=" + new Date() + "'>",
			    lock : true,
			    height : 500,
			    width: 800,
			    ok: function(){
			        
			    },
			    cancel:function(){
			    	
			    }
			});
			
		}
		
		function searchDeploy(){
			$("#deploy").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/fr/financialReport!searchDeploy.action',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str1 = "<tr><td><input type='button' value='查看流程图' onclick=showDiagramByDeplId('" + this.id + "')>";
			  			str1 += "<input type='button' value='删除' onclick=deleDeploy('" + this.id + "')></td>";
			  			str1 += "<td>" + this.key + "</td>";
			  			str1 += "<td>" + this.deplTime + "</td>";
			  			str1 += "<td>" + this.diagramResourceName + "</td>";
			  			str1 += "<td>" + this.version + "</td>";
			  			str1 += "</tr>";
			  			$("#deploy").append(str1);
			  		})
			  	}
			 });
		}
		
		function deleDeploy(deplId){
			$.ajax({
			 	type: "POST",
			 	dataType : "text",
			  	url: '${path}/fr/financialReport!deleDeploy.action?deplId=' + deplId,
			  	success: function(data){
			  		alert(data);
			  		searchDeploy();
			  	}
			 });
		}
		
		function showDiagramByDeplId(deplId){
			
			art.dialog({
			    title: '流程图',
			    content: "<img src='${path}/fr/financialReport!showDiagramByDeplId.action?deplId=" + deplId + "&_=" + new Date() + "'>",
			    lock : true,
			    height : 500,
			    width: 800,
			    ok: function(){
			        
			    },
			    cancel:function(){
			    	
			    }
			});
			
		}
		
	</script>
  </head>
  <body>
  	<input type="button" value="发布流程" onclick="deploy()">
  	<input type="button" value="查询流程" onclick="searchDeploy()"><br>
  	<hr>
	  	<table summary="所有流程">
	  		<thead>
	  			<tr>
	  				<th>操作</th>
	  				<th>key</th>
	  				<th>deplTime</th>
	  				<th>diagramResourceName</th>
	  				<th>version</th>
	  			</tr>
	  		</thead>
	  		<tbody id="deploy"></tbody>
	  	</table>
  	<hr>
  	<input type="button" value="启动一个任务" onclick="startOneInstance()"><br>
  	<input type="button" value="查询任务" onclick="searchTask('g2')"><br>
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
  		<tbody id="g2"></tbody>
  	</table>
  	<input type="button" value="查询张三认领的任务" onclick="searchTaskAssignee()"><br>
  	<hr>
  	<table>
  		<thead>
  			<tr>
  				<th>操作</th>
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
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="all"></tbody>
  	</table>
  </body>
</html>