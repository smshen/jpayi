/**
 * 
 */
package cn.zeli.pay.boc;

import java.io.IOException;
import java.security.GeneralSecurityException;

import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.util.FileUtil;

/**
 * @author Administrator
 *
 */
public class BocB2cPayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3633814861649789251L;

	private String merchantNo;
	private String orderNo;
	private String orderSeq;
	private String cardTyp;
	private String payTime;
	private String orderStatus;
	private String payAmount;
	private String orderIp;
	private String orderRefer;
	private String bankTranSeq;
	private String returnActFlag;
	private String signData;
	
	
	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		if ("1".equals(orderStatus)) {
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
//		merchantNo|orderNo|orderSeq|cardTyp|payTime|orderStatus|payAmount
		StringBuffer sb = new StringBuffer();
		sb.append(merchantNo).append("|").append(orderNo).append("|").append(orderSeq).append("|")
		.append(cardTyp).append("|").append(payTime).append("|").append(orderStatus).append("|").append(payAmount);
		
		try {
			PKCS7Tool tool = PKCS7Tool.getVerifier(FileUtil.getClasspath() + "/cert/boc/ROOTCA.cer");
			tool.verify(signData, sb.toString().getBytes(), null);// String dn：银行签名证书DN，如果为空则不验证DN
			return true;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return orderNo;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderAmount()
	 */
	public String payAmount() {
		return payAmount;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "BOC|B2C";
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getCardTyp() {
		return cardTyp;
	}

	public void setCardTyp(String cardTyp) {
		this.cardTyp = cardTyp;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderIp() {
		return orderIp;
	}

	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}

	public String getOrderRefer() {
		return orderRefer;
	}

	public void setOrderRefer(String orderRefer) {
		this.orderRefer = orderRefer;
	}

	public String getBankTranSeq() {
		return bankTranSeq;
	}

	public void setBankTranSeq(String bankTranSeq) {
		this.bankTranSeq = bankTranSeq;
	}

	public String getReturnActFlag() {
		return returnActFlag;
	}

	public void setReturnActFlag(String returnActFlag) {
		this.returnActFlag = returnActFlag;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

}
