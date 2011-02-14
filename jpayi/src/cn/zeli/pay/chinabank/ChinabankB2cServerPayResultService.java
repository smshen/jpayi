/**
 * 
 */
package cn.zeli.pay.chinabank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.PayResultObject;
import cn.zeli.pay.PayResultService;
import cn.zeli.util.HttpUtils;

/**
 * 网银服务器反馈接口.
 * 需要通知网银客服来设置该程序的URL地址。地址格式为：http://www.domain.com/xx/xx/chinabank/b2c/server。xx/xx/为NotifyServlet的path
 * @author Administrator
 *
 */
public class ChinabankB2cServerPayResultService implements PayResultService {

	private void writeString(HttpServletResponse response, String content) {
		try {
			PrintWriter out = response.getWriter();
			out.write("error");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doVerifyFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doVerifyFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		writeString(response, "error");
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doSuccess(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		writeString(response, "ok");
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultService#doFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, cn.zeli.pay.PayResultObject)
	 */
	public boolean doFail(HttpServletRequest request,
			HttpServletResponse response, PayResultObject pro) {
		writeString(response, "ok");
		return true;
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
