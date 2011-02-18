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
	 * 交易金额.
	 * 注意：交易金额不一定等于订单金额。对于B2B支付，此费用通常指之际支付金额。此金额可能包括 订单金额+交易费用（手续费等）<br/>
	 * 扩展：目前更加银行返回支付金额为主。如果要更细化，则可以再划分具体金额。当前版本不实现。
	 * @return
	 */
	public String payAmount();
	
	/**
	 * 银行信息，可以包括银行基本信息和业务基本信息
	 * @return
	 */
	public String bankInfo();
}
