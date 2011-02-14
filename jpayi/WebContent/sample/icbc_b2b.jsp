<%@page import="cn.zeli.pay.icbc.IcbcB2bPayObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
IcbcB2bPayObject icbcB2b = new IcbcB2bPayObject();
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, icbcB2b);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://www.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>