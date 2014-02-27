<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    	<title>w2ui demo</title>
    	<script type="text/javascript" src="${path }/w2ui/jquery-1.10.1.js"></script>
    	<script type="text/javascript" src="${path }/w2ui/w2ui-1.3.1-grid.js"></script>
<%--     	<script type="text/javascript" src="${path }/w2ui/w2ui-1.3.1.js"></script> --%>
    	<link rel="stylesheet" href="${path }/w2ui/w2ui-1.3.1.css" />
    	<script type="text/javascript">
	    	$(function () {
	    		getData();
// 				testData();
	    	});
	    	
	    	function testData(){
	    		resetDiv();
	    	    $('#grid').w2grid({
	    	        name: 'grid',
	    	        header: 'List of Names',
	    	        columnGroups:[
	    	        	{span:3,caption:"前3列"},              
	    	        	{span:1,caption:"最后一列"}
	    	        ],
	    	        columns: [
	    	            { field: 'fname', caption: 'First Name', size: '30%' },
	    	            { field: 'lname', caption: 'Last Name', size: '30%',resizable:true },
	    	            { field: 'email', caption: 'Email', size: '40%' },
	    	            { field: 'sdate', caption: 'Start Date', size: '120px' }
	    	        ],
	    	        records: [
	    	            { recid: 1, fname: "Peter", lname: "Jeremia", email: 'peter@mail.com', sdate: '2/1/2010' },
	    	            { recid: 2, fname: "Bruce", lname: "Wilkerson", email: 'bruce@mail.com', sdate: '6/1/2010' },
	    	            { recid: 3, fname: "John", lname: "McAlister", email: 'john@mail.com', sdate: '1/16/2010' },
	    	            { recid: 4, fname: "Ravi", lname: "Zacharies", email: 'ravi@mail.com', sdate: '3/13/2007' },
	    	            { recid: 5, fname: "William", lname: "Dembski", email: 'will@mail.com', sdate: '9/30/2011' },
	    	            { recid: 6, fname: "David", lname: "Peterson", email: 'david@mail.com', sdate: '4/5/2010' }
	    	        ]
	    	    });
	    	}
	    	
	    	function getData(){
	    		resetDiv();
	    		$.ajax({
	    			type:"POST",
	    			url:"${path}/web/pojo!ajaxGetW2UIDemoData.action",
	    			data:"a=b",
	    			dataType:"json",
	    			success:function(data){
	    	    	    $('#grid').w2grid({
	    	    	        name: 'grid',
	    	    	        header: 'List of Names',
	    	    	        columnGroups:[
	    	    		    	        	{span:3,caption:"前3列"},              
	    	    		    	        	{span:1,caption:"最后一列"}
	    	    		    	        ],
	    	    	        columns: [
	    	    	            { field: 'fname', caption: 'First Name', size: '400px' },
	    	    	            { field: 'lname', caption: 'Last Name', size: '200px',resizable:true },
	    	    	            { field: 'email', caption: 'Email',size: '100px' },
	    	    	            { field: 'sdate', caption: 'Start Date', size: '200px' }
	    	    	        ],
	    	    	        records: data
	    	    	    });
	    			}
	    		});
	    	}
	    	
	    	function popup(){
	    		
	    	}
	    	
	    	function resetDiv(){
	    		$("#grid").w2destroy();
	    	}
		</script>
	</head>
	<body>
		<input type="button" value="获取数据" onclick="getData()">
		<input type="button" value="测试" onclick="testData()">
		<input type="button" value="弹出窗口" onclick="popup()">
		<div id="grid" style="width: 100%; height: 250px;"></div>
		<div>aaaaaaaaaaa</div>
	</body>
</html>