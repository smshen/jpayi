<%@page import="cn.zeli.pay.icbc.IcbcB2cPayObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

IcbcB2cPayObject icbcB2c = new IcbcB2cPayObject();
icbcB2c.setAmount("23342");
icbcB2c.setMerAcct("aaaa");
icbcB2c.setMerID("sdfsf");
icbcB2c.setNotifyType("02");
icbcB2c.setOrderid("ORDIERI");
icbcB2c.setVerifyJoinFlag("1");
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, icbcB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://www.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>