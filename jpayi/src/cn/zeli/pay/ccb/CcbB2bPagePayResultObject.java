/**
 * 
 */
package cn.zeli.pay.ccb;

import cn.zeli.pay.AbstractPayResultObject;

/**
 * @author Administrator
 *
 */
public class CcbB2bPagePayResultObject extends AbstractPayResultObject {

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
		return "CCB|B2B";
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
	public String payAmount() {
		return PAYMENT;
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
		// TODO Auto-generated method stub
		return false;
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

}
