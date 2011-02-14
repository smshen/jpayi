/**
 * 
 */
package cn.zeli.pay.icbc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;
import cn.zeli.util.HttpUtils;

/**
 * @author Administrator
 *
 */
public class IcbcB2bPayResultService implements PayResultService {

	

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject()
	 */
	@Override
	public PayResultObject payResultObject(HttpServletRequest request) {
		IcbcB2bPayResultObject o = new IcbcB2bPayResultObject();
		try {
			HttpUtils.bindBeanOnlyString(request, o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		// 验证失败，不做处理，则返回true。XXX 具体调试时看是否为异步处理，若是，则此返回值即可。否则，要返回相关页面。
		return true;
	}

	@Override
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		// 疑问同doVerifyFail部分
		return true;
	}

	@Override
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		// 疑问同doVerifyFail部分
		return true;
	}

}
