/**
 * 
 */
package cn.zeli.pay.ccb;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;

/**
 * @author Administrator
 *
 */
public class CcbB2bPayObject extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 750637533840387096L;

	/**
	 * 商户代码 CHAR(21) 由建行统一分配
	 */
	@PayField(required = true, max = 21)
	private String MERCHANTID;

	/**
	 * 商户柜台代码 CHAR(9) 由建行统一分配，缺省为000000000
	 */
	@PayField(required = true, max = 9)
	private String POSID = "000000000";

	/**
	 * 分行代码 CHAR(9) 由建行统一指定
	 */
	@PayField(required = true, max = 9)
	private String BRANCHID;

	/**
	 * 定单号 CHAR(30) 由商户提供，最长30位,按实际长度给出
	 */
	@PayField(required = true, max = 30)
	private String ORDERID;

	/**
	 * 付款金额 NUMBER(16,2) 由商户提供，按实际金额给出
	 */
	@PayField(required = true, max = 16)
	private String PAYMENT;

	/**
	 * 币种 CHAR(2) 缺省为01－人民币（只支持人民币支付）
	 */
	@PayField(required = true, max = 2)
	private String CURCODE = "01";

	/**
	 * 备注1 CHAR(30) 网银不处理，直接传到城综网
	 */
	@PayField(max = 30)
	private String REMARK1;

	/**
	 * 备注2 CHAR(30) 网银不处理，直接传到城综网
	 */
	@PayField(max = 30)
	private String REMARK2;

	/**
	 * 交易码 CHAR(6) 由建行统一分配为690401
	 */
	@PayField(max = 6)
	private String TXCODE = "690401";

	/**
	 * MAC校验域 CHAR(32) 采用标准MD5算法，由商户实现
	 */
	@PayField(required = true, length = 32)
	private String MAC;

	public String sign() {

		// 字符串中变量名必须是大写字母
		StringBuffer sb = new StringBuffer();
		sb.append("MERCHANTID=").append(MERCHANTID).append("&POSID=")
				.append(POSID).append("&BRANCHID=").append(BRANCHID)
				.append("&ORDERID=").append(ORDERID).append("&PAYMENT=")
				.append(PAYMENT).append("&CURCODE=").append(CURCODE)
				.append("&TXCODE=").append(TXCODE).append("&REMARK1=")
				.append(REMARK1).append("&REMARK2=").append(REMARK2);

		return CcbUtil.getMAC(sb.toString());
	}

	public String getMERCHANTID() {
		return MERCHANTID;
	}

	public void setMERCHANTID(String mERCHANTID) {
		MERCHANTID = mERCHANTID;
	}

	public String getPOSID() {
		return POSID;
	}

	public void setPOSID(String pOSID) {
		POSID = pOSID;
	}

	public String getBRANCHID() {
		return BRANCHID;
	}

	public void setBRANCHID(String bRANCHID) {
		BRANCHID = bRANCHID;
	}

	public String getORDERID() {
		return ORDERID;
	}

	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}

	public String getPAYMENT() {
		return PAYMENT;
	}

	public void setPAYMENT(String pAYMENT) {
		PAYMENT = pAYMENT;
	}

	public String getCURCODE() {
		return CURCODE;
	}

	public void setCURCODE(String cURCODE) {
		CURCODE = cURCODE;
	}

	public String getREMARK1() {
		return REMARK1;
	}

	public void setREMARK1(String rEMARK1) {
		REMARK1 = rEMARK1;
	}

	public String getREMARK2() {
		return REMARK2;
	}

	public void setREMARK2(String rEMARK2) {
		REMARK2 = rEMARK2;
	}

	public String getTXCODE() {
		return TXCODE;
	}

	public void setTXCODE(String tXCODE) {
		TXCODE = tXCODE;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}
	
}
