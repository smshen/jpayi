/**
 * CopyRight(c) zeli.cn 2011
 * 2011-5-11 下午01:27:58
 */
package cn.zeli.pay.icbc;

import java.util.List;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.util.DateUtil;

/**
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11TranDataOrderInfo extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144453351127828103L;

	/**
	 * [交易日期时间] =14 必输，格式为：YYYYMMDDHHmmss
	 * 要求在银行系统当前时间的前1小时和后12小时范围内，否则判定交易时间非法。
	 */
	@PayField(required = true, length = 14, regex = "^[1-9]{1}[0-9]{3}(0[1-9]{1}|1[0-2]{1})([0-2]{1}[0-9]{1}|3[0-1]{1})([0-1]{1}[0-9]{1}|2[0-3]{1})[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}$")
	private String orderDate = DateUtil
			.getCurrentDate(DateUtil.SHORT_FORMAT_TIME);


	/**
	 * [支付币种] =3 必输，签名，用来区分一笔支付的币种，目前工行只支持使用人民币（001）支付。取值： “001”
	 */
	@PayField(required = true, length = 3)
	private String curType = "001";

	/**
	 * [商户代码] MAX(20) 必输，签名，唯一确定一个商户的代码，由商户在工行开户时，由工行告知商户。
	 */
	@PayField(required = true, max = 20)
	private String merID = "";
	
	/**
	 * 标签 subOrderInfoList
	 */
	private List<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo> subOrderInfoList;

	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the curType
	 */
	public String getCurType() {
		return curType;
	}

	/**
	 * @param curType the curType to set
	 */
	public void setCurType(String curType) {
		this.curType = curType;
	}

	/**
	 * @return the merID
	 */
	public String getMerID() {
		return merID;
	}

	/**
	 * @param merID the merID to set
	 */
	public void setMerID(String merID) {
		this.merID = merID;
	}

	/**
	 * @return the subOrderInfoList
	 */
	public List<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo> getSubOrderInfoList() {
		return subOrderInfoList;
	}

	/**
	 * @param subOrderInfoList the subOrderInfoList to set
	 */
	public void setSubOrderInfoList(
			List<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo> subOrderInfoList) {
		this.subOrderInfoList = subOrderInfoList;
	}
	
}
