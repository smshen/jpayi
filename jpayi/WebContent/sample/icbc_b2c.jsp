<%@page import="cn.zeli.pay.icbc.IcbcB2cPayObject"%>
<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@page import="cn.zeli.pay.icbc.IcbcB2cConfigObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();


IcbcB2cPayObject icbcB2c = new IcbcB2cPayObject();
IcbcB2cConfigObject ico = (IcbcB2cConfigObject) ConfigObjectHelper.getConfig(IcbcB2cConfigObject.class);

icbcB2c.setAmount("1");
icbcB2c.setMerAcct(ico.getMerAcct());
icbcB2c.setMerID(ico.getMerID());
icbcB2c.setMerURL(path + "/pay/notify/icbc/b2c/server");
icbcB2c.setNotifyType("HS");
icbcB2c.setOrderid("T" + System.currentTimeMillis());
icbcB2c.setVerifyJoinFlag("0");
icbcB2c.setResultType("0");

icbcB2c.setMerSignMsg(icbcB2c.sign());

cn.zeli.pay.ValidationMsg vm = icbcB2c.validation();
System.out.println(vm);


System.out.println(icbcB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, icbcB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://B2C.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>