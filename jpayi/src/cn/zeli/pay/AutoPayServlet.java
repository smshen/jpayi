package cn.zeli.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zeli.pay.ValidationMsg.Msg;

/**
 * 自动生成对应支付表单，并且自动进行提交操作.<br/>
 * 当需要进一步确认提交支付的金额内容时，可扩展使用jsp页面展现，代替此servlet。
 * Servlet implementation class AutoPayServlet
 */
public class AutoPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String characterEncoding = "UTF-8";
	
	private String formAction;
	
	private String formMethod = "POST";
	
	private static final String _E_FORM = "E_FORM";

	public static final String PARAM_NAME_FORWARD_URL = "forwardName";
	public static final String PARAM_NAME_FORWARD_TYPE = "forwardType";
    
	public static final String ATTR_NAME_PAY_OBJECT = "payObject";
	public static final String ATTR_NAME_FROM_ACTION = "formAction";
	public static final String ATTR_NAME_FROM_METHOD = "formMethod";
	public static final String ATTR_NAME_CHARACTER_ENCODING = "characterEncoding";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoPayServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getAttribute(request);
		
		// 编码
		request.setCharacterEncoding(characterEncoding);
		response.setCharacterEncoding(characterEncoding);
		response.setContentType("text/html;charset=" + characterEncoding);
		
		PrintWriter out = response.getWriter();
		
		PayObject po = (PayObject) request.getAttribute(ATTR_NAME_PAY_OBJECT);
		
		if (null == po) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "错误支付对象");
			return;
		}
		
		// 验证提交支付字段内容是否正确
		ValidationMsg vm = po.validation();
		if (!vm.isSuccess()) {
			List<Msg> msgs = vm.getList();
			
			// 直接返回错误信息 ，注：转 到其他页面（可加入样式或结合其他view等）
			String forwardUrl = request.getParameter(PARAM_NAME_FORWARD_URL);// url
			String forwardType = request.getParameter(PARAM_NAME_FORWARD_TYPE);// forward | redirect
			// 补充，可attribute置入
			if (null == forwardUrl)
				forwardUrl = (String) request.getAttribute(PARAM_NAME_FORWARD_URL);
			if (null == forwardType)
				forwardType = (String) request.getAttribute(PARAM_NAME_FORWARD_TYPE);
			
			if (null != forwardUrl && !"".equals(forwardUrl.trim())) {
				if (null != forwardType && "redirect".equalsIgnoreCase(forwardType)) {
					response.sendRedirect(getRedirectUrl(forwardUrl, msgs));
				} else {
					request.setAttribute("msgs", msgs);
					request.getRequestDispatcher(forwardUrl).forward(request, response);
				}
			} else {
				out.print("<html><body>");
				for (Msg msg : msgs) {
					out.print(msg.getMsg());
					out.print("<br/>");
				}
				out.print("</body></html>");
				out.flush();
				out.close();
			}
			return;
		}
		
		// 页面失效
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		out.print("<html><body onLoad=\"javascript:document." + _E_FORM + ".submit()\">");
		out.print("<form action=\"");
		out.print(formAction);
		out.print("\" method=\"");
		out.print(formMethod);
		out.print("\" name=\"" + _E_FORM + "\">");
		
		// input (hidden)
		out.print(po.generateFormInputsHtml());
		
		out.print("</form></body></html>");
		out.flush();
		out.close();
	}
	
	private void getAttribute(HttpServletRequest request) {
		String attrCharacterEncoding = (String) request.getAttribute(ATTR_NAME_CHARACTER_ENCODING);
		if (null != attrCharacterEncoding && !"".equals(attrCharacterEncoding.trim())) {
			this.characterEncoding = attrCharacterEncoding;
		}

		String attrFormMethod = (String) request.getAttribute(ATTR_NAME_FROM_METHOD);
		if (null != attrFormMethod && !"".equals(attrFormMethod.trim())) {
			this.formMethod = attrFormMethod;
		}

		String attrFormAction = (String) request.getAttribute(ATTR_NAME_FROM_ACTION);
		if (null != attrFormAction && !"".equals(attrFormAction.trim())) {
			this.formAction = attrFormAction;
		}
		
		
	}
	
	private String getRedirectUrl(String url, List<Msg> msgs) {
		StringBuffer sb = new StringBuffer(url);
		int idx = 0;
		if ((idx = sb.indexOf("?")) > 0) {
			if (idx < sb.length() - 1) {
				sb.append("&");
			}
		} else {
			sb.append("?");
		}
		sb.append("msgs=");
		for (Msg m : msgs) {
			sb.append(m).append("<br/>");
		}
		
		return sb.toString();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
	
	

}
