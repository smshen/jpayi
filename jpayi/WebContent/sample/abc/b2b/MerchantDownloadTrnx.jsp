<%
/*
 * @(#)MerchantDownloadTrnx.jsp	V1.0.3	2004/04/28
 *
 * Project: BJP03004
 *
 * Description:
 *    �̻����ؽ��׼�¼����������
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
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-���ؽ��׼�¼����</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>���ؽ��׼�¼����<br>
<%
//1��ȡ�����ؽ��׼�¼��������Ҫ����Ϣ
String tMerchantTrnxDate   = request.getParameter("MerchantTrnxDate");
String tMerchantRemarks    = request.getParameter("MerchantRemarks" );

//2���������ؽ��׼�¼�������
DownloadTrnxRequest tDownloadTrnxRequest = new DownloadTrnxRequest();
tDownloadTrnxRequest.setMerchantTrnxDate(tMerchantTrnxDate);           //�趨�̻����ױ��    ����Ҫ��Ϣ��
tDownloadTrnxRequest.setMerchantRemarks(tMerchantRemarks);             //�趨�̻���ע��Ϣ
 
//3���������ؽ��׼�¼����ȡ��֧����ַ
TrxResponse tTrxResponse = tDownloadTrnxRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//4�����ؽ��׼�¼�����ύ�ɹ�
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
        	out.println("<br>ָ����������û�н��׼�¼<br><br>");
        }
}
else {
//5�����ؽ��׼�¼�����ύʧ�ܣ��̻��Զ���������
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>