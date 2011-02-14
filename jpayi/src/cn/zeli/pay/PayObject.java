/**
 * 
 */
package cn.zeli.pay;

import java.io.Serializable;

/**
 * 支付对象
 * 
 * @author Administrator
 * 
 */
public interface PayObject extends Serializable {

	/**
	 * 验证对象值是否符合相应规则
	 * @return
	 */
	public ValidationMsg validation();
	
	/**
	 * 获取支付提交对象html代码
	 * @return
	 */
	public String generateFormInputsHtml();
}
