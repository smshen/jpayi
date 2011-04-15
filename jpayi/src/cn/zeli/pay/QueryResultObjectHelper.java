/**
 * 
 */
package cn.zeli.pay;

import cn.zeli.pay.icbc.IcbcB2bConfigObject;
import cn.zeli.pay.icbc.IcbcB2bQueryResultObject;
import cn.zeli.pay.icbc.IcbcB2bQueryResultObject.IcbcB2bQueryObject;
import cn.zeli.pay.icbc.IcbcB2cConfigObject;
import cn.zeli.pay.icbc.IcbcB2cQueryResultObject;
import cn.zeli.pay.icbc.IcbcB2cQueryResultObject.IcbcB2cQueryObject;
import cn.zeli.util.DateUtil;


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

		/*==============================================*/
		/*===============       ABC      ===============*/
		/*==============================================*/ 
		
//		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221427290057", ""));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
//
//		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221439050011", ""));
//		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());

		/*
		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221447530011", ""));
		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
		
		qro = query(AbcB2bQueryResultObject.class, new AbcB2bQueryObject("LX1103221632080024", ""));
		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
		*/
		
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

		
		/*==============================================*/
		/*===============      ICBC      ===============*/
		/*==============================================*/
		// B2C
		IcbcB2cConfigObject icbcB2co = new IcbcB2cConfigObject();
		icbcB2co.setMerID("1611EC23589693");
		icbcB2co.setMerAcct("1611002709200086326");
//		icbcB2co.setPassword("123456");
		
		IcbcB2cQueryObject iqo = new IcbcB2cQueryObject();
		iqo.setIcbcB2cConfigObject(icbcB2co);// icbc Config
		iqo.setOrderNum("YF1104131316031039");// orderId
		iqo.setTranDate(DateUtil.getDateFromString("20110413", DateUtil.SHORT_FORMAT_DAY));// orderTime
		
		qro = query(IcbcB2cQueryResultObject.class, iqo);
		
		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
		

		// B2B
		IcbcB2bConfigObject icbcB2bo = new IcbcB2bConfigObject();
		icbcB2bo.setShop_code("1611EC13401181");
		icbcB2bo.setShop_acc_num("1611002319022123647");
//		icbcB2bo.setPayeeAcct("1611002709200086326");
		IcbcB2bQueryObject ibqo = new IcbcB2bQueryObject();
		ibqo.setIcbcB2bConfigObject(icbcB2bo);
		
		ibqo.setOrderNum("YF1104131720111276");
		ibqo.setTranDate(DateUtil.getDateFromString("20110413", DateUtil.SHORT_FORMAT_DAY));

		qro = query(IcbcB2bQueryResultObject.class, ibqo);
		
		System.out.println(qro.orderId() + "--" + qro.status() + "--" + qro.bankInfo() + "--" + qro.payAmount() + "--" + qro.statusRemark() + "--" + qro.success());
		
	}
	
}
