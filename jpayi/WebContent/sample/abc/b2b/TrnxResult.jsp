<%
/*
 * @(#)TrnxResult.jsp	V1.0.3	2004/04/16
 *
 * Project: BJP03004
 *
 * Description:
 *    �̻����׽�����շ�������
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    HiTRUST       2004/05/13  V1.0 Release
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
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ�����Ͻ���ƽ̨-�̻��ӿڷ���-���׽������</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>���׽��<br>
<URL>http://www.hitrust.com.cn</URL>
<%
//1��ȡ��MSG�����������ô˲���ֵ���ɽ��׽������
TrnxResult tResult = new TrnxResult(request.getParameter("MSG"));

//2���жϽ��׽��״̬�����к�������
if (tResult.isSuccess()) {
  //3�����׳ɹ�
  out.println("�̻�����               = [" + tResult.getValue("MerchantID"            ) + "]<br>");
  out.println("��ҵ�ͻ�����           = [" + tResult.getValue("CorporationCustomerNo" ) + "]<br>");
  out.println("�̻����ױ��           = [" + tResult.getValue("MerchantTrnxNo"        ) + "]<br>");
  out.println("<font color=red>������ˮ�� = [" + tResult.getValue("TrnxSN"            ) + "]</font><br>");
  out.println("��������               = [" + tResult.getValue("TrnxType"      	      ) + "]<br>");
  out.println("���׽��        	  = [" + tResult.getValue("TrnxAMT"       		  ) + "]<br>");
  out.println("������� 	          = [" + tResult.getValue("FreezeNo"              ) + "]<br>");
  out.println("ԭ������� 	          = [" + tResult.getValue("OrginalFreezeNo"       ) + "]<br>");
  out.println("����ʺ�         	  = [" + tResult.getValue("AccountNo"             ) + "]<br>");
  out.println("����ʻ���           = [" + tResult.getValue("AccountName"           ) + "]<br>");
  out.println("������������к�     = [" + tResult.getValue("AccountBank"           ) + "]<br>");
  out.println("�տ�ʺ�             = [" + tResult.getValue("AccountDBNo"           ) + "]<br>");
  out.println("�տ�ʻ���           = [" + tResult.getValue("AccountDBName"         ) + "]<br>");
  out.println("�տ���������к�     = [" + tResult.getValue("AccountDBBank"         ) + "]<br>");
  out.println("����ʱ��         	  = [" + tResult.getValue("TrnxTime"              ) + "]<br>");
  out.println("����״̬               = [" + tResult.getValue("TrnxStatus"            ) + "]<br>");
/*
  out.println("MerchantID             = [" + tResult.getValue("MerchantID"        	  ) + "]<br>");
  out.println("CorporationCustomerNo  = [" + tResult.getValue("CorporationCustomerNo" ) + "]<br>");
  out.println("MerchantTrnxNo         = [" + tResult.getValue("MerchantTrnxNo"        ) + "]<br>");
  out.println("TrnxSN                 = [" + tResult.getValue("TrnxSN"                ) + "]<br>");
  out.println("TrnxType               = [" + tResult.getValue("TrnxType"       	      ) + "]<br>");
  out.println("TrnxAMT        		  = [" + tResult.getValue("TrnxAMT"       		  ) + "]<br>");
  out.println("FreezeNo 	          = [" + tResult.getValue("FreezeNo"              ) + "]<br>");
  out.println("OrginalFreezeNo 	      = [" + tResult.getValue("OrginalFreezeNo"       ) + "]<br>");
  out.println("AccountNo         	  = [" + tResult.getValue("AccountNo"             ) + "]<br>");
  out.println("AccountName            = [" + tResult.getValue("AccountName"           ) + "]<br>");
  out.println("AccountBank            = [" + tResult.getValue("AccountBank"           ) + "]<br>");
  out.println("AccountDBNo            = [" + tResult.getValue("AccountDBNo"           ) + "]<br>");
  out.println("AccountDBName          = [" + tResult.getValue("AccountDBName"         ) + "]<br>");
  out.println("AccountDBBank          = [" + tResult.getValue("AccountDBBank"         ) + "]<br>");
  out.println("TrnxTime         	  = [" + tResult.getValue("TrnxTime"              ) + "]<br>");
  out.println("TrnxStatus             = [" + tResult.getValue("TrnxStatus"            ) + "]<br>");
*/    
}
else {
  //4������ʧ��
  out.println("�̻�����               = [" + tResult.getValue("MerchantID"        	  ) + "]<br>");
  out.println("��ҵ�ͻ�����           = [" + tResult.getValue("CorporationCustomerNo" ) + "]<br>");
  out.println("�̻����ױ��           = [" + tResult.getValue("MerchantTrnxNo"        ) + "]<br>");
  out.println("��������               = [" + tResult.getValue("TrnxType"       	  ) + "]<br>");
  out.println("���׽��               = [" + tResult.getValue("TrnxAMT"       		  ) + "]<br>");
  out.println("����ʱ��         	   = [" + tResult.getValue("TrnxTime"              ) + "]<br>");
  out.println("����״̬               = [" + tResult.getValue("TrnxStatus"            ) + "]<br>");
/*                                                                                    
  out.println("MerchantID             = [" + tResult.getValue("MerchantID"        	  ) + "]<br>");
  out.println("CorporationCustomerNo  = [" + tResult.getValue("CorporationCustomerNo" ) + "]<br>");
  out.println("MerchantTrnxNo         = [" + tResult.getValue("MerchantTrnxNo"        ) + "]<br>");
  out.println("TrnxType               = [" + tResult.getValue("TrnxType"       	      ) + "]<br>");
  out.println("TrnxAMT        		  = [" + tResult.getValue("TrnxAMT"       		  ) + "]<br>");
  out.println("TrnxTime         	  = [" + tResult.getValue("TrnxTime"              ) + "]<br>");
  out.println("TrnxStatus             = [" + tResult.getValue("TrnxStatus"            ) + "]<br>");
*/  
  //out.println("ReturnCode   = [" + tResult.getReturnCode  () + "]<br>");
  //out.println("ErrorMessage = [" + tResult.getErrorMessage() + "]<br>");
}
%>
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>