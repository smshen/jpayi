<%@page import="cn.zeli.pay.boc.*"%>
<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();


BocB2cPayObject bocB2c = new BocB2cPayObject();
BocB2cConfigObject bco = (BocB2cConfigObject) ConfigObjectHelper.getConfig(BocB2cConfigObject.class);

// TODO


request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, bocB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://ibsbjstar.ccb.com.cn/app/ccbMain");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>