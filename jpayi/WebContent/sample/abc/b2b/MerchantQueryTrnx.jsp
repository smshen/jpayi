<%
/*
 * @(#)MerchantQueryTrnx.jsp	V1.0.3	2004/04/28
 *
 * Project: BJP03004
 *
 * Description:
 *    商户交易查询请求范例程序。
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    HiTRUST       2004/04/28  V1.0 Release
 *
 * Copyright Notice:
 *   Copyright (c) 2001-2004 Beijing HiTRUST Technology Co., Ltd.
 *   1808 Room, Science & Technology Building, No. 9 South Street,
 *   Zhong Guan Cun, Haidian District, Beijing ,100081, China
 *   All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   HiTRUST.COM, Inc. ("Confidential Information"). You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with HiTRUST.
 */
%>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import = "com.hitrust.b2b.trustpay.client.b2b.*" %>
<%@ page import = "com.hitrust.b2b.trustpay.client.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-交易查询请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>交易查询请求<br>
<%
//1、取得交易查询请求所需要的信息
String tMerchantTrnxNo           = request.getParameter("MerchantTrnxNo"       );
String tMerchantRemarks          = request.getParameter("MerchantRemarks"      );

//2、生成交易查询请求对象
QueryTrnxRequest tQueryTrnxRequest = new QueryTrnxRequest();
tQueryTrnxRequest.setMerchantTrnxNo(tMerchantTrnxNo);               //设定商户交易编号    （必要信息）
tQueryTrnxRequest.setMerchantRemarks(tMerchantRemarks);             //设定商户备注信息
 
//3、传送交易查询请求并取得支付网址
TrxResponse tTrxResponse = tQueryTrnxRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//4、交易查询请求提交成功
  out.println("TrnxType       = [" + tTrxResponse.getValue("TrnxType"  )     + "]<br>");
  out.println("TrnxAMT        = [" + tTrxResponse.getValue("TrnxAMT"  )      + "]<br>");
  out.println("MerchantID     = [" + tTrxResponse.getValue("MerchantID"  )   + "]<br>");
  out.println("MerchantTrnxNo = [" + tTrxResponse.getValue("MerchantTrnxNo") + "]<br>");
  out.println("ReturnCode     = [" + tTrxResponse.getValue("ReturnCode")     + "]<br>");
}
else {
//5、交易查询请求提交失败，商户自定后续动作
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>