<%@page import="cn.zeli.pay.ccb.CcbB2bPayObject"%>
<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@page import="cn.zeli.pay.ccb.CcbB2bConfigObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

CcbB2bPayObject ccbB2b = new CcbB2bPayObject();
CcbB2bConfigObject ico = (CcbB2bConfigObject) ConfigObjectHelper.getConfig(CcbB2bConfigObject.class);

ccbB2b.setMERCHANTID(ico.getMERCHANTID());
ccbB2b.setPOSID(ico.getPOSID());
ccbB2b.setBRANCHID(ico.getBRANCHID());
ccbB2b.setORDERID("T" + System.currentTimeMillis());
ccbB2b.setPAYMENT("0.01");
ccbB2b.setREMARK1("B2B");
ccbB2b.setREMARK2("test");

// /pay/notify/ccb/b2b/page

ccbB2b.setMAC(ccbB2b.sign());

cn.zeli.pay.ValidationMsg vm = ccbB2b.validation();
System.out.println(vm);


System.out.println(ccbB2b);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, ccbB2b);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://ibsbjstar.ccb.com.cn/app/ccbMain");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>