/**
 * 
 */
package cn.zeli.pay.ccb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;

/**
 * @author Administrator
 *
 */
public class CcbB2bPagePayResultService implements PayResultService {

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public PayResultObject payResultObject(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
