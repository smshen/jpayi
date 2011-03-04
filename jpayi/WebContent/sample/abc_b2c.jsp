<%@page import="com.hitrust.trustpay.client.*,com.hitrust.trustpay.client.b2c.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

//1、取得支付请求所需要的信息
String tOrderNo         = "T" + System.currentTimeMillis();//request.getParameter("OrderNo"        );
String tOrderDesc       = "only Testing";//request.getParameter("OrderDesc"      );
String tOrderDate       = cn.zeli.util.DateUtil.getDateFormat(new java.util.Date(),"yyyy/MM/dd");//request.getParameter("OrderDate"      );
String tOrderTime       = cn.zeli.util.DateUtil.getDateFormat(new java.util.Date(),"HH:mm:ss");//request.getParameter("OrderTime"      );
String tOrderAmountStr  = "0.01";//request.getParameter("OrderAmount"    );
String tOrderURL        = "http://localhost/jpayi";//request.getParameter("OrderURL"       );
String tProductType     = "1";//request.getParameter("ProductType"    );
String tPaymentType     = "1";//request.getParameter("PaymentType"    );
String tNotifyType      = "0";//request.getParameter("NotifyType"     );
String tResultNotifyURL = path + "/pay/notify/abc/b2c/server";//request.getParameter("ResultNotifyURL");
String tMerchantRemarks = "test";//request.getParameter("MerchantRemarks");
double  tOrderAmount    = java.lang.Double.parseDouble(tOrderAmountStr);
String tPaymentLinkType = "1";//request.getParameter("PaymentLinkType");

//2、生成订单对象
Order tOrder = new Order();
tOrder.setOrderNo    (tOrderNo    ); //设定订单编号 （必要信息）
tOrder.setOrderDesc  (tOrderDesc  ); //设定订单说明
tOrder.setOrderDate  (tOrderDate  ); //设定订单日期 （必要信息 - YYYY/MM/DD）
tOrder.setOrderTime  (tOrderTime  ); //设定订单时间 （必要信息 - HH:MM:SS）
tOrder.setOrderAmount(tOrderAmount); //设定订单金额 （必要信息）
tOrder.setOrderURL   (tOrderURL   ); //设定订单网址

//3、生成定单订单对象，并将订单明细加入定单中（可选信息）
//tOrder.addOrderItem(new OrderItem("IP000001", "中国移动IP卡", 100.00f, 1));
//tOrder.addOrderItem(new OrderItem("IP000002", "网通IP卡"    ,  90.00f, 2));

//4、生成支付请求对象
PaymentRequest tPaymentRequest = new PaymentRequest();
tPaymentRequest.setOrder      (tOrder      ); //设定支付请求的订单 （必要信息）
tPaymentRequest.setProductType(tProductType); //设定商品种类 （必要信息）
                                              //PaymentRequest.PRD_TYPE_ONE：非实体商品，如服务、IP卡、下载MP3、...
                                              //PaymentRequest.PRD_TYPE_TWO：实体商品
tPaymentRequest.setPaymentType(tPaymentType); //设定支付类型
                                              //PaymentRequest.PAY_TYPE_ABC：农行卡支付
                                              //PaymentRequest.PAY_TYPE_INT：国际卡支付
tPaymentRequest.setNotifyType(tNotifyType);	  //设定商户通知方式
                                              //0：URL页面通知
                                              //1：服务器通知
tPaymentRequest.setResultNotifyURL(tResultNotifyURL); //设定支付结果回传网址 （必要信息）
tPaymentRequest.setMerchantRemarks(tMerchantRemarks); //设定商户备注信息
tPaymentRequest.setPaymentLinkType(tPaymentLinkType);//设定支付接入方式

//5、传送支付请求并取得支付网址
//TrxResponse tTrxResponse = tPaymentRequest.postRequest();
TrxResponse tTrxResponse = tPaymentRequest.extendPostRequest(1);
if (tTrxResponse.isSuccess()) {
   //6、支付请求提交成功，将客户端导向支付页面
   System.out.println("PaymentURL-->"+tTrxResponse.getValue("PaymentURL"));
   response.sendRedirect(tTrxResponse.getValue("PaymentURL"));
} else {
	System.out.println(tTrxResponse.getErrorMessage());
	   //7、支付请求提交失败，商户自定后续动作
	response.sendError(HttpServletResponse.SC_FORBIDDEN, "错误支付对象");
}
%>