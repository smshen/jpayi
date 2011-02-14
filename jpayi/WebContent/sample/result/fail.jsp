<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付失败</title>
</head>
<body>
<h1><font color="red">失败</font></h1>
<%
String type = request.getParameter("type");
if (null != type && type.equals("verify")) {
	out.println("验证失败");
} else {
	out.println("支付失败");
}
%>
</body>
</html>