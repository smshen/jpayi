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
<HEAD><TITLE>农行网上支付平台-商户接口范例-电子凭证打印</TITLE></HEAD>
<script language=javascript>
     function printResult()
     {
         window.print();
         window.close();
     }
     function ChangeToBig(value){
	   if (value == null) {
	   		return "错误金额";
	   }
	   
	   value = "" + value;    
       
       var intFen,i;
       var strArr,strCheck,nstrCheck,strFen,strDW,strNum,strBig,strNow;
               
        var v = -1;
        try {
        	v = parseFloat(value);
        }
        catch (ex) {
        }
         
        if (v == 0 ) {
        	return "零元";
        }
        if (isNaN(v) || v <= 0 || v > 999999999999.99) {
        	return "错误金额";
        }
        else if (v >= 1 && value.charAt(0) == '0') {
        	return "错误金额";
        }        
        else if (value.charAt(0) == '+') {
        	return "错误金额";
        }
        
        if (value.indexOf("e") >=0 || value.indexOf("E") >=0) {
        	return "错误金额";
        }
        
        var strArray = value.split(".");
        
        if (strArray[1] && strArray[1].length > 2) {     //小数点后面超过两位提示
        	return "错误金额";
        }

        try
        {
                i = 0;
                strBig = "";
                intFen = parseInt(value*100+0.00099999999);          //转换为以分为单位的数值
                strFen = intFen.toString();
                strArr = strFen.split(".");
                strFen = strArr[0];
                intFen = strFen.length;      //获取长度
                strArr = strFen.split("");   //将各个数值分解到数组内                
                while(intFen!=0)             //分解并转换
                {
                        i = i+1;
                        switch(i)           //选择单位
                        {
                                case 1:strDW = "分";break;
                                case 2:strDW = "角";break;
                                case 3:strDW = "元";break;
                                case 4:strDW = "拾";break;
                                case 5:strDW = "佰";break;
                                case 6:strDW = "仟";break;
                                case 7:strDW = "万";break;
                                case 8:strDW = "拾";break;
                                case 9:strDW = "佰";break;
                                case 10:strDW = "仟";break;
                                case 11:strDW = "亿";break;
                                case 12:strDW = "拾";break;
                                case 13:strDW = "佰";break;
                                case 14:strDW = "仟";break;
                        }
                                
                        switch (strArr[intFen-1])  //选择数字
                        {
                                case "1":strNum = "壹";break;
                                case "2":strNum = "贰";break;
                                case "3":strNum = "叁";break;
                                case "4":strNum = "肆";break;
                                case "5":strNum = "伍";break;
                                case "6":strNum = "陆";break;
                                case "7":strNum = "柒";break;
                                case "8":strNum = "捌";break;
                                case "9":strNum = "玖";break;
                                case "0":strNum = "零";break;
                        }

                        //处理特殊情况
                        strNow = strBig.split("");
                        //分为零时的情况
                        if((i==1)&&(strArr[intFen-1]=="0"))
                        {
                                strBig = "整";
                        }
                        //角为零时的情况
                        else if((i==2)&&(strArr[intFen-1]=="0"))
                        {    //角分同时为零时的情况
                                if(strBig!="整")
                                strBig = "零"+strBig;
                        }
                        //元为零的情况
                        else if((i==3)&&(strArr[intFen-1]=="0"))
                        {
                                strBig = "元"+strBig;
                        }
                        //拾－仟中一位为零且其前一位（元以上）不为零的情况时补零
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="元"))
                        {
                                strBig = "零"+strBig;
                        }
                        //拾－仟中一位为零且其前一位（元以上）也为零的情况时跨过
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}                                 
                        //拾－仟中一位为零且其前一位是元且为零的情况时跨过
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="元")){}                                
                        //当万为零时必须补上万字
                        else if((i==7)&&(strArr[intFen-1]=="0"))
                        {
                                strBig ="万"+strBig;
                        }     
                        //拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="万"))
                        {
                                strBig = "零"+strBig;
                        }
                        //拾万－仟万中一位为零且其前一位（万以上）也为零的情况时跨过
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="万")){}
                        //拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}
                        //万位为零且存在仟位和十万以上时，在万仟间补零
                        else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
                        {
                                strBig = strNum+strDW+"万零"+strBig.substring(1,strBig.length);
                        }
                        //单独处理亿位
                        else if(i==11)
                        {
                                //亿位为零且万全为零存在仟位时，去掉万补为零
                                if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
                                {
                                        strBig ="亿"+"零"+strBig.substring(1,strBig.length);
                                }
                                //亿位为零且万全为零不存在仟位时，去掉万
                                else if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]!="仟"))
                                {
                                        strBig ="亿"+strBig.substring(1,strBig.length);
                                }
                                //亿位不为零且万全为零存在仟位时，去掉万补为零
                                else if((strNow[0]=="万")&&(strNow[2]=="仟"))
                                {
                                        strBig = strNum+strDW+"零"+strBig.substring(1,strBig.length);
                                }
                                //亿位不为零且万全为零不存在仟位时，去掉万        
                                else if((strNow[0]=="万")&&(strNow[2]!="仟"))
                                {
                                        strBig = strNum+strDW+strBig.substring(1,strBig.length);
                                }
                                //其他正常情况
                                else
                                {
                                        strBig = strNum+strDW+strBig;
                                }
                        }
                        //拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="亿"))
                        {
                                strBig = "零"+strBig;
                        }
                        //拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="亿")){}
                        //拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零")){}
                        //亿位为零且不存在仟万位和十亿以上时去掉上次写入的零
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]!="仟"))
                        {
                                strBig = strNum+strDW+strBig.substring(1,strBig.length);
                        }
                        //亿位为零且存在仟万位和十亿以上时，在亿仟万间补零
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]=="仟"))
                        {
                                strBig = strNum+strDW+"亿零"+strBig.substring(2,strBig.length);
                        }
                        else
                        {
                                strBig = strNum+strDW+strBig;
                        }
                                
                        strFen = strFen.substring(0,intFen-1);
                        intFen = strFen.length;
                        strArr = strFen.split("");
                }
                return strBig;
        }
        catch(err)
        {
                return "";      //若失败则返回原值
        }
    }    
</script>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>电子凭证打印<br>
<%
//1、取得交易查询请求所需要的信息
String tMerchantTrnxNo           = request.getParameter("MerchantTrnxNo"       );

//2、生成交易查询请求对象
PrintTrnxVoucherRequest tPrintTrnxVoucherRequest = new PrintTrnxVoucherRequest();
tPrintTrnxVoucherRequest.setMerchantTrnxNo(tMerchantTrnxNo);               //设定商户交易编号    （必要信息）
//3、传送交易查询请求并取得支付网址
TrxResponse tTrxResponse = tPrintTrnxVoucherRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//4、交易查询请求提交成功
  out.println("电子交易凭证号码     = [" + tTrxResponse.getValue("VoucherNo")     + "]<br>");
  out.println("付款人户名       = [" + tTrxResponse.getValue("AccountName"  )     + "]<br>");
  out.println("付款人账号        = [" + tTrxResponse.getValue("AccountNo"  )      + "]<br>");
  out.println("付款人开户银行     = [" + tTrxResponse.getValue("AccountBank"  )   + "]<br>");
  out.println("收款人户名       = [" + tTrxResponse.getValue("AccountDBName"  )     + "]<br>");
  out.println("收款人账号        = [" + tTrxResponse.getValue("AccountDBNo"  )      + "]<br>");
  out.println("收款人开户银行     = [" + tTrxResponse.getValue("AccountDBBank"  )   + "]<br>");
  out.println("金额(小写) = [" + tTrxResponse.getValue("TrnxAMT") + "]<br>");
  out.println("金额(大写) = [<script language=javascript>document.write(ChangeToBig('" + tTrxResponse.getValue("TrnxAMT") + "'))</script>]<br>");
  out.println("用途     = []<br>");
  out.println("交易流水号 = [" + tTrxResponse.getValue("TrnxSN") + "]<br>");
  out.println("订单号     = [" + tTrxResponse.getValue("MerchantTrnxNo")     + "]<br>");
  out.println("交易日期 = [" + tTrxResponse.getValue("TrnxDate") + "]<br>");
  out.println("时间戳     = [" + tTrxResponse.getValue("TrnxTime")     + "]<br>");
  out.println("备注 = []<br>");
  out.println("打印日期     = [" + tTrxResponse.getValue("PrtTime")     + "]<br>");
}
else {
//5、交易查询请求提交失败，商户自定后续动作
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a class=top href="javascript:printResult()">打印</a>&nbsp;
<a href='Merchant.html'>回商户首页</a></CENTER>
</BODY></HTML>