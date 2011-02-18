/**
 * 
 */
package cn.zeli.pay.boc;

import java.io.IOException;
import java.security.GeneralSecurityException;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.util.FileUtil;

/**
 * 中国银行B2B支付接口模型.
 * 
 * @author Administrator
 *
 */
public class BocB2bPayObject extends AbstractPayObject {

	/**
	 *  

02.	商户订单号
必填
商户系统产生的订单号
03.	订单币种
必填
04.	订单金额
必填
05.	订单时间
必填
06	订单说明
非必填
07.	通知商户URL
必填
　 


	 */
	private static final long serialVersionUID = -7568421195839277918L;

	/**
	 * 商户号
	 * 必填 varchar(20)
	 * 网关商户号
	 */
	@PayField(required = true, max = 20)
	private String bocNo;

	/**
	 * 商户订单号
	 * 必填 varchar(20)
	 * 商户系统产生的订单号；数字与26个英文字母以及中划线（-）和下划线（_）
	 */
	@PayField(required = true, max = 20)
	private String orderNo;

	/**
	 * 订单币种
	 * 必填 varchar(3)
	 * 目前只支持001-人民币。固定填001
	 */
	@PayField(required = true, max = 3)
	private String curCode = "001";

	/**
	 * 订单金额
	 * 必填 number(15,2)
	 * 订单总金额
	 * 格式：不超过12位整数位+1位小数点+2位小数; 无效格式如123，.10，1.131,有效格式如1.10，0.10
	 */
	@PayField(required = true, max = 15, regex = "^(0(.[0-9]{2}))|([1-9][0-9]{0,11}+(.[0-9]{2}))$")
	private String orderAmount;

	/**
	 * 订单时间
	 * 必填 varchar(14)
	 * 商户端生成的订单时间
	 * 格式：YYYYMMDD24HHMMSS; 其中时间为24小时格式，如下午3点15表示为151500
	 */
	@PayField(required = true, max = 14)
	private String orderTime;

	/**
	 * 订单说明
	 * 必填 varchar(30)
	 * 订单描述，要求如果全中文最多允许15个汉字长度
	 */
	@PayField(required = true, max = 15)
	private String orderNote;

	/**
	 * 通知商户URL
	 * 必填 varchar(200)
	 * 网关完成交易获得明确交易状态后向该URL发送通知
	 */
	@PayField(required = true, max = 200)
	private String orderUrl;

	/**
	 * 数字签名	必填
	 * 商户签名数据串格式，各项数据用管道符分隔： 
	 * 商户号|商户订单号|订单币种|订单金额|订单时间bocNo|orderNo|curCode|orderAmount|orderTime
	 */
	@PayField(required = true)
	private String signData;
	

	public String sign() {
		StringBuffer sb = new StringBuffer();
		sb.append(bocNo).append("|").append(orderNo).append("|")
		.append(curCode).append("|").append(orderAmount).append("|").append(orderTime);
		
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


	public String getBocNo() {
		return bocNo;
	}


	public void setBocNo(String bocNo) {
		this.bocNo = bocNo;
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
	
	
	
}
