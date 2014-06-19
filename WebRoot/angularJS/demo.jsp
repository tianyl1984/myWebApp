<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html ng-app="helloApp">
	<head>
    <title>AngularJS Demo</title>
    <script type="text/javascript" src="${path }/angularJS/js/angular.js"></script>
	<script type="text/javascript">
		var helloApp = angular.module("helloApp", []);
	    helloApp.controller("HelloCtrl", function($scope) {
	    	$scope.name = "Calvin Hobbes";
	    });
	</script>
	</head>
	<body ng-controller="HelloCtrl">
		<input type="text" name="name" ng-model="name"/>
		<hr>
		hello,{{name}}。
	</body>
</html>