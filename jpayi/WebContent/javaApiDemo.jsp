<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=GBK"
pageEncoding="GBK"
%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="cn.com.infosec.icbc.ReturnValue" %>

<META http-equiv="Content-Type" content="text/html; charset=GBK">
<TITLE>javaApiDemo.jsp</TITLE>
</HEAD>
<BODY bgcolor=Silver>
<%
String tranData = "this is a test";
out.println("<font face='Arial' size='4' color='Green'>���ģ�</font>"+tranData+"<br>");
String password = "11111111";
try{
	byte[] byteSrc = tranData.getBytes();
	char[] keyPass = password.toCharArray();
	
	FileInputStream in1 = new FileInputStream("E:\\liuzeng\\workspace\\jpayi\\src\\cert\\icbc\\user.crt");
	byte[] bcert = new byte[in1.available()];
	in1.read(bcert);
	in1.close();
	FileInputStream in2 = new FileInputStream("E:\\liuzeng\\workspace\\jpayi\\src\\cert\\icbc\\user.key");
	byte[] bkey = new byte[in2.available()];
	in2.read(bkey);
	in2.close();
	

    byte[] sign =ReturnValue.sign(byteSrc,byteSrc.length,bkey,keyPass);
    if (sign==null) {
    	out.println("<font face='Arial' size='4' color='Red'>ǩ��ʧ��,ǩ������Ϊ�ա�<br>����֤��˽Կ��˽Կ���������Ƿ���ȷ��</font><br>");
    }else{
    	out.println("<font face='Arial' size='4' color='Green'>ǩ���ɹ�</font><br>");
   
	    byte[] EncSign = ReturnValue.base64enc(sign);
	    String SignMsgBase64=new String(EncSign).toString();
	    out.println("<font face='Arial' size='4' color='Green'>ǩ����ϢBASE64���룺</font>"+SignMsgBase64.substring(0,100)+"...<br>");
	    
		byte[] EncCert=ReturnValue.base64enc(bcert);
		String CertBase64=new String(EncCert).toString();
		out.println("<font face='Arial' size='4' color='Green'>֤�鹫ԿBASE64���룺</font>"+CertBase64.substring(0,100)+"...<br>");
	
		byte[] DecSign = ReturnValue.base64dec(EncSign);
	    if (DecSign!=null){
	    	out.println("<font face='Arial' size='4' color='Green'>ǩ����ϢBASE64����ɹ�</font><br>");
	    	byte[] DecCert = ReturnValue.base64dec(EncCert);
	    	if (DecCert!=null){
	    		out.println("<font face='Arial' size='4' color='Green'>֤�鹫ԿBASE64����ɹ�</font><br>");
	    		int a=ReturnValue.verifySign(byteSrc,byteSrc.length,DecCert,DecSign);
	    		out.println("<br/>" + new String(DecSign, "GBK") + "<br/>");
	    		if (a==0) out.println("<font face='Arial' size='4' color='Green'>��ǩ�ɹ�</font><br>");
	    		else out.println("<font face='Arial' size='4' color='Red'>��ǩʧ��<br>��֤�����룺</font><br>"+a);	    		
	    	}else out.println("<font face='Arial' size='4' color='Red'>֤��BASE64����ʧ��</font><br>");
	    }else out.println("<font face='Arial' size='4' color='Red'>ǩ����ϢBASE64����ʧ��</font><br>");	
	}
	
}catch (Exception e){
	out.println(e);
}
%>
</BODY>
</HTML>
