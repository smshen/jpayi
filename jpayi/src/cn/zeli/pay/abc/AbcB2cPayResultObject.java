/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.PayResultObject;

import com.hitrust.trustpay.client.TrxException;
import com.hitrust.trustpay.client.b2c.PaymentResult;

/**
 * 
 * 参考《农行网上支付平台-商户接口编程指南-Java_Editiion-V2.0.4.pdf》Page 40 <br/>
 * 
 * @author Administrator
 *
 */
public class AbcB2cPayResultObject extends PaymentResult implements
		PayResultObject {

	public AbcB2cPayResultObject(String aMessage) throws TrxException {
		super(aMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2692180525968246276L;

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "ABC|B2C";
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return getValue("OrderNo");
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public String payAmount() {
		return getValue("Amount");
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		return isSuccess();
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		// 由于农行已经封装过，因此，此验证直接返回true，只使用success();
		return true;
	}

}
