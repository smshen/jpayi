/**
 * Copyright(c) 2011 zeli.cn. All rights reserved.
 * 2011-5-19 下午02:13:07
 */
package cn.zeli.pay.icbc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;
import cn.zeli.util.HttpUtils;

/**
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11ServerPayResultService implements PayResultService {

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doVerifyFail-----新接口V1.0.0.11验签失败！>_<--------->" + new Date());
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doSuccess-----新接口V1.0.0.11数据返回成功！^-^--------->" + new Date());
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	@Override
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		System.out.println("doFail-----新接口V1.0.0.11数据返回失败！>_<--------->" + new Date());
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#payResultObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public PayResultObject payResultObject(HttpServletRequest request) {

		IcbcB2cV1_0_0_11PayResultObject o = new IcbcB2cV1_0_0_11PayResultObject();
		try {
			HttpUtils.bindBeanOnlyString(request, o, "GBK");
			o.convertNotifyData();// 针对V1.0.0.11返回接口做的notifyData转换工作
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
