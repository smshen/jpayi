/**
 * 
 */
package cn.zeli.pay.ccb;

import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.util.PayConfig;

/**
 * 建行参考网银B2B返回数据对象。参考《BtoB网上支付手册_200907(最新).doc》
 * @author lz
 *
 */
public class CcbB2bPagePayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1009573647073739295L;
	
//	private String POSID;
//
//	private String BRANCHID;
//	
//	private String ORDERID;
//	private String PAYMENT;
//	private String CURCODE;
//	private String REMARK1;
//	private String REMARK2;
//	private String SUCCESS;
//	private String SIGN;
//	private String ACC_TYPE;
	
	private String MPOSID;// 商户柜台代码 CHAR(9) 从商户传送的信息中获得
	private String ORDER_NUMBER;// 定单号 CHAR(30)
	private String CUST_ID;// 付款客户号 CHAR(20)
	private String ACC_NO;// 付款账号 CHAR(32) 从网银中心获得
	private String ACC_NAME;// 付款账户名称 CHAR(40) 从网银中心获得
	private String AMOUNT;// 付款金额 NUMBER(16,2) 从网银中心获得
	private String STATUS;// 支付结果 CHAR(1) 从网银中心获得
	private String REMARK1;// 备注一 CHAR(32)
	private String REMARK2;// 备注二 CHAR(32)
	private String TRAN_FLAG;// 付款方式 CHAR(1) 从网银中心获得 . 付款方式(TRAN_FLAG)含义  N：对公账户支付
	private String TRAN_TIME;// 交易时间 CHAR(12) 从网银中心获得
	private String BRANCH_NAME;// 付款分行名称 CHAR(40) 从网银中心获得
	private String SIGNSTRING;// 数字签名加密串 CHAR(256) 从网银中心获得
	private String CHECKOK;// 最后一级复核员是否审核通过 CHAR(1) 从网银中心获得   1：通过 0：不通过
	
	

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "CCB|B2B";
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return ORDER_NUMBER;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public float payAmount() {
		return toFloat(AMOUNT);
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 * 支付结果(STATUS)含义：
	 * 2：支付成功
	 * 3：支付成功
	 * 4：支付成功
	 * 5：交易失败
	 * 6：交易不确定
	 */
	public boolean success() {
		return ("2".equals(STATUS) || "3".equals(STATUS) || "4".equals(STATUS)) && "1".equals(CHECKOK);
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		StringBuffer sb = new StringBuffer();
		sb.append(MPOSID).append(ORDER_NUMBER).append(CUST_ID)
			.append(ACC_NO).append(ACC_NAME).append(AMOUNT)
			.append(STATUS).append(REMARK1).append(REMARK2).append(TRAN_FLAG)
			.append(TRAN_TIME).append(BRANCH_NAME);
		
		System.out.println("sb---" + sb.toString());
		System.out.println("SIGN---" + SIGNSTRING);
		CCBSign.RSASig rsa = new CCBSign.RSASig();
		rsa.setPublicKey(PayConfig.CCB_B2B_PUBLIC_KEY);
		return rsa.verifySigature(SIGNSTRING, sb.toString());
	}

	public String getMPOSID() {
		return MPOSID;
	}

	public void setMPOSID(String mPOSID) {
		MPOSID = mPOSID;
	}

	public String getORDER_NUMBER() {
		return ORDER_NUMBER;
	}

	public void setORDER_NUMBER(String oRDERNUMBER) {
		ORDER_NUMBER = oRDERNUMBER;
	}

	public String getCUST_ID() {
		return CUST_ID;
	}

	public void setCUST_ID(String cUSTID) {
		CUST_ID = cUSTID;
	}

	public String getACC_NO() {
		return ACC_NO;
	}

	public void setACC_NO(String aCCNO) {
		ACC_NO = aCCNO;
	}

	public String getACC_NAME() {
		return ACC_NAME;
	}

	public void setACC_NAME(String aCCNAME) {
		ACC_NAME = aCCNAME;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
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

	public String getTRAN_FLAG() {
		return TRAN_FLAG;
	}

	public void setTRAN_FLAG(String tRANFLAG) {
		TRAN_FLAG = tRANFLAG;
	}

	public String getTRAN_TIME() {
		return TRAN_TIME;
	}

	public void setTRAN_TIME(String tRANTIME) {
		TRAN_TIME = tRANTIME;
	}

	public String getBRANCH_NAME() {
		return BRANCH_NAME;
	}

	public void setBRANCH_NAME(String bRANCHNAME) {
		BRANCH_NAME = bRANCHNAME;
	}

	public String getSIGNSTRING() {
		return SIGNSTRING;
	}

	public void setSIGNSTRING(String sIGNSTRING) {
		SIGNSTRING = sIGNSTRING;
	}

	public String getCHECKOK() {
		return CHECKOK;
	}

	public void setCHECKOK(String cHECKOK) {
		CHECKOK = cHECKOK;
	}


}
