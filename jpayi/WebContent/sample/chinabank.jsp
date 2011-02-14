<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@page import="cn.zeli.pay.chinabank.ChinabankConfigObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
cn.zeli.pay.chinabank.ChinabankPayObject cb = new cn.zeli.pay.chinabank.ChinabankPayObject();

ChinabankConfigObject cco = (ChinabankConfigObject) ConfigObjectHelper.getConfig(ChinabankConfigObject.class);

cb.setKey(cco.getKey());
cb.setV_amount("0.01");
cb.setV_mid(cco.getV_mid());
cb.setV_oid("201101270952180001" + new java.util.Random().nextInt(10000));
cb.setV_url("http://localhost:8080/bank/pay/notify/chinabank/b2c/page");
cb.setV_md5info(cb.sign());// sign md5

//System.out.println(beartool.MD5.getMD5ofStr(cb.getV_amount() + "" + cb.getV_moneytype() + cb.getV_oid() + cb.getV_mid() + cb.getV_url() + cb.getKey()));

//request.setCharacterEncoding("gb2312");
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, cb);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://pay3.chinabank.com.cn/PayGate");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>