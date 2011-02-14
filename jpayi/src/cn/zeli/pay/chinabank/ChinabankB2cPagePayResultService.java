/**
 * 
 */
package cn.zeli.pay.chinabank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;
import cn.zeli.util.HttpUtils;

/**
 * 网银在线支付结果通知（页面返回）.<br/>
 * 发送到chinabank支付网关的调用返回地址v_url设置为：http://www.domain.com/xx/xx/chinabank/b2c/page。xx/xx/为NotifyServlet的path
 * @author Administrator
 *
 */
public class ChinabankB2cPagePayResultService implements PayResultService {

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {

		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {

		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject(javax.servlet.http.HttpServletRequest)
	 */
	public PayResultObject payResultObject(HttpServletRequest request) {

		ChinabankPayResultObject o = new ChinabankPayResultObject();
		try {
			HttpUtils.bindBeanOnlyString(request, o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
