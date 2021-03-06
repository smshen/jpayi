/**
 * 
 */
package cn.zeli.pay.ccb;

import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.util.PayConfig;

/**
 * @author Administrator
 *
 */
public class CcbB2cPagePayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1009573647073739295L;
	
	private String POSID;

	private String BRANCHID;
	
	private String ORDERID;
	private String PAYMENT;
	private String CURCODE;
	private String REMARK1;
	private String REMARK2;
	private String SUCCESS;
	private String SIGN;
//	private String ACC_TYPE;

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	@Override
	public String bankInfo() {
		return "CCB|B2C";
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	@Override
	public String orderId() {
		return ORDERID;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	@Override
	public float payAmount() {
		return toFloat(PAYMENT);
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	@Override
	public boolean success() {
		return "Y".equals(SUCCESS);
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	@Override
	public boolean verify() {
		StringBuffer sb = new StringBuffer();
		sb.append("POSID=").append(POSID).append("&")
		.append("BRANCHID=").append(BRANCHID).append("&")
		.append("ORDERID=").append(ORDERID).append("&")
		.append("PAYMENT=").append(PAYMENT).append("&")
		.append("CURCODE=").append(CURCODE).append("&")
		.append("REMARK1=").append(REMARK1).append("&")
		.append("REMARK2=").append(REMARK2).append("&")
		.append("SUCCESS=").append(SUCCESS);
		
		System.out.println(sb.toString());
		CCBSign.RSASig rsa = new CCBSign.RSASig();

		rsa.setPublicKey(PayConfig.CCB_B2C_PUBLIC_KEY);
		System.out.println("-->" + PayConfig.CCB_B2C_PUBLIC_KEY);
		System.out.println(SIGN);
		System.out.println(rsa.verifySigature(SIGN, sb.toString()));
		return rsa.verifySigature(SIGN, sb.toString());
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

	public String getSUCCESS() {
		return SUCCESS;
	}

	public void setSUCCESS(String sUCCESS) {
		SUCCESS = sUCCESS;
	}

	public String getSIGN() {
		return SIGN;
	}

	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}

	@Override
	public String toString() {
		return "CcbB2cPagePayResultObject [BRANCHID=" + BRANCHID + ", CURCODE="
				+ CURCODE + ", ORDERID=" + ORDERID + ", PAYMENT=" + PAYMENT
				+ ", POSID=" + POSID + ", REMARK1=" + REMARK1 + ", REMARK2="
				+ REMARK2 + ", SIGN=" + SIGN + ", SUCCESS=" + SUCCESS + "]";
	}

}
