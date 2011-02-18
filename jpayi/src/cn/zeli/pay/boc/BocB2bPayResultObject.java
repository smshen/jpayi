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
public class BocB2bPayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4308763126485944891L;

	private String tranSeq;// 交易流水
	private String tranCode;// 交易类型
	private String orderSeq;// 订单流水
	private String bocNo;// 商户号
	private String orderNo;// 商户订单号
	private String curCode;// 币种
	private String orderAmount;// 订单金额
	private String feeAmount;// 交易费用
	private String tranTime;// 交易时间
	private String tranCur;// 交易币种
	private String tranAmount;// 交易金额
	private String tranStatus;// 交易状态：0:待处理/1:成功/2:失败/3:未明,银行处理中
	private String errMsg;// 失败原因
	private String signData;// 银行数字签名
	
	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		return "1".equals(tranStatus);
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		StringBuffer sb = new StringBuffer();
		sb.append(tranSeq).append("|")
		.append(tranCode).append("|")
		.append(tranStatus).append("|")
		.append(tranTime).append("|")
		.append(tranAmount).append("|")
		.append(feeAmount).append("|")
		.append(bocNo).append("|")
		.append(orderNo).append("|")
		.append(curCode);
		
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
		return tranAmount;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "BOC|B2B";
	}

	public String getTranSeq() {
		return tranSeq;
	}

	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getBocNo() {
		return bocNo;
	}

	public void setBocNo(String bocNo) {
		this.bocNo = bocNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getTranCur() {
		return tranCur;
	}

	public void setTranCur(String tranCur) {
		this.tranCur = tranCur;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

}
