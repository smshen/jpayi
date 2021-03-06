/**
 * 
 */
package cn.zeli.pay.icbc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.zeli.pay.QueryObject;
import cn.zeli.util.DateUtil;
import cn.zeli.util.PayConfig;

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

	private ICBCAPI icbcapi;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.QueryResultObject#getDetailObject()
	 */
	public Object getDetailObject() {
		return icbcapi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.QueryResultObject#process()
	 */
	public void process() throws Exception {
		Map map = new HashMap();
		map.put("APIName", ((IcbcB2cQueryObject) queryObject).getAPIName());
		map.put("APIVersion",
				((IcbcB2cQueryObject) queryObject).getAPIVersion());
		map.put("MerReqData",
				((IcbcB2cQueryObject) queryObject).getMerReqData());

		Map result = IcbcHelper
				.queryHelper(
						PayConfig.ICBC_QUERY_PFX,//"C:\\Documents and Settings\\Administrator\\桌面\\lxhgdg.e.1611.pfx",
						PayConfig.ICBC_QUERY_PFX_PASSWORD,//"123456",
						map,
//						"https://B2C.icbc.com.cn/servlet/ICBCINBSEBusinessServlet",
						"https://corporbank.icbc.com.cn/servlet/ICBCINBSEBusinessServlet",
						"GBK");
		
		Boolean flag = false;
		if (null == result || null == (flag = (Boolean) result.get(IcbcHelper.SUCCESS))) {
			throw new Exception("工行B2C查询获取Order对象错误！");
		}
		if (flag) {
			// 查询得到返回，则将接收到xml转换成对象
			XStream xstream = new XStream(new DomDriver());
			xstream.alias("ICBCAPI", ICBCAPI.class);
			xstream.alias("pub", PUB.class);
			xstream.alias("in", IN.class);
			xstream.alias("out", OUT.class);
			icbcapi = (ICBCAPI) xstream.fromXML((String) result.get(IcbcHelper.CONTENT));
//			System.out.println(icbcapi);
			if (null == icbcapi || null == icbcapi.getOut()) {
				throw new Exception("工行B2C商户订单查询失败：ErrorMessage:[返回xml无法正常转换为ICBCAPI对象]");
			}
		} else {
			String errorMsg = getErrorMsg((String) result.get(IcbcHelper.CONTENT));
			throw new Exception("工行B2C商户订单查询失败：ErrorMessage:["
					+ errorMsg + "]");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeli.pay.QueryResultObject#setQueryObject(cn.zeli.pay.QueryObject)
	 */
	public void setQueryObject(QueryObject queryObject) {
		this.queryObject = (IcbcB2cQueryObject) queryObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.QueryResultObject#status()
	 */
	public String status() {
		return icbcapi.getOut().getTranStat();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.QueryResultObject#statusRemark()
	 */
	public String statusRemark() {
		String status = status();
		if ("0".equals(status)) {
			return "支付成功，未清算";
		}
		if ("1".equals(status)) {
			return "支付成功，已清算";
		}
		if ("2".equals(status)) {
			return "支付失败";
		}
		if ("3".equals(status)) {
			return "支付可疑交易";
		}
		return "未知";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "ICBC|B2C";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return icbcapi.getIn().getOrderNum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public float payAmount() {
		return toFloat(String.valueOf(icbcapi.getOut().getAmount()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		String status = status();
		return "0".equals(status) || "1".equals(status);
	}

	/**
	 * @deprecated
	 * @param str
	 * @throws Exception
	 */
	public static void main(String... str) throws Exception {
		// IcbcB2cQueryObject iqo = new IcbcB2cQueryObject();
		// System.out.println(iqo.getMerReqData());

		IcbcB2cQueryResultObject iqro = new IcbcB2cQueryResultObject();
		IcbcB2cConfigObject icbcB2co = new IcbcB2cConfigObject();
		icbcB2co.setMerID("1611EC23589693");
		icbcB2co.setMerAcct("1611002709200086326");
//		icbcB2co.setPassword("123456");
		IcbcB2cQueryObject iqo = new IcbcB2cQueryObject();
		iqo.setIcbcB2cConfigObject(icbcB2co);
		
		iqo.setOrderNum("YF1104131316031039");
		iqo.setTranDate(DateUtil.getDateFromString("20110413", DateUtil.SHORT_FORMAT_DAY));
		
		iqro.setQueryObject(iqo);
		iqro.process();
	}

	public static class IcbcB2cQueryObject implements QueryObject {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6324749391963138838L;

		/**
		 * 接口名称. MAX (30) 必输，签名，上送“EAPI”，区别大小写！
		 */
		private String APIName = "EAPI";

		/**
		 * 口版本号. =15 必输，签名,上送”001.001.002.001”
		 */
		private String APIVersion = "001.001.002.001";// B2C

		/**
		 * 请求数据,xml包（格式见下）
		 */
		private String MerReqData = "";

		private String orderNum;
		private Date tranDate;
		
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
			in.setOrderNum(orderNum);
			in.setTranDate(DateUtil.getDateFormat(tranDate, DateUtil.SHORT_FORMAT_DAY));
			in.setShopCode(icbcB2cConfigObject.getMerID());
			in.setShopAccount(icbcB2cConfigObject.getMerAcct());
			ibcapi.setIn(in);

			return "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>"
					+ xstream.toXML(ibcapi).replaceAll("\n", "")
							.replaceAll("\r", "").replaceAll(" ", "");
		}

		public void setMerReqData(String merReqData) {
			MerReqData = merReqData;
		}

		public IcbcB2cConfigObject getIcbcB2cConfigObject() {
			return icbcB2cConfigObject;
		}

		public void setIcbcB2cConfigObject(
				IcbcB2cConfigObject icbcB2cConfigObject) {
			this.icbcB2cConfigObject = icbcB2cConfigObject;
		}

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		public Date getTranDate() {
			return tranDate;
		}

		public void setTranDate(Date tranDate) {
			this.tranDate = tranDate;
		}

	}

//	public static class IN {
//
//		private String orderNum;
//		private String tranDate;
//		private String ShopCode;
//		private String ShopAccount;
//
//		public String getOrderNum() {
//			return orderNum;
//		}
//
//		public void setOrderNum(String orderNum) {
//			this.orderNum = orderNum;
//		}
//
//		public String getTranDate() {
//			return tranDate;
//		}
//
//		public void setTranDate(String tranDate) {
//			this.tranDate = tranDate;
//		}
//
//		public String getShopCode() {
//			return ShopCode;
//		}
//
//		public void setShopCode(String shopCode) {
//			ShopCode = shopCode;
//		}
//
//		public String getShopAccount() {
//			return ShopAccount;
//		}
//
//		public void setShopAccount(String shopAccount) {
//			ShopAccount = shopAccount;
//		}
//
//		@Override
//		public String toString() {
//			return "IN [orderNum=" + orderNum + ", tranDate=" + tranDate
//					+ ", ShopCode=" + ShopCode + ", ShopAccount=" + ShopAccount
//					+ "]";
//		}
//		
//	}
//
	public static class ICBCAPI {
		private IN in;
		private PUB pub;
		private OUT out;

		public IN getIn() {
			return in;
		}

		public void setIn(IN in) {
			this.in = in;
		}

		public PUB getPub() {
			return pub;
		}

		public void setPub(PUB pub) {
			this.pub = pub;
		}

		public OUT getOut() {
			return out;
		}

		public void setOut(OUT out) {
			this.out = out;
		}

		@Override
		public String toString() {
			return "ICBCAPI [in=" + in + ", pub=" + pub + ", out=" + out + "]";
		}

	}
//
//	public static class PUB {
//		private String APIName;
//		private String APIVersion;
//
//		public String getAPIName() {
//			return APIName;
//		}
//
//		public void setAPIName(String aPIName) {
//			APIName = aPIName;
//		}
//
//		public String getAPIVersion() {
//			return APIVersion;
//		}
//
//		public void setAPIVersion(String aPIVersion) {
//			APIVersion = aPIVersion;
//		}
//
//		@Override
//		public String toString() {
//			return "PUB [APIName=" + APIName + ", APIVersion=" + APIVersion
//					+ "]";
//		}
//
//	}
//
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

		@Override
		public String toString() {
			return "OUT [tranSerialNum=" + tranSerialNum + ", tranStat="
					+ tranStat + ", bankRem=" + bankRem + ", amount=" + amount
					+ ", currType=" + currType + ", tranTime=" + tranTime
					+ ", ShopAccount=" + ShopAccount + ", PayeeName="
					+ PayeeName + ", JoinFlag=" + JoinFlag + ", MerJoinFlag="
					+ MerJoinFlag + ", CustJoinFlag=" + CustJoinFlag
					+ ", CustJoinNum=" + CustJoinNum + ", CertID=" + CertID
					+ "]";
		}

	}
}
