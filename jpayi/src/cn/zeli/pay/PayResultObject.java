/**
 * 
 */
package cn.zeli.pay;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public interface PayResultObject extends Serializable {

	/**
	 * 检查支付状态
	 * @return
	 */
	public boolean success();
	
	/**
	 * 验证信息传输是否正确
	 * @return
	 */
	public boolean verify();
	
	/**
	 * 获得订单号
	 * @return
	 */
	public String orderId();
	
	/**
	 * 订单金额
	 * @return
	 */
	public String orderAmount();
	
	/**
	 * 银行信息，可以包括银行基本信息和业务基本信息
	 * @return
	 */
	public String bankInfo();
}
