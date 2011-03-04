<%
/*
 * @(#)MerchantQueryTrnx.jsp	V1.0.3	2004/04/28
 *
 * Project: BJP03004
 *
 * Description:
 *    �̻����ײ�ѯ����������
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
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-���ײ�ѯ����</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>���ײ�ѯ����<br>
<%
//1��ȡ�ý��ײ�ѯ��������Ҫ����Ϣ
String tMerchantTrnxNo           = request.getParameter("MerchantTrnxNo"       );
String tMerchantRemarks          = request.getParameter("MerchantRemarks"      );

//2�����ɽ��ײ�ѯ�������
QueryTrnxRequest tQueryTrnxRequest = new QueryTrnxRequest();
tQueryTrnxRequest.setMerchantTrnxNo(tMerchantTrnxNo);               //�趨�̻����ױ��    ����Ҫ��Ϣ��
tQueryTrnxRequest.setMerchantRemarks(tMerchantRemarks);             //�趨�̻���ע��Ϣ
 
//3�����ͽ��ײ�ѯ����ȡ��֧����ַ
TrxResponse tTrxResponse = tQueryTrnxRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//4�����ײ�ѯ�����ύ�ɹ�
  out.println("TrnxType       = [" + tTrxResponse.getValue("TrnxType"  )     + "]<br>");
  out.println("TrnxAMT        = [" + tTrxResponse.getValue("TrnxAMT"  )      + "]<br>");
  out.println("MerchantID     = [" + tTrxResponse.getValue("MerchantID"  )   + "]<br>");
  out.println("MerchantTrnxNo = [" + tTrxResponse.getValue("MerchantTrnxNo") + "]<br>");
  out.println("ReturnCode     = [" + tTrxResponse.getValue("ReturnCode")     + "]<br>");
}
else {
//5�����ײ�ѯ�����ύʧ�ܣ��̻��Զ���������
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>