<%
/*
 * @(#)MerchantQueryTrnxBatch.jsp	V1.0.3	2004/04/28
 *
 * Project: BJP03004
 *
 * Description:
 *    ���׼�¼������ѯ����������
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
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-���׼�¼������ѯ����</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>���׼�¼������ѯ����<br>
<%
//1��ȡ�ý���������ѯ��������Ҫ����Ϣ


String tTrnxStatus = request.getParameter("TrnxStatus");
String tStartAccDate = request.getParameter("StDate");
String tEndAccDate = request.getParameter("EndDate");

//2�����ɽ���������ѯ�������
QueryTrnxBatchRequest tQueryTrnxBatchRequest = new QueryTrnxBatchRequest();

tQueryTrnxBatchRequest.setTrnxStatus(tTrnxStatus);               //����״̬   
tQueryTrnxBatchRequest.setStartAccDate(tStartAccDate);          //��ʼ��ѯ����YYYYMMDD ����Ҫ��Ϣ��
tQueryTrnxBatchRequest.setEndAccDate(tEndAccDate);             //��ֹ��ѯ����YYYYMMDD ����Ҫ��Ϣ��

 
//3�����ͽ���������ѯ����ȡ��֧����ַ
TrxResponse tTrxResponse = tQueryTrnxBatchRequest.postRequest();
if (tTrxResponse.isSuccess()) {

//4�����ײ�ѯ�����ύ�ɹ�
	XMLDocument tXMLDocument = tTrxResponse.getResponseMessage();
	ArrayList tResults = tXMLDocument.getDocuments("Row");
	if (tResults.size()==0)
	out.print("û�н��׼�¼<br>");
	else{
		out.print("������   |      ����ʱ��      |   ����˻���   |   ����ʺ�   |   ���׽��   |   ����״̬<br>");
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
//5������������ѯ�����ύʧ�ܣ��̻��Զ���������
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>