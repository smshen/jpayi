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
public class IcbcB2bQueryResultObject extends AbstractIcbcQueryResultObject {

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
		map.put("APIName", ((IcbcB2bQueryObject) queryObject).getAPIName());
		map.put("APIVersion",
				((IcbcB2bQueryObject) queryObject).getAPIVersion());
		map.put("MerReqData",
				((IcbcB2bQueryObject) queryObject).getMerReqData());

		Map result = IcbcHelper
				.queryHelper(
						PayConfig.ICBC_QUERY_PFX,//"C:\\Documents and Settings\\Administrator\\桌面\\lxhgdg.e.1611.pfx",
						PayConfig.ICBC_QUERY_PFX_PASSWORD,//"123456",
						map,
//						"https://B2B.icbc.com.cn/servlet/ICBCINBSEBusinessServlet",
						"https://corporbank.icbc.com.cn/servlet/ICBCINBSEBusinessServlet",
						"GBK");
		
		Boolean flag = false;
		if (null == result || null == (flag = (Boolean) result.get(IcbcHelper.SUCCESS))) {
			throw new Exception("工行B2B查询获取Order对象错误！");
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
				throw new Exception("工行B2B商户订单查询失败：ErrorMessage:[返回xml无法正常转换为ICBCAPI对象]");
			}
		} else {
			String errorMsg = getErrorMsg((String) result.get(IcbcHelper.CONTENT));
			throw new Exception("工行B2B商户订单查询失败：ErrorMessage:["
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
		this.queryObject = (IcbcB2bQueryObject) queryObject;
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
		if ("3".equals(status)) {
			return "指令处理完成，转账成功";
		}
		if ("4".equals(status)) {
			return "指令处理失败，转账未完成";
		}
		if ("6".equals(status)) {
			return "指令超过支付人的限额，正在等待主管会计批复";
		}
		if ("7".equals(status)) {
			return "指令超过支付人的限额，正在等待主管会计第二次批复";
		}
		if ("8".equals(status)) {
			return "指令超过支付人的限额，被主管会计否决";
		}
		if ("9".equals(status)) {
			return "银行正在处理（可疑）";
		}
		return "未知";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "ICBC|B2B";
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
		return "3".equals(status);
	}

	/**
	 * @deprecated
	 * @param str
	 * @throws Exception
	 */
	public static void main(String... str) throws Exception {
		// IcbcB2bQueryObject iqo = new IcbcB2bQueryObject();
		// System.out.println(iqo.getMerReqData());

		IcbcB2bQueryResultObject iqro = new IcbcB2bQueryResultObject();
		IcbcB2bConfigObject icbcB2bo = new IcbcB2bConfigObject();
		icbcB2bo.setShop_code("1611EC13401181");
		icbcB2bo.setShop_acc_num("1611002319022123647");
//		icbcB2bo.setPayeeAcct("1611002709200086326");
		IcbcB2bQueryObject iqo = new IcbcB2bQueryObject();
		iqo.setIcbcB2bConfigObject(icbcB2bo);
		
		iqo.setOrderNum("YF1104131722201280");
		iqo.setTranDate(DateUtil.getDateFromString("20110413", DateUtil.SHORT_FORMAT_DAY));
		
		iqro.setQueryObject(iqo);
		iqro.process();
	}

	public static class IcbcB2bQueryObject implements QueryObject {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6324749391963138838L;

		/**
		 * 接口名称. MAX (30) 必输，签名，上送“EAPI”，区别大小写！
		 */
		private String APIName = "EAPI";

		/**
		 * 口版本号. =15 必输，签名,上送”001.001.001.001”
		 */
		private String APIVersion = "001.001.001.001";// B2B

		/**
		 * 请求数据,xml包（格式见下）
		 */
		private String MerReqData = "";

		private String orderNum;
		private Date tranDate;
		
		private IcbcB2bConfigObject icbcB2bConfigObject;

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
			in.setShopCode(icbcB2bConfigObject.getShop_code());
			in.setShopAccount(icbcB2bConfigObject.getShop_acc_num());
			ibcapi.setIn(in);

			return "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>"
					+ xstream.toXML(ibcapi).replaceAll("\n", "")
							.replaceAll("\r", "").replaceAll(" ", "");
		}

		public void setMerReqData(String merReqData) {
			MerReqData = merReqData;
		}

		public IcbcB2bConfigObject getIcbcB2bConfigObject() {
			return icbcB2bConfigObject;
		}

		public void setIcbcB2bConfigObject(
				IcbcB2bConfigObject icbcB2bConfigObject) {
			this.icbcB2bConfigObject = icbcB2bConfigObject;
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
		private String PayeeAcct;

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

		public String getPayeeAcct() {
			return PayeeAcct;
		}

		public void setPayeeAcct(String payeeAcct) {
			PayeeAcct = payeeAcct;
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
					+ ", PayeeAcct=" + PayeeAcct + ", PayeeName=" + PayeeName
					+ ", JoinFlag=" + JoinFlag + ", MerJoinFlag=" + MerJoinFlag
					+ ", CustJoinFlag=" + CustJoinFlag + ", CustJoinNum="
					+ CustJoinNum + ", CertID=" + CertID + "]";
		}

	}
}
