<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>struts2测试</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
	<script type="text/javascript">

	</script>
  </head>
  <body>
	<form action="${path }/um/user2!testStudents.action">
	<input type="checkbox" name="students[0].name" value="name11"><br>
	<input type="checkbox" name="students[0].name" value="name12"><br>
	<input type="checkbox" name="students[1].name" value="name21"><br>
	<input type="checkbox" name="students[1].name" value="name22"><br>
	<input type="submit" value="提交"><br>
	</form>
  </body>
</html>