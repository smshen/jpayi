/**
 * CopyRight(c) zeli.cn 2011
 * 2011-5-11 下午01:29:29
 */
package cn.zeli.pay.icbc;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;

/**
 * tranData数据中的标签：message.
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11TranDataMessage extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1153547934047951342L;
	/**
	 * [支持订单支付的银行卡种类] = 1 必输
	 * 默认“2”。取值范围为0、1、2，其中0表示仅允许使用借记卡支付，1表示仅允许使用信用卡支付，2表示借记卡和信用卡都能对订单进行支付
	 */
	@PayField(required = true, length = 1)
	private String creditType = "2";
	
	
	/**
	 * [通知类型] =2
	 * 必输，在交易转账处理完成后把交易结果通知商户的处理模式。
	 * 取值“HS”：在交易完成后实时将通知信息以HTTP协议POST方式，主动发送给商户，发送地址为商户端随订单数据提交的接收工行支付结果的URL即表单中的merURL字段；
	 * 取值“AG”：在交易完成后不通知商户。商户需使用浏览器登录工行的B2C商户服务网站，或者使用工行提供的客户端程序API主动获取通知信息。
	 */
	@PayField(required = true, length = 2)
	private String notifyType = "HS";
	
	/**
	 * [结果发送类型] =1
	 * 选输，签名，取值“0”：无论支付成功或者失败，银行都向商户发送交易通知信息；取值“1”，银行只向商户发送交易成功的通知信息。
	 * 只有通知方式为HS时此值有效，如果使用AG方式，可不上送此项，但签名数据中必须包含此项，取值可为空。
	 */
	@PayField(required = false, length = 1)
	private String resultType = "0";
	
	/**
	 * [商户reference]  MAX(200) 选输，
	 * 上送商户网站域名，如果上送，工行会在客户支付订单时，
	 * 校验商户上送域名与客户跳转工行支付页面之前网站域名的一致性。
	 */
	@PayField(required = false, max = 200)
	private String merReference = "";
	
	/**
	 * [客户端IP] MAX(20) 选输，工行在支付页面显示该信息。
	 */
	@PayField(required = false, max = 20)
	private String merCustomIp = "";
	
	/**
	 * [虚拟商品/实物商品标志位] 必输取值“0”：虚拟商品；取值“1”，实物商品。
	 */
	@PayField(required = true, length = 1)
	private String goodsType = "1";// 默认取用实物商品
	
	/**
	 * [买家用户号]  MAX(100)  选输
	 */
	@PayField(required = false, max = 100)
	private String merCustomID = "";
	
	/**
	 * [买家联系电话] MAX(20) 选输，当商户上送goodsType值为“0”，该项必输
	 */
	@PayField(required = false, max = 20)
	private String merCustomPhone = "merCustomPhone";
	
	/**
	 * [收货地址] MAX(200) 选输，当商户上送goodsType值为“1”，该项必输
	 */
	@PayField(required = false, max = 200)
	private String goodsAddress = "收货地址";
	
	/**
	 * [订单备注] MAX(200) 选输，工行在支付页面显示该信息。
	 */
	@PayField(required = false, max = 200)
	private String merOrderRemark = "本支付采用防欺诈接口";

	/**
	 * [商城提示] MAX(120) 选输
	 */
	@PayField(required = false, max = 120)
	private String merHint = "";
	
	/**
	 * [备注字段1] MAX(100) 选输 单位：字节
	 */
	@PayField(required = false, max = 100)
	private String remark1 = "";

	/**
	 * [备注字段2] MAX(100) 选输 单位：字节
	 */
	@PayField(required = false, max = 100)
	private String remark2 = "";

	/**
	 * [返回商户URL] MAX(1024)
	 * 必输，
	 * 必须合法的URL，交易结束，将客户引导到商户的此url，
	 * 即通过客户浏览器post交易结果信息到商户的此URL
	 */
	@PayField(required = true, max = 1024)
	private String merURL = "";
	
	/**
	 * [返回商户变量] MAX(1024) 选输 
	 * 商户自定义，当返回银行结果时，作为一个隐藏域变量，
	 * 商户可以用此变量维护session等等。
	 * 由客户端浏览器支付完成后提交通知结果时是明文传输，
	 * 建议商户对此变量使用额外安全防范措施，如签名、base64
	 */
	private String merVAR = "";

	/**
	 * @return the creditType
	 */
	public String getCreditType() {
		return creditType;
	}

	/**
	 * @param creditType the creditType to set
	 */
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	/**
	 * @return the notifyType
	 */
	public String getNotifyType() {
		return notifyType;
	}

	/**
	 * @param notifyType the notifyType to set
	 */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	/**
	 * @return the resultType
	 */
	public String getResultType() {
		return resultType;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the merReference
	 */
	public String getMerReference() {
		return merReference;
	}

	/**
	 * @param merReference the merReference to set
	 */
	public void setMerReference(String merReference) {
		this.merReference = merReference;
	}

	/**
	 * @return the merCustomIp
	 */
	public String getMerCustomIp() {
		return merCustomIp;
	}

	/**
	 * @param merCustomIp the merCustomIp to set
	 */
	public void setMerCustomIp(String merCustomIp) {
		this.merCustomIp = merCustomIp;
	}

	/**
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return the merCustomID
	 */
	public String getMerCustomID() {
		return merCustomID;
	}

	/**
	 * @param merCustomID the merCustomID to set
	 */
	public void setMerCustomID(String merCustomID) {
		this.merCustomID = merCustomID;
	}

	/**
	 * @return the merCustomPhone
	 */
	public String getMerCustomPhone() {
		return merCustomPhone;
	}

	/**
	 * @param merCustomPhone the merCustomPhone to set
	 */
	public void setMerCustomPhone(String merCustomPhone) {
		this.merCustomPhone = merCustomPhone;
	}

	/**
	 * @return the goodsAddress
	 */
	public String getGoodsAddress() {
		return goodsAddress;
	}

	/**
	 * @param goodsAddress the goodsAddress to set
	 */
	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}

	/**
	 * @return the merOrderRemark
	 */
	public String getMerOrderRemark() {
		return merOrderRemark;
	}

	/**
	 * @param merOrderRemark the merOrderRemark to set
	 */
	public void setMerOrderRemark(String merOrderRemark) {
		this.merOrderRemark = merOrderRemark;
	}

	/**
	 * @return the merHint
	 */
	public String getMerHint() {
		return merHint;
	}

	/**
	 * @param merHint the merHint to set
	 */
	public void setMerHint(String merHint) {
		this.merHint = merHint;
	}

	/**
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}

	/**
	 * @param remark1 the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}

	/**
	 * @param remark2 the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * @return the merURL
	 */
	public String getMerURL() {
		return merURL;
	}

	/**
	 * @param merURL the merURL to set
	 */
	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}

	/**
	 * @return the merVAR
	 */
	public String getMerVAR() {
		return merVAR;
	}

	/**
	 * @param merVAR the merVAR to set
	 */
	public void setMerVAR(String merVAR) {
		this.merVAR = merVAR;
	}
	
	
}
