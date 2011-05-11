/**
 * CopyRight(c) zeli.cn 2011
 * 2011-5-11 下午01:28:24
 */
package cn.zeli.pay.icbc;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;

/**
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216753822904822385L;

	/**
	 * [订单号] MAX(30)
	 * 必输，客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。
	 * 工行通过订单号加订单日期来唯一确认一笔订单的重复性。
	 */
	@PayField(required = true, max = 30)
	private String orderid = "";
	
	/**
	 * [订单金额] MAX(10) 必输，客户支付订单的总金额，一笔订单一个，以分为单位。
	 * 不可以为零，必需符合金额标准。
	 */
	@PayField(required = true, max = 10)
	private String amount = "";
	
	/**
	 * [分期付款期数] MAX（2） 必输
	 * 每笔订单一个；取值：1、3、6、9、12、18、24；1代表全额付款，必须为以上数值，否则订单校验不通过。
	 */
	@PayField(required = true, max = 2)
	private String installmentTimes = "";

	/**
	 * [商城账号] MAX(19) 必输，
	 * 每笔订单一个，可以相同；
	 * 商户入账账号，只能交易时指定。
	 * （商户付给银行手续费的账户，可以在开户的时候指定，也可以用交易指定方式；
	 * 用交易指定方式则使用此商户账号）
	 */
	@PayField(required = true, max = 19)
	private String merAcct = "";
	

	/**
	 * [商品编号] MAX(30) 选输 每笔订单一个；
	 */
	@PayField(required = false, max = 30)
	private String goodsID = "";

	/**
	 * [商品名称] MAX(60) 必输 每笔订单一个；
	 */
	@PayField(required = true, max = 60)
	private String goodsName = "";

	/**
	 * [商品数量] MAX(10) 选输 每笔订单一个；
	 */
	@PayField(required = false, max = 10)
	private String goodsNum = "";

	/**
	 * [已含运费金额] MAX(10) 选输 每笔订单一个；
	 */
	@PayField(required = false, max = 10)
	private String carriageAmt = "";

	/**
	 * @return the orderid
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the installmentTimes
	 */
	public String getInstallmentTimes() {
		return installmentTimes;
	}

	/**
	 * @param installmentTimes the installmentTimes to set
	 */
	public void setInstallmentTimes(String installmentTimes) {
		this.installmentTimes = installmentTimes;
	}

	/**
	 * @return the merAcct
	 */
	public String getMerAcct() {
		return merAcct;
	}

	/**
	 * @param merAcct the merAcct to set
	 */
	public void setMerAcct(String merAcct) {
		this.merAcct = merAcct;
	}

	/**
	 * @return the goodsID
	 */
	public String getGoodsID() {
		return goodsID;
	}

	/**
	 * @param goodsID the goodsID to set
	 */
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsNum
	 */
	public String getGoodsNum() {
		return goodsNum;
	}

	/**
	 * @param goodsNum the goodsNum to set
	 */
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	/**
	 * @return the carriageAmt
	 */
	public String getCarriageAmt() {
		return carriageAmt;
	}

	/**
	 * @param carriageAmt the carriageAmt to set
	 */
	public void setCarriageAmt(String carriageAmt) {
		this.carriageAmt = carriageAmt;
	}
	
	
}
