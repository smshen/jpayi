/**
 * 
 */
package cn.zeli.pay.icbc;

import cn.zeli.pay.AbstractQueryResultObject;
import cn.zeli.pay.QueryObject;

/**
 * icbc抽象实现部分功能
 * @author lz
 *
 */
public abstract class AbstractIcbcQueryResultObject extends
		AbstractQueryResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817539382216884026L;

	protected QueryObject queryObject;
	
	/**
	 * 工行返回错误信息分析。B2B、B2C以及C2C均一样，故抽象类完成
	 * @param content
	 * @return
	 */
	protected String getErrorMsg(String content) {
		int errorCode = 0;
		try {
			errorCode = Integer.parseInt(content);
		} catch (Exception e) {
			return content;
		}
		
		switch (errorCode) {
			case 40972:
				return "API查询的订单不存在";
			case 40973:
				return "API查询过程中系统异常";
			case 40976:
				return "API查询系统异常";
			case 40977:
				return "商户证书信息错";
			case 40978:
				return "解包商户请求数据报错";
			case 40979:
				return "查询的订单不存在";
			case 40980:
				return "API查询过程中系统异常";
			case 40981:
				return "给商户打包返回数据错";
			case 40982:
				return "系统错误";
			case 40983:
				return "查询的订单不唯一";
			case 40987:
				return "请求数据中接口名错误";
			case 40947:
				return "商户代码或者商城账号有误";
			case 40948:
				return "商城状态非法";
			case 40949:
				return "商城类别非法";
			case 40950:
				return "商城应用类别非法";
			case 40951:
				return "商户证书id状态非法";
			case 40952:
				return "商户证书id未绑定";
			case 40953:
				return "商户id权限非法";
			case 40954:
				return "检查商户状态时数据库异常";
			default:
				return content;
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

		@Override
		public String toString() {
			return "IN [orderNum=" + orderNum + ", tranDate=" + tranDate
					+ ", ShopCode=" + ShopCode + ", ShopAccount=" + ShopAccount
					+ "]";
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

		@Override
		public String toString() {
			return "PUB [APIName=" + APIName + ", APIVersion=" + APIVersion
					+ "]";
		}

	}

}
