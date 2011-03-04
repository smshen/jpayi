<%
/*
 * @(#)TrnxResult.jsp	V1.0.3	2004/04/16
 *
 * Project: BJP03004
 *
 * Description:
 *    商户交易结果接收范例程序。
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
<HEAD><TITLE>农行网上交易平台-商户接口范例-交易结果接收</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>交易结果<br>
<URL>http://www.hitrust.com.cn</URL>
<%
//1、取得MSG参数，并利用此参数值生成交易结果对象
TrnxResult tResult = new TrnxResult(request.getParameter("MSG"));

//2、判断交易结果状态，进行后续操作
if (tResult.isSuccess()) {
  //3、交易成功
  out.println("商户代码               = [" + tResult.getValue("MerchantID"            ) + "]<br>");
  out.println("企业客户代码           = [" + tResult.getValue("CorporationCustomerNo" ) + "]<br>");
  out.println("商户交易编号           = [" + tResult.getValue("MerchantTrnxNo"        ) + "]<br>");
  out.println("<font color=red>交易流水号 = [" + tResult.getValue("TrnxSN"            ) + "]</font><br>");
  out.println("交易类型               = [" + tResult.getValue("TrnxType"      	      ) + "]<br>");
  out.println("交易金额        	  = [" + tResult.getValue("TrnxAMT"       		  ) + "]<br>");
  out.println("冻结序号 	          = [" + tResult.getValue("FreezeNo"              ) + "]<br>");
  out.println("原冻结序号 	          = [" + tResult.getValue("OrginalFreezeNo"       ) + "]<br>");
  out.println("付款方帐号         	  = [" + tResult.getValue("AccountNo"             ) + "]<br>");
  out.println("付款方帐户名           = [" + tResult.getValue("AccountName"           ) + "]<br>");
  out.println("付款方开户行联行号     = [" + tResult.getValue("AccountBank"           ) + "]<br>");
  out.println("收款方帐号             = [" + tResult.getValue("AccountDBNo"           ) + "]<br>");
  out.println("收款方帐户名           = [" + tResult.getValue("AccountDBName"         ) + "]<br>");
  out.println("收款方开户行联行号     = [" + tResult.getValue("AccountDBBank"         ) + "]<br>");
  out.println("交易时间         	  = [" + tResult.getValue("TrnxTime"              ) + "]<br>");
  out.println("交易状态               = [" + tResult.getValue("TrnxStatus"            ) + "]<br>");
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
  //4、交易失败
  out.println("商户代码               = [" + tResult.getValue("MerchantID"        	  ) + "]<br>");
  out.println("企业客户代码           = [" + tResult.getValue("CorporationCustomerNo" ) + "]<br>");
  out.println("商户交易编号           = [" + tResult.getValue("MerchantTrnxNo"        ) + "]<br>");
  out.println("交易类型               = [" + tResult.getValue("TrnxType"       	  ) + "]<br>");
  out.println("交易金额               = [" + tResult.getValue("TrnxAMT"       		  ) + "]<br>");
  out.println("交易时间         	   = [" + tResult.getValue("TrnxTime"              ) + "]<br>");
  out.println("交易状态               = [" + tResult.getValue("TrnxStatus"            ) + "]<br>");
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
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>