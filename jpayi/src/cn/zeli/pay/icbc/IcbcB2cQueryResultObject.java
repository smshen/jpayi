/**
 * 
 */
package cn.zeli.pay.icbc;

import java.util.HashMap;
import java.util.Map;

import cn.zeli.pay.QueryObject;
import cn.zeli.pay.abc.AbcB2bQueryResultObject.AbcB2bQueryObject;
import cn.zeli.util.HttpUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author lz
 *
 */
public class IcbcB2cQueryResultObject extends AbstractIcbcQueryResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4400057647670136665L;

	
	
	/* (non-Javadoc)
	 * @see cn.zeli.pay.QueryResultObject#getDetailObject()
	 */
	public Object getDetailObject() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.QueryResultObject#process()
	 */
	public void process() throws Exception {
		Map map = new HashMap();
		map.put("APIName", ((IcbcB2cQueryObject) queryObject).getAPIName());
		map.put("APIVersion", ((IcbcB2cQueryObject) queryObject).getAPIVersion());
		map.put("MerReqData", ((IcbcB2cQueryObject) queryObject).getMerReqData());
//		String rsp = HttpUtils.doPostWithHttps("https://B2C.icbc.com.cn/servlet/ICBCINBSEBusinessServlet", map, "GBK", 10000, 10000);
//		System.out.println(rsp);
		
		IcbcHelper.queryHelper("F:\\icbc\\B2B\\test.jks", "123456", map, "https://B2C.icbc.com.cn/servlet/ICBCINBSEBusinessServlet", "GBK");
		
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.QueryResultObject#setQueryObject(cn.zeli.pay.QueryObject)
	 */
	public void setQueryObject(QueryObject queryObject) {
		this.queryObject = (IcbcB2cQueryObject) queryObject;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.QueryResultObject#status()
	 */
	public String status() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.QueryResultObject#statusRemark()
	 */
	public String statusRemark() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public float payAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @deprecated 
	 * @param str
	 * @throws Exception 
	 */
	public static void main(String... str) throws Exception {
//		IcbcB2cQueryObject iqo = new IcbcB2cQueryObject();
//		System.out.println(iqo.getMerReqData());
		
		IcbcB2cQueryResultObject iqro = new IcbcB2cQueryResultObject();
		IcbcB2cConfigObject icbcB2co = new IcbcB2cConfigObject();
		icbcB2co.setMerID("1611EC23589693");
		icbcB2co.setMerAcct("1611002319022123647");
		icbcB2co.setPassword("123456");
		IcbcB2cQueryObject iqo = new IcbcB2cQueryObject();
		iqo.setIcbcB2cConfigObject(icbcB2co);
		iqro.setQueryObject(iqo);
		iqro.process();
	}
	
	public static class IcbcB2cQueryObject implements QueryObject {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6324749391963138838L;
		
		/**
		 * 接口名称.
		 * MAX (30)
		 * 必输，签名，上送“EAPI”，区别大小写！
		 */
		private String APIName = "EAPI";
		
		/**
		 * 口版本号.
		 * =15
		 * 必输，签名,上送”001.001.002.001”
		 */
		private String APIVersion = "001.001.002.001";
		
		/**
		 * 请求数据,xml包（格式见下）
		 */
		private String MerReqData = "";
		
		private IcbcB2cConfigObject icbcB2cConfigObject;

		public String getAPIName() {
			return APIName;
		}

		public void setAPIName(String aPIName) {
			APIName = aPIName;
		}

		public String getAPIVersion() {
			return APIVersion;
		}

		public void setAPIVersion(String aPIVersion) {
			APIVersion = aPIVersion;
		}

		public String getMerReqData() {
			XStream xstream = new XStream(new DomDriver()); 
			xstream.alias("in", IN.class);
			xstream.alias("ICBCAPI", ICBCAPI.class);

			ICBCAPI ibcapi = new ICBCAPI();
			IN in = new IN();
			in.setOrderNum("YF1103251615360172");
			in.setTranDate("20110325");
			in.setShopCode(icbcB2cConfigObject.getMerID());
			in.setShopAccount(icbcB2cConfigObject.getMerAcct());
			ibcapi.setIn(in);
			
			return "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>" + xstream.toXML(ibcapi);
		}

		public void setMerReqData(String merReqData) {
			MerReqData = merReqData;
		}

		public IcbcB2cConfigObject getIcbcB2cConfigObject() {
			return icbcB2cConfigObject;
		}

		public void setIcbcB2cConfigObject(IcbcB2cConfigObject icbcB2cConfigObject) {
			this.icbcB2cConfigObject = icbcB2cConfigObject;
		}
		
	}
	
	public static class IN {

		private String orderNum;
		private String tranDate;
		private String ShopCode;
		private String ShopAccount;

		public String getOrderNum() {
			return orderNum;
		}
		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}
		public String getTranDate() {
			return tranDate;
		}
		public void setTranDate(String tranDate) {
			this.tranDate = tranDate;
		}
		public String getShopCode() {
			return ShopCode;
		}
		public void setShopCode(String shopCode) {
			ShopCode = shopCode;
		}
		public String getShopAccount() {
			return ShopAccount;
		}
		public void setShopAccount(String shopAccount) {
			ShopAccount = shopAccount;
		}
	}
	
	public static class ICBCAPI {
		private IN in;

		public IN getIn() {
			return in;
		}

		public void setIn(IN in) {
			this.in = in;
		}
		
		
	}
	
	public static class PUB {
		private String APIName;
		private String APIVersion;
		public String getAPIName() {
			return APIName;
		}
		public void setAPIName(String aPIName) {
			APIName = aPIName;
		}
		public String getAPIVersion() {
			return APIVersion;
		}
		public void setAPIVersion(String aPIVersion) {
			APIVersion = aPIVersion;
		}
		
	}
	
	public static class OUT {
		/**
		 * 指令序号
		 */
		private String tranSerialNum;
		/**
		 * 订单处理状态
		 */
		private String tranStat;
		
		/**
		 * 指令错误信息
		 */
		private String bankRem;
		
		/**
		 * 订单总金额
		 */
		private String amount;
		
		/**
		 * 支付币种
		 */
		private String currType;
	
		/**
		 * 返回通知日期时间
		 */
		private String tranTime;
		
		/**
		 * 商城账号
		 */
		private String ShopAccount;
		
		/**
		 * 商城户名
		 */
		private String PayeeName;
		
		/**
		 * 校验联名标志
		 */
		private String JoinFlag;
		
		/**
		 * 商城联名标志
		 */
		private String MerJoinFlag;
		
		/**
		 * 客户联名标志
		 */
		private String CustJoinFlag;
		
		/**
		 * 联名会员号
		 */
		private String CustJoinNum;
		
		/**
		 * 商户签名证书id
		 */
		private String CertID;

		public String getTranSerialNum() {
			return tranSerialNum;
		}

		public void setTranSerialNum(String tranSerialNum) {
			this.tranSerialNum = tranSerialNum;
		}

		public String getTranStat() {
			return tranStat;
		}

		public void setTranStat(String tranStat) {
			this.tranStat = tranStat;
		}

		public String getBankRem() {
			return bankRem;
		}

		public void setBankRem(String bankRem) {
			this.bankRem = bankRem;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getCurrType() {
			return currType;
		}

		public void setCurrType(String currType) {
			this.currType = currType;
		}

		public String getTranTime() {
			return tranTime;
		}

		public void setTranTime(String tranTime) {
			this.tranTime = tranTime;
		}

		public String getShopAccount() {
			return ShopAccount;
		}

		public void setShopAccount(String shopAccount) {
			ShopAccount = shopAccount;
		}

		public String getPayeeName() {
			return PayeeName;
		}

		public void setPayeeName(String payeeName) {
			PayeeName = payeeName;
		}

		public String getJoinFlag() {
			return JoinFlag;
		}

		public void setJoinFlag(String joinFlag) {
			JoinFlag = joinFlag;
		}

		public String getMerJoinFlag() {
			return MerJoinFlag;
		}

		public void setMerJoinFlag(String merJoinFlag) {
			MerJoinFlag = merJoinFlag;
		}

		public String getCustJoinFlag() {
			return CustJoinFlag;
		}

		public void setCustJoinFlag(String custJoinFlag) {
			CustJoinFlag = custJoinFlag;
		}

		public String getCustJoinNum() {
			return CustJoinNum;
		}

		public void setCustJoinNum(String custJoinNum) {
			CustJoinNum = custJoinNum;
		}

		public String getCertID() {
			return CertID;
		}

		public void setCertID(String certID) {
			CertID = certID;
		}
		
		
	}
}
