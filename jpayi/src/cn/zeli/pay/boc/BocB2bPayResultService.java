/**
 * 
 */
package cn.zeli.pay.boc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;
import cn.zeli.util.HttpUtils;

/**
 * 中国银行支付结果通知（银行直接返回）.<br/>
 * 参考文档：《B2C商户端接口说明(ver 2.3)》中“2.2.4 银行发送B2C支付结果通知”
 * 发送到chinabank支付网关的调用返回地址v_url设置为：http://www.domain.com/xx/xx/boc/b2b。xx/xx/为NotifyServlet的path
 * @author Administrator
 *
 */
public class BocB2bPayResultService implements PayResultService {

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject(javax.servlet.http.HttpServletRequest)
	 */
	public PayResultObject payResultObject(HttpServletRequest request) {
		BocB2bPayResultObject o = new BocB2bPayResultObject();
		try {
			HttpUtils.bindBeanOnlyString(request, o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
