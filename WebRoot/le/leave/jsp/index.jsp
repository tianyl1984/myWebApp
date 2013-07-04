<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>请假申请</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
	
		function deploy(){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/le/leave!deploy.action?',
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
			  	url: '${path}/le/leave!ajaxGetDeploy.action?',
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
			  	url : '${path}/le/leave!ajaxDeleteDeploy.action?id=' + id,
			  	success : function(data){
			  		alert(data);
			  		queryDeploy();
			  	}
			 });
		}
		
		function startOneInstance(){
			$.dialog.load("${path}/le/leave!input.action",{
				title : '请假申请',
				width : 700,
				height : 400,
				id : 'leaveInput',
				ok: function(){
					var data = $("#appFormId").serialize();
					$.ajax({
					 	type : "POST",
					 	data : data,
					 	dataType : "text",
					  	url : '${path}/le/leave!ajaxSaveApplicant.action?',
					  	success: function(data){
					  		alert(data);
					  		art.dialog.list['leaveInput'].close();
					  		searchMyApp();
					  	}
					 });
					return false;
			    }
			});
		}
		
		function searchMyApp(){
			$("#myApp").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/le/leave!ajaxGetMyApp.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td></tr>";
			  			str = str.replace("{0}",this.id);
			  			str = str.replace("{1}",this.reason);
			  			str = str.replace("{2}", this.applicantDate);
			  			str = str.replace("{3}", this.taskName);
			  			str = str.replace("{4}", this.assignee);
			  			$("#myApp").append(str);
			  		})
			  	}
			 });
		}
		
		function searchMyTask(){
			$("#myTask").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/le/leave!ajaxGetMyTask.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}{9}</td></tr>";
			  			str = str.replace("{0}",this.id);
			  			str = str.replace("{1}",this.reason);
			  			str = str.replace("{2}", this.applicantDate);
			  			str = str.replace("{3}", this.applicantUser);
			  			str = str.replace("{4}", this.deptUser);
			  			str = str.replace("{5}", this.deptSuggestion);
			  			str = str.replace("{6}", this.status);
			  			str = str.replace("{7}", this.taskName);
			  			if(this.statusFlag == "true"){
				  			str = str.replace("{8}", "<input type='button' value='认领任务' onclick='claim(\"" + this.taskId + "\")' >");
			  			}else{
				  			str = str.replace("{8}", "<input type='button' value='执行任务' onclick='" + this.jsFunction + "(\"" + this.id + "\")' >");
			  			}
			  			str = str.replace("{9}", "<input type='button' value='查看进度' onclick='showProcess(\"" + this.taskId + "\")' >");
			  			$("#myTask").append(str);
			  		})
			  	}
			 });
		}
		
		function showProcess(taskId){
			var imageUrl = "${path}/le/leave!loadPicture.action?taskId=" + taskId;
			var titles = "";
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/le/leave!traceProcess.action?taskId=' + taskId,
			  	success: function(info){
			  		var html = "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
		  	        "<div style='position:absolute; border:2px solid red;left:" +
		  	        (info.x - 2) +
		  	        "px;top:" +
		  	        (info.y - 2) +
		  	        "px;width:" +
		  	        (info.width) +
		  	        "px;height:" +
		  	        (info.height) +
		  	        "px;' title='" + titles +"'></div>"
		  	      	art.dialog({
		  	        	title: '当前任务进度',
		  	        	content: html,
		  	        	width : 800,
		  	        	height : 400
		  	    	}).show();
			  	}
			 });
		}
		
		function searchAllTask(){
			$("#allTask").empty();
			$.ajax({
			 	type: "POST",
			 	dataType : "json",
			  	url: '${path}/le/leave!ajaxGetAllTask.action?',
			  	success: function(data){
			  		$.each(data,function(){
			  			var str = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}</td></tr>";
			  			str = str.replace("{0}",this.id);
			  			str = str.replace("{1}",this.reason);
			  			str = str.replace("{2}", this.applicantDate);
			  			str = str.replace("{3}", this.applicantUser);
			  			str = str.replace("{4}", this.deptUser);
			  			str = str.replace("{5}", this.deptSuggestion);
			  			str = str.replace("{6}", this.status);
			  			str = str.replace("{7}", this.assignee);
			  			str = str.replace("{8}", this.taskName);
			  			$("#allTask").append(str);
			  		})
			  	}
			});
		}
		//认领任务
		function claim(taskId){
			$.ajax({
			 	type: "POST",
			 	dataType : "text",
			  	url: '${path}/le/leave!ajaxClaim.action?taskId=' + taskId,
			  	success: function(data){
			  		alert(data);
			  		searchMyTask()
			  	}
			 });
		}
		//部门经理审核
		function deptComplate(id){
			$.dialog.load("${path}/le/leave!inputDept.action?id=" + id,{
				title : '经理审核',
				width : 700,
				height : 400,
				id : 'deptInput',
				ok: function(){
					var data = $("#deptFormId").serialize();
					$.ajax({
					 	type : "POST",
					 	data : data,
					 	dataType : "text",
					  	url : '${path}/le/leave!ajaxDeptComplate.action?',
					  	success: function(data){
					  		alert(data);
					  		art.dialog.list['deptInput'].close();
					  		searchMyTask();
					  	}
					 });
					return false;
			    }
			});
		}
		//人事审核
		function leaderComplate(id){
			$.dialog.load("${path}/le/leave!inputLeader.action?id=" + id,{
				title : '人事主管审核',
				width : 700,
				height : 400,
				id : 'leaderInput',
				ok: function(){
					var data = $("#leaderFormId").serialize();
					$.ajax({
					 	type : "POST",
					 	data : data,
					 	dataType : "text",
					  	url : '${path}/le/leave!ajaxLeaderComplate.action?',
					  	success: function(data){
					  		alert(data);
					  		art.dialog.list['leaderInput'].close();
					  		searchMyTask();
					  	}
					 });
					return false;
			    }
			});
		}
		//销假
		function userDelLeave(id){
			$.ajax({
			 	type : "POST",
			 	dataType : "text",
			  	url : '${path}/le/leave!ajaxUserDelLeave.action?id=' + id,
			  	success: function(data){
			  		alert(data);
			  		searchMyTask();
			  	}
			 });
		}
		//重新申请
		function userReAppLeave(id){
			$.dialog.load("${path}/le/leave!inputReApp.action?id=" + id,{
				title : '重新申请',
				width : 700,
				height : 400,
				id : 'inputReApp',
				okVal : '确定申请',
				cancelVal : '取消申请',
				ok: function(){
					$("#resultId").val("0");
					var data = $("#inputReAppFormId").serialize();
					$.ajax({
					 	type : "POST",
					 	data : data,
					 	dataType : "text",
					  	url : '${path}/le/leave!ajaxSaveReApp.action?',
					  	success: function(data){
					  		alert(data);
					  		art.dialog.list['inputReApp'].close();
					  		searchMyTask();
					  	}
					 });
					return false;
			    },
			    cancel : function(){
			    	$("#resultId").val("1");
			    	var data = $("#inputReAppFormId").serialize();
					$.ajax({
					 	type : "POST",
					 	data : data,
					 	dataType : "text",
					  	url : '${path}/le/leave!ajaxSaveReApp.action?',
					  	success: function(data){
					  		alert(data);
					  		art.dialog.list['inputReApp'].close();
					  		searchMyTask();
					  	}
					 });
					return false;
			    }
			});
		}
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
  	<input type="button" value="请假申请" onclick="startOneInstance()"><input type="button" value="查询我的申请" onclick="searchMyApp()"><br>
  	<table>
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>原因</th>
  				<th>申请时间</th>
  				<th>当前节点</th>
  				<th>任务认领人</th>
  			</tr>
  		</thead>
  		<tbody id="myApp"></tbody>
  	</table>
  	<hr>
  	<input type="button" value="查询我的任务" onclick="searchMyTask()"><br>
  	<table>
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>原因</th>
  				<th>申请时间</th>
  				<th>申请人</th>
  				<th>部门经理</th>
  				<th>经理意见</th>
  				<th>状态</th>
  				<th>当前节点</th>
  				<th>操作</th>
  			</tr>
  		</thead>
  		<tbody id="myTask"></tbody>
  	</table>
  	<hr>
  	<input type="button" value="查询所有申请" onclick="searchAllTask()"><br>
  	<table>
  		<thead>
  			<tr>
  				<th>id</th>
  				<th>原因</th>
  				<th>申请时间</th>
  				<th>申请人</th>
  				<th>部门经理</th>
  				<th>经理意见</th>
  				<th>状态</th>
  				<th>认领人</th>
  				<th>当前节点</th>
  			</tr>
  		</thead>
  		<tbody id="allTask"></tbody>
  	</table>
  	<hr>
  	
  </body>
</html>