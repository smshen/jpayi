/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.PayObject;
import cn.zeli.pay.ValidationMsg;
import cn.zeli.pay.ValidationMsg.Msg;

import com.hitrust.trustpay.client.TrxException;
import com.hitrust.trustpay.client.b2c.PaymentRequest;

/**
 * @deprecated 由于农行做过该支付的封装，因此不需要单独处理，直接在支付入口去区分即可
 * 农行B2C支付请求对象.
 * 参考文档：《农行网上支付平台-商户接口编程指南-Java_Edition-V2.0.4.pdf》
 * @author Administrator
 *
 */
public class AbcB2cPayObject extends PaymentRequest implements PayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 501290597072263589L;

	@Override
	public ValidationMsg validation() {
		ValidationMsg msg = new ValidationMsg(true);
		try {
			checkRequest();
			return msg;
		} catch (TrxException e) {

			msg.setSuccess(false);
			msg.addList(new Msg(e.getCode(), Msg.ERROR_CUSTOM,
					e.getDetailMessage()));
			return msg;
		}
	}

	@Override
	public String generateFormInputsHtml() {
		return null;
	}

	
	
	
	
}
