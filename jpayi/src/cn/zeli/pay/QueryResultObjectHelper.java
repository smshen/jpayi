/**
 * 
 */
package cn.zeli.pay;

import cn.zeli.pay.abc.AbcB2bQueryResultObject;
import cn.zeli.pay.abc.AbcB2cQueryResultObject;
import cn.zeli.pay.abc.AbcB2bQueryResultObject.AbcB2bQueryObject;
import cn.zeli.pay.abc.AbcB2cQueryResultObject.AbcB2cQueryObject;


/**
 * @author lz
 *
 */
public class QueryResultObjectHelper {

	public static QueryResultObject query(Class clazz, QueryObject qo) {
		QueryResultObject qro = null;
		try {
			qro = (QueryResultObject) clazz.newInstance();
			qro.setQueryObject(qo);
			qro.process();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qro;
	}
	
	public static void main(String... str) {
		QueryResultObject qro = null;

//		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221427290057", ""));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
//
//		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221439050011", ""));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());

		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221447530011", ""));
		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
		
		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221632080024", ""));
		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
		
		
//		qro = query(AbcB2cQueryResultObject.class, new AbcB2cQueryObject("LX11032516051300692", false));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
//		System.out.println(qro.getDetailObject());
		
//		qro = query(AbcB2cQueryResultObject.class, new AbcB2cQueryObject("LX11032509273900361", false));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
//		System.out.println(qro.getDetailObject());
//		
//		qro = query(AbcB2cQueryResultObject.class, new AbcB2cQueryObject("LX11032510102500101", false));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
//		System.out.println(qro.getDetailObject());
//		
//
////		qro = null;
////		qro = query(AbcB2cQueryResultObject.class, new AbcB2cQueryObject("LX11032509245700231", false));
////		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
////		System.out.println(qro.getDetailObject());
//		
//		qro = null;
//		qro = query(AbcB2cQueryResultObject.class, new AbcB2cQueryObject("LX11032510174400281", false));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
//		System.out.println(qro.getDetailObject());
//		
////		qro = query(AbcB2cQueryResultObject.class, new AbcB2cQueryObject("LX11032509200600152", false));
////		System.out.println(qro.orderId() + qro.status());
////		System.out.println(qro.getDetailObject());
		
	}
	
}
