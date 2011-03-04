<%
/*
 * @(#)MerchantQueryTrnxBatch.jsp	V1.0.3	2004/04/28
 *
 * Project: BJP03004
 *
 * Description:
 *    交易记录批量查询请求范例程序。
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
<%@ page import = "java.util.ArrayList" %>

<%@ page import = "com.hitrust.b2b.trustpay.client.*" %>
<%@ page import = "java.util.Vector" %>
<% 
response.setHeader("Cache-Control", "no-cache"); 
%>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-交易记录批量查询请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>交易记录批量查询请求<br>
<%
//1、取得交易批量查询请求所需要的信息


String tTrnxStatus = request.getParameter("TrnxStatus");
String tStartAccDate = request.getParameter("StDate");
String tEndAccDate = request.getParameter("EndDate");

//2、生成交易批量查询请求对象
QueryTrnxBatchRequest tQueryTrnxBatchRequest = new QueryTrnxBatchRequest();

tQueryTrnxBatchRequest.setTrnxStatus(tTrnxStatus);               //交易状态   
tQueryTrnxBatchRequest.setStartAccDate(tStartAccDate);          //起始查询日期YYYYMMDD （必要信息）
tQueryTrnxBatchRequest.setEndAccDate(tEndAccDate);             //终止查询日期YYYYMMDD （必要信息）

 
//3、传送交易批量查询请求并取得支付网址
TrxResponse tTrxResponse = tQueryTrnxBatchRequest.postRequest();
if (tTrxResponse.isSuccess()) {

//4、交易查询请求提交成功
	XMLDocument tXMLDocument = tTrxResponse.getResponseMessage();
	ArrayList tResults = tXMLDocument.getDocuments("Row");
	if (tResults.size()==0)
	out.print("没有交易记录<br>");
	else{
		out.print("订单号   |      交易时间      |   付款方账户名   |   付款方帐号   |   交易金额   |   交易状态<br>");
		for(int i=0;i<tResults.size();i++){
			String tRows = tResults.get(i).toString();
			XMLDocument tChild = new XMLDocument(tRows);
			out.println( tChild.getValueNoNull("MerchantTrnxNo"  )     + "|");
		  	out.println( tChild.getValueNoNull("TrnxTime"  )      + "|");
		  	out.println( tChild.getValueNoNull("PayAccountName"  )   + "|");
		  	out.println( tChild.getValueNoNull("PayAccount") + "|");
		  	out.println( tChild.getValueNoNull("TrnxAmount")     + "|");
			out.println( tChild.getValueNoNull("TrnxStatus")     + "<br>");
		}
	}
}  
else {
//5、交易批量查询请求提交失败，商户自定后续动作
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>