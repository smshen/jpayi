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
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-����ƾ֤��ӡ</TITLE></HEAD>
<script language=javascript>
     function printResult()
     {
         window.print();
         window.close();
     }
     function ChangeToBig(value){
	   if (value == null) {
	   		return "������";
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
        	return "��Ԫ";
        }
        if (isNaN(v) || v <= 0 || v > 999999999999.99) {
        	return "������";
        }
        else if (v >= 1 && value.charAt(0) == '0') {
        	return "������";
        }        
        else if (value.charAt(0) == '+') {
        	return "������";
        }
        
        if (value.indexOf("e") >=0 || value.indexOf("E") >=0) {
        	return "������";
        }
        
        var strArray = value.split(".");
        
        if (strArray[1] && strArray[1].length > 2) {     //С������泬����λ��ʾ
        	return "������";
        }

        try
        {
                i = 0;
                strBig = "";
                intFen = parseInt(value*100+0.00099999999);          //ת��Ϊ�Է�Ϊ��λ����ֵ
                strFen = intFen.toString();
                strArr = strFen.split(".");
                strFen = strArr[0];
                intFen = strFen.length;      //��ȡ����
                strArr = strFen.split("");   //��������ֵ�ֽ⵽������                
                while(intFen!=0)             //�ֽⲢת��
                {
                        i = i+1;
                        switch(i)           //ѡ��λ
                        {
                                case 1:strDW = "��";break;
                                case 2:strDW = "��";break;
                                case 3:strDW = "Ԫ";break;
                                case 4:strDW = "ʰ";break;
                                case 5:strDW = "��";break;
                                case 6:strDW = "Ǫ";break;
                                case 7:strDW = "��";break;
                                case 8:strDW = "ʰ";break;
                                case 9:strDW = "��";break;
                                case 10:strDW = "Ǫ";break;
                                case 11:strDW = "��";break;
                                case 12:strDW = "ʰ";break;
                                case 13:strDW = "��";break;
                                case 14:strDW = "Ǫ";break;
                        }
                                
                        switch (strArr[intFen-1])  //ѡ������
                        {
                                case "1":strNum = "Ҽ";break;
                                case "2":strNum = "��";break;
                                case "3":strNum = "��";break;
                                case "4":strNum = "��";break;
                                case "5":strNum = "��";break;
                                case "6":strNum = "½";break;
                                case "7":strNum = "��";break;
                                case "8":strNum = "��";break;
                                case "9":strNum = "��";break;
                                case "0":strNum = "��";break;
                        }

                        //�����������
                        strNow = strBig.split("");
                        //��Ϊ��ʱ�����
                        if((i==1)&&(strArr[intFen-1]=="0"))
                        {
                                strBig = "��";
                        }
                        //��Ϊ��ʱ�����
                        else if((i==2)&&(strArr[intFen-1]=="0"))
                        {    //�Ƿ�ͬʱΪ��ʱ�����
                                if(strBig!="��")
                                strBig = "��"+strBig;
                        }
                        //ԪΪ������
                        else if((i==3)&&(strArr[intFen-1]=="0"))
                        {
                                strBig = "Ԫ"+strBig;
                        }
                        //ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ���Ϊ������ʱ����
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="��")&&(strNow[0]!="Ԫ"))
                        {
                                strBig = "��"+strBig;
                        }
                        //ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ�ҲΪ������ʱ���
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="��")){}                                 
                        //ʰ��Ǫ��һλΪ������ǰһλ��Ԫ��Ϊ������ʱ���
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="Ԫ")){}                                
                        //����Ϊ��ʱ���벹������
                        else if((i==7)&&(strArr[intFen-1]=="0"))
                        {
                                strBig ="��"+strBig;
                        }     
                        //ʰ��Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="��")&&(strNow[0]!="��"))
                        {
                                strBig = "��"+strBig;
                        }
                        //ʰ��Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="��")){}
                        //ʰ��Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="��")){}
                        //��λΪ���Ҵ���Ǫλ��ʮ������ʱ������Ǫ�䲹��
                        else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="��")&&(strNow[2]=="Ǫ"))
                        {
                                strBig = strNum+strDW+"����"+strBig.substring(1,strBig.length);
                        }
                        //����������λ
                        else if(i==11)
                        {
                                //��λΪ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
                                if((strArr[intFen-1]=="0")&&(strNow[0]=="��")&&(strNow[2]=="Ǫ"))
                                {
                                        strBig ="��"+"��"+strBig.substring(1,strBig.length);
                                }
                                //��λΪ������ȫΪ�㲻����Ǫλʱ��ȥ����
                                else if((strArr[intFen-1]=="0")&&(strNow[0]=="��")&&(strNow[2]!="Ǫ"))
                                {
                                        strBig ="��"+strBig.substring(1,strBig.length);
                                }
                                //��λ��Ϊ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
                                else if((strNow[0]=="��")&&(strNow[2]=="Ǫ"))
                                {
                                        strBig = strNum+strDW+"��"+strBig.substring(1,strBig.length);
                                }
                                //��λ��Ϊ������ȫΪ�㲻����Ǫλʱ��ȥ����        
                                else if((strNow[0]=="��")&&(strNow[2]!="Ǫ"))
                                {
                                        strBig = strNum+strDW+strBig.substring(1,strBig.length);
                                }
                                //�����������
                                else
                                {
                                        strBig = strNum+strDW+strBig;
                                }
                        }
                        //ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="��")&&(strNow[0]!="��"))
                        {
                                strBig = "��"+strBig;
                        }
                        //ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="��")){}
                        //ʰ�ڣ�Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="��")){}
                        //��λΪ���Ҳ�����Ǫ��λ��ʮ������ʱȥ���ϴ�д�����
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="��")&&(strNow[1]=="��")&&(strNow[3]!="Ǫ"))
                        {
                                strBig = strNum+strDW+strBig.substring(1,strBig.length);
                        }
                        //��λΪ���Ҵ���Ǫ��λ��ʮ������ʱ������Ǫ��䲹��
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="��")&&(strNow[1]=="��")&&(strNow[3]=="Ǫ"))
                        {
                                strBig = strNum+strDW+"����"+strBig.substring(2,strBig.length);
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
                return "";      //��ʧ���򷵻�ԭֵ
        }
    }    
</script>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>����ƾ֤��ӡ<br>
<%
//1��ȡ�ý��ײ�ѯ��������Ҫ����Ϣ
String tMerchantTrnxNo           = request.getParameter("MerchantTrnxNo"       );

//2�����ɽ��ײ�ѯ�������
PrintTrnxVoucherRequest tPrintTrnxVoucherRequest = new PrintTrnxVoucherRequest();
tPrintTrnxVoucherRequest.setMerchantTrnxNo(tMerchantTrnxNo);               //�趨�̻����ױ��    ����Ҫ��Ϣ��
//3�����ͽ��ײ�ѯ����ȡ��֧����ַ
TrxResponse tTrxResponse = tPrintTrnxVoucherRequest.postRequest();
if (tTrxResponse.isSuccess()) {
//4�����ײ�ѯ�����ύ�ɹ�
  out.println("���ӽ���ƾ֤����     = [" + tTrxResponse.getValue("VoucherNo")     + "]<br>");
  out.println("�����˻���       = [" + tTrxResponse.getValue("AccountName"  )     + "]<br>");
  out.println("�������˺�        = [" + tTrxResponse.getValue("AccountNo"  )      + "]<br>");
  out.println("�����˿�������     = [" + tTrxResponse.getValue("AccountBank"  )   + "]<br>");
  out.println("�տ��˻���       = [" + tTrxResponse.getValue("AccountDBName"  )     + "]<br>");
  out.println("�տ����˺�        = [" + tTrxResponse.getValue("AccountDBNo"  )      + "]<br>");
  out.println("�տ��˿�������     = [" + tTrxResponse.getValue("AccountDBBank"  )   + "]<br>");
  out.println("���(Сд) = [" + tTrxResponse.getValue("TrnxAMT") + "]<br>");
  out.println("���(��д) = [<script language=javascript>document.write(ChangeToBig('" + tTrxResponse.getValue("TrnxAMT") + "'))</script>]<br>");
  out.println("��;     = []<br>");
  out.println("������ˮ�� = [" + tTrxResponse.getValue("TrnxSN") + "]<br>");
  out.println("������     = [" + tTrxResponse.getValue("MerchantTrnxNo")     + "]<br>");
  out.println("�������� = [" + tTrxResponse.getValue("TrnxDate") + "]<br>");
  out.println("ʱ���     = [" + tTrxResponse.getValue("TrnxTime")     + "]<br>");
  out.println("��ע = []<br>");
  out.println("��ӡ����     = [" + tTrxResponse.getValue("PrtTime")     + "]<br>");
}
else {
//5�����ײ�ѯ�����ύʧ�ܣ��̻��Զ���������
  out.println("ReturnCode   = [" + tTrxResponse.getReturnCode  () + "]<br>");
  out.println("ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>");
}
%>
<a class=top href="javascript:printResult()">��ӡ</a>&nbsp;
<a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY></HTML>