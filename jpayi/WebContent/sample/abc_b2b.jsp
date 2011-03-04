<%
/*
 * @(#)MerchantFundTransfer.jsp	V1.0.0	2004/10/15
 *
 * Project: BJP03004
 *
 * Description:
 *    �̻�ֱ��֧������������
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
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import = "com.hitrust.b2b.trustpay.client.b2b.*" %>
<%@ page import = "com.hitrust.b2b.trustpay.client.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-ֱ��֧������</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>ֱ��֧������<br>
<%
String path = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

//1��ȡ��֧����������Ҫ����Ϣ
String tMerchantTrnxNo           = "T" + System.currentTimeMillis();//request.getParameter("MerchantTrnxNo"       );
String tTrnxAmountStr            = "0.01";//request.getParameter("TrnxAmount"           );
String tTrnxDate                 = cn.zeli.util.DateUtil.getDateFormat(new java.util.Date(),"yyyy/MM/dd");//request.getParameter("TrnxDate"             );
String tTrnxTime                 = cn.zeli.util.DateUtil.getDateFormat(new java.util.Date(),"HH:mm:ss");//request.getParameter("TrnxTime"             );
String tAccountDBNo              = request.getParameter("AccountDBNo"          );
String tAccountDBName            = request.getParameter("AccountDBName"        );
       tAccountDBName = new String(tAccountDBName.getBytes("ISO-8859-1"),"gb2312");
String tAccountDBBank            = request.getParameter("AccountDBBank"        );
String tResultNotifyURL          = path + "/pay/notify/abc/b2b/server";//request.getParameter("ResultNotifyURL"      );
String tMerchantRemarks          = request.getParameter("MerchantRemarks"      );
double  tTrnxAmount              = 0;
boolean isTrnxAmountOK = true;
try {
    tTrnxAmount = Double.parseDouble(tTrnxAmountStr);
} catch (NumberFormatException e) {
    isTrnxAmountOK = false;
}

if(isTrnxAmountOK){
//2������TrnxInfo����
TrnxItems tTrnxItems = new TrnxItems();  
tTrnxItems.addTrnxItem(new TrnxItem("0001",     "��ʾ��",       1000.00f, 2));
tTrnxItems.addTrnxItem(new TrnxItem("0002",     "Ӳ��",         600.00f,  5));
tTrnxItems.addTrnxItem(new TrnxItem("IP000001", "�й��ƶ�IP��", 100.00f,  1));

TrnxRemarks tTrnxRemarks = new TrnxRemarks();
tTrnxRemarks.addTrnxRemark(new TrnxRemark("��ͬ��",  "555000000"));
tTrnxRemarks.addTrnxRemark(new TrnxRemark("�ɹ�ʱ��","2003/11/12 14:23:34"));
tTrnxRemarks.addTrnxRemark(new TrnxRemark("��������","����"));
tTrnxRemarks.addTrnxRemark(new TrnxRemark("����˵��","�ܹ�ʹ��ȷ�Ÿý������Լ����׵���Ϣ"));

TrnxInfo tTrnxInfo = new TrnxInfo();
tTrnxInfo.setTrnxOpr("TrnxOperator0001");
tTrnxInfo.setTrnxRemarks(tTrnxRemarks);
tTrnxInfo.setTrnxItems(tTrnxItems);

//3������ֱ��֧���������
FundTransferRequest tFundTransferRequest = new FundTransferRequest();
tFundTransferRequest.setTrnxInfo(tTrnxInfo);                           //�趨����ϸ��        ����Ҫ��Ϣ��
tFundTransferRequest.setMerchantTrnxNo(tMerchantTrnxNo);               //�趨�̻����ױ��    ����Ҫ��Ϣ��
tFundTransferRequest.setTrnxAmount(tTrnxAmount);                       //�趨���׽��        ����Ҫ��Ϣ��
tFundTransferRequest.setTrnxDate(tTrnxDate);                           //�趨��������        ����Ҫ��Ϣ��
tFundTransferRequest.setTrnxTime(tTrnxTime);                           //�趨����ʱ��        ����Ҫ��Ϣ��
tFundTransferRequest.setAccountDBNo(tAccountDBNo);                     //�趨�տ�˺�      ����Ҫ��Ϣ��
tFundTransferRequest.setAccountDBName(tAccountDBName);                 //�趨�տ�˻���    ����Ҫ��Ϣ��
tFundTransferRequest.setAccountDBBank(tAccountDBBank);                 //�趨�տ�˻����������кţ���Ҫ��Ϣ��
tFundTransferRequest.setResultNotifyURL(tResultNotifyURL);             //�趨���׽���ش���ַ����Ҫ��Ϣ��
tFundTransferRequest.setMerchantRemarks(tMerchantRemarks);             //�趨�̻���ע��Ϣ
 
//4������ֱ��֧������ȡ��֧����ַ
TrxResponse tTrxResponse = tFundTransferRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//5��ֱ��֧�������ύ�ɹ�,���ͻ��˵����ʾ����ҵ�ͻ�֤��ҳ�棨����ע�͵�4�г���Ĳ���ֵ�̻���Ȼ����ȡ����
  //out.println("TrnxType       = [" + tTrxResponse.getValue("TrnxType"  )     + "]<br>");
  //out.println("TrnxAMT        = [" + tTrxResponse.getValue("TrnxAMT"  )      + "]<br>");
  //out.println("MerchantID     = [" + tTrxResponse.getValue("MerchantID"  )   + "]<br>");
  //out.println("MerchantTrnxNo = [" + tTrxResponse.getValue("MerchantTrnxNo") + "]<br>");
  response.sendRedirect(tTrxResponse.getValue("PaymentURL"));
}
else {
   //6��ֱ��֧�������ύʧ�ܣ��̻��Զ���������
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>
<%
}else{
%>
���׽��Ƿ�!<br>
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>
<%
}
%>