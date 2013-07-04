<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>index.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#jsonmap").jqGrid({
				url:'${path}/um/user!ajaxGetData.action',
				datatype: "json",
				postData: {depId: 1},
				colNames:['姓名','ID', 'Email'],
				colModel:[
<%--					{name:'name',index:'usrName2', width:310,formatter:function(d,e){--%>
<%--						//alert(d+":" + e.rowId);--%>
<%--						for(var a in e){--%>
<%--							//alert(a + ":"+ e[a]);--%>
<%--						}--%>
<%--						return "<input type='button' value='test' onclick='alert(123)' />"--%>
<%--						}--%>
<%--					},--%>
					{name:'id',index:'usrName2', width:310,align:"center"},
					{name:'email',index:'loginName', width:300,formatter:
						function(cellvalue,options,rowObject){
							var str = "<a href='#' onclick=\"javascript:aaa('" + options.rowId + "')\" >" + $.jgrid.htmlEncode(cellvalue) + "</a>";
							return str;
						}
					},
					{name:'name',index:'id', width:200}
				],
				rownumbers : true,
				rowNum:10,
				rowList:[10,20,30],
				pager: '#pjmap',
				sortname: 'id',
				viewrecords: true,
				sortorder: "desc",
				autoencode : true,
				loadComplete : function(data){
					$.each($("#jsonmap").getDataIDs(),
							function(i) {
<%--								var be = "<input type='button' value='绑定角色' onclick=\"alert(1);\"  />";--%>
								var rowData = $("#jsonmap").getRowData(this);
<%--								alert(rowData.email);--%>
								var be = "<a href='#' onclick=\"javascript:aaa('" + rowData.email + "')\" >" + rowData.email + "</a>";
								//$("#jsonmap").jqGrid("setCell", this, {email : be});
							});
<%--					alert("loadComplete:" + data);--%>
				},
<%--				gridComplete : function(data){--%>
<%--					alert("gridComplete:" + data);--%>
<%--				},--%>
				jsonReader: {
					repeatitems : false,
					root : "gridModel",
					id: "0"
				},
				caption: "部门人员信息"
			}).navGrid('#pjmap',{edit:false,add:false,del:false});
		});
		
		function aaa(p1){
<%--			for(var a in p1){--%>
<%--				alert(a + ":"+p1[a]);--%>
<%--			}--%>
			alert(p1)
		}
		
		function m1(){
			$("#jsonmap").jqGrid("stripPref");
		}
	</script>
  </head>
  <body>
	<table id="jsonmap" class="scroll" cellpadding="0" cellspacing="0"></table>
	<div id="pjmap" class="scroll" style="text-align:center;"></div><br>
	<input type="button" value="测试" onclick="m1()">
  </body>
</html>