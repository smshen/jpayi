<%@page import="cn.zeli.pay.icbc.IcbcB2bPayObject"%>
<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@page import="cn.zeli.pay.icbc.IcbcB2bConfigObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

IcbcB2bPayObject icbcB2b = new IcbcB2bPayObject();
IcbcB2bConfigObject ico = (IcbcB2bConfigObject) ConfigObjectHelper.getConfig(IcbcB2bConfigObject.class);

icbcB2b.setShop_code(ico.getShop_code());
icbcB2b.setMerchantURL(path + "/pay/notify/icbc/b2b/server");
icbcB2b.setContractNo("T" + System.currentTimeMillis());
icbcB2b.setContractAmt("100");
icbcB2b.setSendType("1");
icbcB2b.setShop_acc_num(ico.getShop_acc_num());
icbcB2b.setPayeeAcct(ico.getPayeeAcct());

icbcB2b.setMer_Icbc20_signstr(icbcB2b.sign());


cn.zeli.pay.ValidationMsg vm = icbcB2b.validation();
System.out.println(vm);
System.out.println(icbcB2b);

request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, icbcB2b);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://corporbank.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>