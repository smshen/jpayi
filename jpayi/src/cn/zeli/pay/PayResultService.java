/**
 * 
 */
package cn.zeli.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 银行反馈结果数据的分析及后续处理.
 * 非支付成功后的业务处理
 * @author Administrator
 *
 */
public interface PayResultService {

	/**
	 * 银行反馈接口数据验证失败时候，所做的处理.
	 * 如：固定页面的跳转，或返回固定字符串作为响应。
	 * @param request
	 * @param response
	 * @param pro
	 * @return
	 */
	public boolean doVerifyFail(HttpServletRequest request, HttpServletResponse response, PayResultObject pro);
	
	/**
	 * 接口数据验证成功所作处理。如返回固定字符串作为响应。
	 * @param request
	 * @param response
	 * @param pro
	 * @return
	 */
	public boolean doSuccess(HttpServletRequest request, HttpServletResponse response, PayResultObject pro);
	
	/**
	 * 接口数据验证失败所作处理。
	 * @param request
	 * @param response
	 * @param pro
	 * @return
	 */
	public boolean doFail(HttpServletRequest request, HttpServletResponse response, PayResultObject pro);
	
	/**
	 * 获取银行反馈接口提交过来的数据.
	 * 结合各个银行，结果对象个性化处理。
	 * @param request
	 * @return
	 */
	public PayResultObject payResultObject(HttpServletRequest request);
	
}
