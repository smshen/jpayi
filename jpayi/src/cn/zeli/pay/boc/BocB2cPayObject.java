/**
 * 
 */
package cn.zeli.pay.boc;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.util.FileUtil;

/**
 * 中国银行b2c网银支付对象.
 * 参考文档：《B2C商户端接口说明(ver 2.3).doc》<br/>
 * 注意：传入的金额必须是#.##格式，且最大长度为13位；另外，orderTime必须为24小时制的yyyyMMddHHmmss
 * @author Administrator
 *
 */
public class BocB2cPayObject extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1139743862388200284L;

	/**
	 * 商户号.
	 * 必填 X(20)<br/>
	 * BOC商户ID
	 */
	@PayField(required = true, max = 20)
	private String merchantNo;

	/**
	 * 支付类型.
	 * 必填 X(10)<br/>
	 * 商户支付服务类型 1：网上购物; 2：基金直销
	 */
	@PayField(required = true, max = 10)
	private String peyType;

	/**
	 * 商户订单号.
	 * 必填 X(19)<br/>
	 * 商户系统产生的订单号
	 */
	@PayField(required = true, max = 19)
	private String orderNo;

	/**
	 * 订单币种.
	 * 必填 X(3)<br/>
	 * 目前只支持001：人民币. 固定填001
	 */
	@PayField(required = true, max = 3)
	private String curCode = "001";

	/**
	 * 订单金额.
	 * 必填 X(13)<br/>
	 * 格式：整数位不前补零,小数位补齐2位. 
	 * 即：不超过10位整数位+1位小数点+2位小数. 
	 * 无效格式如123，.10，1.1,有效格式如1.00，0.10
	 */
	@PayField(required = true, max = 13, regex = "^(0(.[0-9]{2}))|([1-9][0-9]{0,9}+(.[0-9]{2}))$")
	private String orderAmount;

	/**
	 * 订单时间.
	 * 必填 9(14)<br/>
	 * 格式：YYYYMMDD24HHMMSS 
	 * 其中时间为24小时格式，如下午3点15表示为151500
	 */
	@PayField(required = true, length = 14, regex = "^[1-9]{1}[0-9]{3}(0[1-9]{1}|1[0-2]{1})([0-2]{1}[0-9]{1}|3[0-1]{1})([0-1]{1}[0-9]{1}|2[0-3]{1})[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}$")
	private String orderTime;

	/**
	 * 订单说明.
	 * 必填 X(30)<br/>
	 * 订单描述
	 */
	@PayField(required = true, max = 30)
	private String orderNote;

	/**
	 * 商户接收通知URL.
	 * 必填 X(100)<br/>
	 * 客户支付完成后银行向商户发送支付结果，商户系统负责接收银行通知的URL
	 */
	@PayField(required = true, max = 100)
	private String orderUrl;

	/**
	 * 商户签名数据.
	 * 必填 X(4000)<br/>
	 * 商户签名数据串格式，各项数据用管道符分隔：
	 * 商户订单号|订单时间|订单币种|订单金额|商户号orderNo|orderTime|curCode|orderAmount|merchantNo
	 */
	@PayField(required = true, max = 4000)
	private String signData;


	public String sign() {
		StringBuffer sb = new StringBuffer();
		sb.append(orderNo).append("|")
			.append(orderTime).append("|")
			.append(curCode).append("|")
			.append(orderAmount).append("|")
			.append(merchantNo);
		
		try {
			PKCS7Tool tool = PKCS7Tool.getSigner(
					FileUtil.getClasspath() + "/cert/boc/keycerts_sit.jks", 
					"1111111a", "1111111a");
			return tool.sign(sb.toString().getBytes());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getPeyType() {
		return peyType;
	}

	public void setPeyType(String peyType) {
		this.peyType = peyType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getOrderUrl() {
		return orderUrl;
	}

	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}
	
//	public static void main(String[] args) {
//		System.out.println(check("32.32"));
//		System.out.println(check("0.32"));
//		System.out.println(check("00.32"));
//		System.out.println(check("100.32"));
//		System.out.println(check("01.32"));
//		System.out.println(check("32.2"));
//		System.out.println(check("32."));
//		System.out.println(check(".32"));
//		System.out.println(check("32"));
//		System.out.println(check("32121111111111111.23"));
//		System.out.println(check("1234567890.23"));
//		System.out.println(check("12345678901.23"));
//		System.out.println(check("123456789.23"));
//	}
//	static boolean check(String s) {
//		Pattern regex = Pattern.compile("^(0(.[0-9]{2}))|([1-9][0-9]{0,9}+(.[0-9]{2}))$");
//		Matcher matcher = regex.matcher(s);
//		boolean isMatched = matcher.matches();
//		if (isMatched){
//			return true;
//		} else {
//			return false;
//		}
//	}
	
}
