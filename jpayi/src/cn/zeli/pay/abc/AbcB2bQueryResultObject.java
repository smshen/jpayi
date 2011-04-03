/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.AbstractQueryResultObject;
import cn.zeli.pay.QueryObject;

import com.hitrust.b2b.trustpay.client.TrxResponse;
import com.hitrust.b2b.trustpay.client.b2b.QueryTrnxRequest;

/**
 * @author lz
 * 
 */
public class AbcB2bQueryResultObject extends AbstractQueryResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3261679800930477869L;

	private TrxResponse tResponse = null;
	private AbcB2bQueryObject queryObject;

	public AbcB2bQueryResultObject() {
		// status
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.QueryResultObject#status()
	 */
	public String status() {
		return tResponse.getValue("TrnxStatus");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.QueryResultObject#statusRemark()
	 */
	public String statusRemark() {
		String status = tResponse.getValue("TrnxStatus");
		if (null == status) {
			return null;
		}
		if ("0000".equals(status)) {
			return "交易请求成功";
		}
		if ("0".equals(status)) {
			return "原始请求";
		}
		if ("1".equals(status)) {
			return "复核中";
		}
		if ("2".equals(status)) {
			return "交易成功";
		}
		if ("3".equals(status)) {
			return "交易失败";
		}
		if ("4".equals(status)) {
			return "交易被驳回";
		}
		if ("5".equals(status)) {
			return "交易作废";
		}
		if ("9".equals(status)) {
			return "交易无响应";
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "ABC|B2B";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return tResponse.getValue("MerchantTrnxNo");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public float payAmount() {
		try {
			return Float.parseFloat(tResponse.getValue("TrnxAMT"));
		} catch (Exception e) {
			return 0f;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		String status = tResponse.getValue("TrnxStatus");
		if ("2".equals(status)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		return true;
	}

	public void setQueryObject(QueryObject queryObject) {
		this.queryObject = (AbcB2bQueryObject) queryObject;
	}

	public Object getDetailObject() {
		return tResponse;
	}

	public void process() throws Exception {
		QueryTrnxRequest tQueryTrnxRequest = new QueryTrnxRequest();
		tQueryTrnxRequest.setMerchantTrnxNo(queryObject.getMerchantTrnxNo());// 设定商户交易编号
																				// （必要信息）
		tQueryTrnxRequest.setMerchantRemarks(queryObject.getMerchantRemarks());// 设定商户备注信息
		tResponse = tQueryTrnxRequest.postRequest();
		if (tResponse.isSuccess()) {
			// log success
		} else {
			throw new Exception("农行B2B商户订单查询失败：ReturnCode:["
					+ tResponse.getReturnCode() + "]; ErrorMessage:["
					+ tResponse.getErrorMessage() + "]");
		}
	}

	public static class AbcB2bQueryObject implements QueryObject {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2470303048560320073L;
		private String merchantTrnxNo;
		private String merchantRemarks = "Query for " + merchantTrnxNo;

		private AbcB2bConfigObject abcB2bConfigObject;
		
		public AbcB2bQueryObject() {
		}

		public AbcB2bQueryObject(String merchantTrnxNo, String merchantRemarks) {
			this.merchantTrnxNo = merchantTrnxNo;
			this.merchantRemarks = merchantRemarks;
		}

		public String getMerchantTrnxNo() {
			return merchantTrnxNo;
		}

		public void setMerchantTrnxNo(String merchantTrnxNo) {
			this.merchantTrnxNo = merchantTrnxNo;
		}

		public String getMerchantRemarks() {
			return merchantRemarks;
		}

		public void setMerchantRemarks(String merchantRemarks) {
			this.merchantRemarks = merchantRemarks;
		}

		public AbcB2bConfigObject getAbcB2bConfigObject() {
			return abcB2bConfigObject;
		}

		public void setAbcB2bConfigObject(AbcB2bConfigObject abcB2bConfigObject) {
			this.abcB2bConfigObject = abcB2bConfigObject;
		}

	}
}
