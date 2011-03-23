package cn.zeli.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.util.Constants;
import cn.zeli.util.PayConfig;
import cn.zeli.util.StringUtils;

/**
 * 接收网银支付通知的Servlet（页面通知）.<br/>
 * 此页面作为通用组件实现的时候，以servlet实现为示例，可以针对不同的应用修改此入口。<br/>
 * web.xml配置如下：
 * 
 * <pre>
 * <code>
 * &lt;servlet&gt;
 * 	&lt;description&gt;xxxx&lt;/description&gt;
 * 	&lt;display-name&gt;xxxxx&lt;/display-name&gt;
 * 	&lt;servlet-name&gt;<b>NotifyServlet</b>&lt;/servlet-name&gt;
 * 	&lt;servlet-class&gt;<b>xxx.package.xxx.NotifyServlet</b>&lt;/servlet-class&gt;
 * &lt;/servlet&gt;
 * &lt;servlet-mapping&gt;
 * 	&lt;servlet-name&gt;<b>NotifyServlet</b>&lt;/servlet-name&gt;
 * 	&lt;url-pattern&gt;<b>/pay/notify/*</b>&lt;/url-pattern&gt;
 * &lt;/servlet-mapping&gt;
 * </code>
 * </pre>
 * 
 * servlet中提供基本实现，但是PayResultService中可以重新实现， 基本实现包括：<br/>
 * 1.本servlet直接显示账单基本信息（最基本，仅包括OrderId，OrderAmount，支付status）；<br/>
 * 2.将最基本账单信息，输出到特定的页面，其中又包含两种方式：(a).forward，参数setAttribute传入; (b).redirect，带参数<br/>
 * 
 * 示例： 传入的url为： http://www.domainname.com/appName/pay/notify/<b>
 * <font color="red"=>/icbc</font>
 * <font color="green">/b2b</font>
 * <font color="blue">/page</font></b>。
 * 其中icbc为银行或者第三方支付名（如：icbc | abc | ccb | alipay | chinabank ）；
 * b2b为业务类型（如：b2b | b2c | c2c | 实时 | 担保 | more...）；
 * page为接收方法（基本分为page和server两种方式）。
 * 根据固定的规则，调用固定的类（当前包名 + icbc + IcbcB2bPayResultObject
 * ），即相对于当前包名下的子包名为某银行或第三方支付，类名为首字母大写银行名+首字母大写业务名（如果存在）+首字母大写的接收方法（如果存在）+PayResultObject。
 * 
 */
public class NotifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotifyServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * 地址确认及生成。对于url connection需要带协议路径。而其他则是应用绝对路径
	 * @param request
	 * @param path 带协议头的绝对路径，或应用绝对路径。
	 * @return
	 */
	private String getPath(HttpServletRequest request, String path) {
		path += "";
		if (path.toLowerCase().startsWith("http://") || path.toLowerCase().startsWith("https://")) {
			return path;
		}
		String s = request.getRequestURL().toString().replace(request.getRequestURI(), "") + request.getContextPath();
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		
		return s + path;
	}
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String pathInfo = request.getPathInfo();
			System.out.println(pathInfo);
			if (pathInfo.startsWith("/")) {
				pathInfo = pathInfo.substring(1);
			}
			
			String[] paths = null;
			// 不符合规则，forbidden
			if (null == (paths = pathInfo.split("/")) || paths.length < 1) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			String bank = StringUtils.firstUpper(paths[0]);
			String serviceClazzName = bank
					+ (paths.length > 1 ? StringUtils.firstUpper(paths[1]) : "")
					+ (paths.length > 2 ? StringUtils.firstUpper(paths[2]) : "");

			ClassLoader cl = this.getClass().getClassLoader();
			String packageName = this.getClass().getPackage().getName();

			PayResultService ps = (PayResultService) cl.loadClass(
					packageName + "." + paths[0] + "." + serviceClazzName + "PayResultService")
					.newInstance();

			// 获取支付结果对象，XXX 是否持久化存储 ？
			PayResultObject pro = ps.payResultObject(request);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(PayConfig.PAY_RESULT_DO_PARAM_ORDERID, pro.orderId());
			params.put(PayConfig.PAY_RESULT_DO_PARAM_ORDERAMOUNT, pro.payAmount());
			params.put(PayConfig.PAY_RESULT_DO_PARAM_BANK, pro.bankInfo());
			params.put(PayConfig.PAY_RESULT_DO_PARAM_CONTENT, pro.toString());// XXX 将pro对象转换成json或者序列化

			// 供下个页面获取，展示
			request.setAttribute(PayConfig.PAY_BASE_RESULT_ATTRIBUTE, params);

//			String successLogicUrl = getPath(request, PayConfig.PAY_RESULT_DO_SUCCESS_URL);
//			String failLogicUrl = getPath(request, PayConfig.PAY_RESULT_DO_FAIL_URL);
			
			if (pro.verify()) {// 验证是否成功
				
				if (pro.success()) {
//					HttpUtils.doPost(successLogicUrl, params, 60000, 60000);
					// 取消上面的http connection实现
					new PayResultLogicServiceInvoker().doSuccess(params);
					
					// 如果返回false，则继续采去默认方式进行
					if (!ps.doSuccess(request, response, pro)) {
						if ("".equals(PayConfig.PAY_SUCCESS_URL)) {
							defaultHtml(request, response, pro);
							return;
						}
//						request.getRequestDispatcher(getPath(request, PayConfig.PAY_SUCCESS_URL)).forward(request, response);
//						request.getRequestDispatcher(PayConfig.PAY_SUCCESS_URL).forward(request, response);

						post(response, PayConfig.PAY_SUCCESS_URL, params);
					}
					
				} else {
					// 支付失败
//					HttpUtils.doPost(failLogicUrl, params, 60000, 60000);// 取消http connection 实现
					new PayResultLogicServiceInvoker().doFail(params);

					// 如果返回false，则继续采去默认方式进行
					if (!ps.doFail(request, response, pro)) {
						if ("".equals(PayConfig.PAY_FAIL_URL)) {
							defaultHtml(request, response, pro);
							return;
						}
						request.getRequestDispatcher(PayConfig.PAY_FAIL_URL).forward(request, response);
					}
				}
				
			} else {
				// 验证失败
				// 是否也需要存入？如果需要，同“支付失败”部分

				// 如果返回false，则继续采去默认方式进行
				if (!ps.doVerifyFail(request, response, pro)) {
					if ("".equals(PayConfig.PAY_FAIL_URL)) {
						defaultHtml(request, response, pro);
						return;
					}
					request.getRequestDispatcher(PayConfig.PAY_FAIL_URL).forward(request, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
	}
	
	private void post(HttpServletResponse response, String url, Map<String, ?> map) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		PrintWriter out = response.getWriter();
		
		out.print("<html><body onLoad=\"javascript:document.xxxxxx.submit()\">");
		out.print("<form action=\"");
		out.print(url);
		out.print("\" method=\"post\" name=\"xxxxxx\">");
		
		StringBuffer buffer = new StringBuffer();
		
		
		for (String key : map.keySet()) {
			Object value = map.get(key);
//			if (value instanceof String) {
				buffer.append("<input type=\"hidden\" name=\"")
				.append(key).append("\" value=\"").append(value)
				.append("\">");
//			}
		}
		out.print(buffer.toString());
		out.print("</form></body></html>");
		out.flush();
		out.close();
	}
	
	private void defaultHtml(HttpServletRequest request, HttpServletResponse response, PayResultObject pro) {
		try {
			request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
			response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
			response.setContentType("text/html;charset=" + Constants.DEFAULT_ENCODING);

			// 页面失效
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			PrintWriter out = response.getWriter();
			
			String s =  new StringBuffer().append(
					"<html><head><title>支付结果</title></head><body>")
					.append("<h1>").append(pro.verify() ? (pro.success() ? "支付成功" : "支付失败") : "支付结果验证失败").append("</h1>")
					.append("<p>订单号：").append(pro.orderId()).append("</p>")
					.append("<p>交易金额：").append(pro.payAmount()).append("</p>")
					.append("<p>银行	：").append(pro.bankInfo()).append("</p>")
					.append("</body></html>")
					.toString();
			
			out.write(s);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
