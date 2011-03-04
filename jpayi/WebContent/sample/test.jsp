<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%=cn.zeli.util.FileUtil.getClasspath()%>
<br/>
<% 
out.println(request.getContextPath());
out.println("<br/>");
out.println(request.getRequestURI());
out.println("<br/>");
out.println(request.getServletPath());
out.println("<br/>");
out.println(request.getServerName());
out.println("<br/>");
out.println(request.getContextPath());
out.println("<br/>");


out.println(request.getProtocol());
out.println("<br/>");
out.println(request.getPathInfo());
out.println("<br/>");

String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

out.println(path);
out.println("<br/>");
%>

</body>
</html>