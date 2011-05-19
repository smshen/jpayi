<%@page import="cn.zeli.pay.icbc.*"%>
<%@page import="cn.zeli.pay.ConfigObjectHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();


IcbcB2cV1_0_0_11PayObject icbcB2c = new IcbcB2cV1_0_0_11PayObject();
IcbcB2cConfigObject ico = (IcbcB2cConfigObject) ConfigObjectHelper.getConfig(IcbcB2cConfigObject.class);

// IcbcB2cV1_0_0_11TranData
// 
// 
// 
		
IcbcB2cV1_0_0_11TranData td = new IcbcB2cV1_0_0_11TranData();

IcbcB2cV1_0_0_11TranDataOrderInfo oi = new IcbcB2cV1_0_0_11TranDataOrderInfo();
List<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo> ls = new ArrayList<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo>();

IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo soi = new IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo();
soi.setOrderid("T" + System.currentTimeMillis());
soi.setAmount("1");
soi.setInstallmentTimes("1");
soi.setMerAcct(ico.getMerAcct());
soi.setGoodsName("test goodName");// goodName

ls.add(soi);
oi.setSubOrderInfoList(ls);
oi.setMerID(ico.getMerID());
td.setOrderInfo(oi);

IcbcB2cV1_0_0_11TranDataCustom c = new IcbcB2cV1_0_0_11TranDataCustom();
td.setCustom(c);
c.setVerifyJoinFlag("0");

IcbcB2cV1_0_0_11TranDataMessage m = new IcbcB2cV1_0_0_11TranDataMessage();
m.setMerURL(path + "/pay/notify/icbc/b2cV1_0_0_11/server");
m.setMerCustomIp("221.2.219.114");
td.setMessage(m);

icbcB2c.setTranDataObject(td);
icbcB2c.setMerSignMsg(icbcB2c.sign());

cn.zeli.pay.ValidationMsg vm = icbcB2c.validation();
System.out.println(vm);


System.out.println(icbcB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_PAY_OBJECT, icbcB2c);
request.setAttribute(cn.zeli.pay.AutoPayServlet.ATTR_NAME_FROM_ACTION, "https://B2C.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
request.getRequestDispatcher("/AutoPayServlet").forward(request, response);

%>