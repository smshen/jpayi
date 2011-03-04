<%
/*
 * @(#)MerchantFundTransfer.jsp	V1.0.0	2004/10/15
 *
 * Project: BJP03004
 *
 * Description:
 *    商户直接支付请求范例程序。
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    HiTRUST       2004/10/15  V1.0 Release
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
<HEAD><TITLE>农行网上支付平台-商户接口范例-直接支付请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>直接支付请求<br>
<%
//1、取得支付请求所需要的信息
String tMerchantTrnxNo           = request.getParameter("MerchantTrnxNo"       );
String tTrnxAmountStr            = request.getParameter("TrnxAmount"           );
String tTrnxDate                 = request.getParameter("TrnxDate"             );
String tTrnxTime                 = request.getParameter("TrnxTime"             );
String tAccountDBNo              = request.getParameter("AccountDBNo"          );
String tAccountDBName            = request.getParameter("AccountDBName"        );
       tAccountDBName = new String(tAccountDBName.getBytes("ISO-8859-1"),"gb2312");
String tAccountDBBank            = request.getParameter("AccountDBBank"        );
String tResultNotifyURL          = request.getParameter("ResultNotifyURL"      );
String tMerchantRemarks          = request.getParameter("MerchantRemarks"      );
double  tTrnxAmount              = 0;
boolean isTrnxAmountOK = true;
try {
    tTrnxAmount = Double.parseDouble(tTrnxAmountStr);
} catch (NumberFormatException e) {
    isTrnxAmountOK = false;
}

if(isTrnxAmountOK){
//2、生成TrnxInfo对象
TrnxItems tTrnxItems = new TrnxItems();  
tTrnxItems.addTrnxItem(new TrnxItem("0001",     "显示器",       1000.00f, 2));
tTrnxItems.addTrnxItem(new TrnxItem("0002",     "硬盘",         600.00f,  5));
tTrnxItems.addTrnxItem(new TrnxItem("IP000001", "中国移动IP卡", 100.00f,  1));

TrnxRemarks tTrnxRemarks = new TrnxRemarks();
tTrnxRemarks.addTrnxRemark(new TrnxRemark("合同号",  "555000000"));
tTrnxRemarks.addTrnxRemark(new TrnxRemark("采购时间","2003/11/12 14:23:34"));
tTrnxRemarks.addTrnxRemark(new TrnxRemark("交易类型","买入"));
tTrnxRemarks.addTrnxRemark(new TrnxRemark("其它说明","能够使买方确信该交易是自己交易的信息"));

TrnxInfo tTrnxInfo = new TrnxInfo();
tTrnxInfo.setTrnxOpr("TrnxOperator0001");
tTrnxInfo.setTrnxRemarks(tTrnxRemarks);
tTrnxInfo.setTrnxItems(tTrnxItems);

//3、生成直接支付请求对象
FundTransferRequest tFundTransferRequest = new FundTransferRequest();
tFundTransferRequest.setTrnxInfo(tTrnxInfo);                           //设定交易细项        （必要信息）
tFundTransferRequest.setMerchantTrnxNo(tMerchantTrnxNo);               //设定商户交易编号    （必要信息）
tFundTransferRequest.setTrnxAmount(tTrnxAmount);                       //设定交易金额        （必要信息）
tFundTransferRequest.setTrnxDate(tTrnxDate);                           //设定交易日期        （必要信息）
tFundTransferRequest.setTrnxTime(tTrnxTime);                           //设定交易时间        （必要信息）
tFundTransferRequest.setAccountDBNo(tAccountDBNo);                     //设定收款方账号      （必要信息）
tFundTransferRequest.setAccountDBName(tAccountDBName);                 //设定收款方账户名    （必要信息）
tFundTransferRequest.setAccountDBBank(tAccountDBBank);                 //设定收款方账户开户行联行号（必要信息）
tFundTransferRequest.setResultNotifyURL(tResultNotifyURL);             //设定交易结果回传网址（必要信息）
tFundTransferRequest.setMerchantRemarks(tMerchantRemarks);             //设定商户备注信息
 
//4、传送直接支付请求并取得支付网址
TrxResponse tTrxResponse = tFundTransferRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//5、直接支付请求提交成功,将客户端导向出示买方企业客户证书页面（下面注释的4行程序的参数值商户仍然可以取到）
  //out.println("TrnxType       = [" + tTrxResponse.getValue("TrnxType"  )     + "]<br>");
  //out.println("TrnxAMT        = [" + tTrxResponse.getValue("TrnxAMT"  )      + "]<br>");
  //out.println("MerchantID     = [" + tTrxResponse.getValue("MerchantID"  )   + "]<br>");
  //out.println("MerchantTrnxNo = [" + tTrxResponse.getValue("MerchantTrnxNo") + "]<br>");
  response.sendRedirect(tTrxResponse.getValue("PaymentURL"));
}
else {
   //6、直接支付请求提交失败，商户自定后续动作
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>
<%
}else{
%>
交易金额非法!<br>
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>
<%
}
%>