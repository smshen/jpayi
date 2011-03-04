/**
 * 
 */
package cn.zeli.pay.icbc;

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
public class IcbcB2bServerPayResultService implements PayResultService {

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doFail-------------->" + new Date());
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doSuccess-------------->" + new Date());
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doVerifyFail-------------->" + new Date());
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject(javax.servlet.http.HttpServletRequest)
	 */
	public PayResultObject payResultObject(HttpServletRequest request) {
		IcbcB2bPayResultObject o = new IcbcB2bPayResultObject();
		try {
			HttpUtils.bindBeanOnlyString(request, o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
