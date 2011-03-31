<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//cn.zeli.util.HttpUtils
java.util.Map map = request.getParameterMap();
for (java.util.Iterator itr = map.keySet().iterator(); itr.hasNext();) {
	String name = (String) itr.next();
	String[] values = (String[]) map.get(name);
	String valueStr = "";
	for (int i = 0; i < values.length; i++) {
		valueStr = (i == values.length - 1) ? valueStr + values[i]
				: valueStr + values[i] + ",";
	}
	System.out.println(name + "=" + valueStr);
	System.out.println(name + "=" + new String(
			valueStr.getBytes("ISO-8859-1"), "UTF-8"));
	System.out.println(name + "=" + new String(
			valueStr.getBytes("ISO-8859-1"), "GBK"));
	System.out.println(name + "=" + new String(
			valueStr.getBytes("GBK"), "UTF-8"));
	System.out.println(name + "=" + new String(
			valueStr.getBytes("GBK"), "UTF-8"));
}

%>