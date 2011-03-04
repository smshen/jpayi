<%
/*
 * @(#)MerchantDownloadTrnx.jsp	V1.0.3	2004/04/28
 *
 * Project: BJP03004
 *
 * Description:
 *    商户下载交易记录请求范例程序。
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
<%@ page import = "java.util.ArrayList" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-下载交易记录请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>下载交易记录请求<br>
<%
//1、取得下载交易记录请求所需要的信息
String tMerchantTrnxDate   = request.getParameter("MerchantTrnxDate");
String tMerchantRemarks    = request.getParameter("MerchantRemarks" );

//2、生成下载交易记录请求对象
DownloadTrnxRequest tDownloadTrnxRequest = new DownloadTrnxRequest();
tDownloadTrnxRequest.setMerchantTrnxDate(tMerchantTrnxDate);           //设定商户交易编号    （必要信息）
tDownloadTrnxRequest.setMerchantRemarks(tMerchantRemarks);             //设定商户备注信息
 
//3、传送下载交易记录请求并取得支付网址
TrxResponse tTrxResponse = tDownloadTrnxRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//4、下载交易记录请求提交成功
        XMLDocument tDetailRecords = new XMLDocument(tTrxResponse.getValue("TrnxDetail"));
        ArrayList tRecords = tDetailRecords.getDocuments("TrnxRecord");
        String[] iRecord = new String[tRecords.size()];
		if(tRecords.size() > 0) {
        	for(int i = 0; i < tRecords.size(); i++) {
            	iRecord[i] = ((XMLDocument)tRecords.get(i)).toString();
            	out.println("Record-" + i + " = [" + iRecord[i] + "]<br>");
        	}
        }
        else {
        	out.println("<br>指定的日期里没有交易记录<br><br>");
        }
}
else {
//5、下载交易记录请求提交失败，商户自定后续动作
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>