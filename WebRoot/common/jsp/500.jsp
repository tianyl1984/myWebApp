<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
	<head>
		<title>页面访问出错</title>
	</head>
	<%
	Throwable ex = null;
	if(exception!=null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception)request.getAttribute("javax.servlet.error.exception");
	LogFactory.getLog("").error("",ex);
	%>
	<body>
		<div id="content">
				<div>
					<h3>
						页面访问时发生错误:
						<%
							if(ex!=null)
								out.println(ex.getMessage());
						%>
					</h3>
				</div>
				<div >
				<div id="detail_error_msg">
					<% if (ex != null) { %>
					<pre>
						<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
					</pre>
					<% } %>
				</div>
			</div>
	</body>
</html>