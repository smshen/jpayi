/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.PayResultObject;

import com.hitrust.b2b.trustpay.client.TrxException;
import com.hitrust.b2b.trustpay.client.b2b.TrnxResult;

/**
 * @author Administrator
 *
 */
public class AbcB2bPayResultObject extends TrnxResult implements
		PayResultObject {

	public AbcB2bPayResultObject(String aMessage) throws TrxException {
		super(aMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3146370496899624482L;

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "ABC|B2B";
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return getValue("MerchantTrnxNo");
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public float payAmount() {
		try {
			return Float.parseFloat(getValue("TrnxAMT"));
		} catch (NumberFormatException e) {
			return 0f;
		}
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		return getValue("TrnxStatus").equals(STATUS_SUCCESS);
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		// 由于农行已经封装过，使用isSuccess();
		return isSuccess();
	}

}
