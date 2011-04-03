/**
 * 
 */
package cn.zeli.pay.ccb;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;
import cn.zeli.util.HttpUtils;

/**
 * @author Administrator
 *
 */
public class CcbB2cPagePayResultService implements PayResultService {

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doFail-------------->" + new Date());
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doSuccess-------------->" + new Date());
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doVerifyFail-------------->" + new Date());
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public PayResultObject payResultObject(HttpServletRequest request) {
		CcbB2cPagePayResultObject o = new CcbB2cPagePayResultObject();
		try {
			HttpUtils.bindBeanOnlyString(request, o, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
