<%@page import="cn.zeli.pay.boc.*"%>
<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();


BocB2cPayObject bocB2c = new BocB2cPayObject();
BocB2cConfigObject bco = (BocB2cConfigObject) ConfigObjectHelper.getConfig(BocB2cConfigObject.class);

ccbB2c.setMERCHANTID(ico.getMERCHANTID());
ccbB2c.setPOSID(ico.getPOSID());
ccbB2c.setBRANCHID(ico.getBRANCHID());
ccbB2c.setORDERID("T" + System.currentTimeMillis());
ccbB2c.setPAYMENT("0.01");
ccbB2c.setREMARK1("B2C");
ccbB2c.setREMARK2("test");

bocB2c.setOrderUrl(path + "/pay/notify/boc/b2c/server");

// /pay/notify/ccb/b2c/page

ccbB2c.setMAC(ccbB2c.sign());

cn.zeli.pay.ValidationMsg vm = ccbB2c.validation();
System.out.println(vm);


System.out.println(ccbB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, ccbB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://ibsbjstar.ccb.com.cn/app/ccbMain");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>