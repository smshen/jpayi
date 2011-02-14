/**
 * 
 */
package cn.zeli.pay.chinabank;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.util.EncodeUtil;

/**
 * 网银在线支付B2C支付数据. 参考文档：《网银在线支付B2C系统商户接口文档.doc》，4.0版
 * 
 * @author Administrator
 * 
 */
public class ChinabankPayObject extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3441268329419371852L;

	/**
	 * 商户编号 不可为空，以初始单所填商户编号为准
	 */
	@PayField(required = true, max = 20)
	private String v_mid;

	/**
	 * 参数key是商户的MD5密钥（该密匙可在登陆商户管理界面后自行更改。） 不进行表单提交
	 */
	@PayField(required = true, submit = false)
	private String key;

	/**
	 * 订单号 不可为空值(64)，订单编号标准格式为：订单生成日期(yyyymmdd)-商户编号-商户流水号。订单编号所有字符总和不可超过64位。<br/>
	 * 举例：19990720-1001-12345。商户流水号为数字，订单号当日内不可重复
	 */
	@PayField(required = true, max = 64)
	private String v_oid;

	/**
	 * 订单总金额（元） 不可为空(8)，单位：元，小数点后保留两位。如：0.01
	 */
	@PayField(required = true, max = 8)
	private String v_amount;

	/**
	 * 币种 必填项，CNY为人民币。默认CNY
	 */
	@PayField(required = true, max = 10)
	private String v_moneytype = "CNY";

	/**
	 * URL地址 200 消费者完成购物后返回的商户页面，URL参数是以http://开头的完整URL地址。
	 * 支付动作完成后返回到该url，支付结果以POST方式发送
	 */
	@PayField(required = true, max = 200)
	private String v_url;

	/**
	 * 订单MD5校验码
	 */
	@PayField(required = true, length = 32)
	private String v_md5info;

	/**
	 * 收货人姓名 选填项，总长不超过80字符
	 */
	@PayField(required = false, max = 80)
	private String v_rcvname;

	/**
	 * 收货人地址 选填项，总长不超过200个字符
	 */
	@PayField(required = false, max = 200)
	private String v_rcvaddr;

	/**
	 * 收货人电话 选填项，总长不超过50个字符
	 */
	@PayField(required = false, max = 50)
	private String v_rcvtel;

	/**
	 * 收货人邮编 选填项，总长不超过10个字符
	 */
	@PayField(required = false, max = 10)
	private String v_rcvpost;

	/**
	 * 收货人邮件 选填项，总长不超过100个字符
	 */
	@PayField(required = false, max = 100)
	private String v_rcvemail;

	/**
	 * 收货人手机号 选填项，总长不超过13个字符
	 */
	@PayField(required = false, max = 13)
	private String v_rcvmobile;

	/**
	 * 订货人姓名 选填项，总长不超过80字符
	 */
	@PayField(required = false, max = 80)
	private String v_ordername;

	/**
	 * 订货人地址 选填项，总长不超过200个字符
	 */
	@PayField(required = false, max = 200)
	private String v_orderaddr;

	/**
	 * 订货人电话 选填项，总长不超过50个字符
	 */
	@PayField(required = false, max = 50)
	private String v_ordertel;

	/**
	 * 订货人邮编 选填项，总长不超过10个字符
	 */
	@PayField(required = false, max = 10)
	private String v_orderpost;

	/**
	 * 订货人邮件 选填项，总长不超过100个字符
	 */
	@PayField(required = false, max = 100)
	private String v_orderemail;

	/**
	 * 订货人手机号 选填项，总长不超过13个字符
	 */
	@PayField(required = false, max = 13)
	private String v_ordermobile;

	/**
	 * 备注1 选填项，总长不超过150个字符
	 */
	@PayField(required = false, max = 150)
	private String remark1;

	/**
	 * 备注2 选填项，总长不超过150个字符
	 */
	@PayField(required = false, max = 150)
	private String remark2;

	/**
	 * MD5校验串生成 当消费者在商户端生成最终订单的时候，将订单中的v_amount v_moneytype v_oid v_mid v_url
	 * key六个参数的value值拼成一个无间隔的字符串(顺序不要改变)。参数key是商户的MD5密钥（该密匙可在登陆商户管理界面后自行更改。）
	 * 
	 * @return
	 */
	public String sign() {

		return EncodeUtil.MD5Encode(new StringBuffer().append(v_amount)
				.append(v_moneytype).append(v_oid).append(v_mid).append(v_url).append(key)
				.toString()).toUpperCase();
	}

	public String getV_mid() {
		return v_mid;
	}

	public void setV_mid(String v_mid) {
		this.v_mid = v_mid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getV_oid() {
		return v_oid;
	}

	public void setV_oid(String v_oid) {
		this.v_oid = v_oid;
	}

	public String getV_amount() {
		return v_amount;
	}

	public void setV_amount(String v_amount) {
		this.v_amount = v_amount;
	}

	public String getV_moneytype() {
		return v_moneytype;
	}

	public void setV_moneytype(String v_moneytype) {
		this.v_moneytype = v_moneytype;
	}

	public String getV_url() {
		return v_url;
	}

	public void setV_url(String v_url) {
		this.v_url = v_url;
	}

	public String getV_md5info() {
		return v_md5info;
	}

	public void setV_md5info(String v_md5info) {
		this.v_md5info = v_md5info;
	}

	public String getV_rcvname() {
		return v_rcvname;
	}

	public void setV_rcvname(String v_rcvname) {
		this.v_rcvname = v_rcvname;
	}

	public String getV_rcvaddr() {
		return v_rcvaddr;
	}

	public void setV_rcvaddr(String v_rcvaddr) {
		this.v_rcvaddr = v_rcvaddr;
	}

	public String getV_rcvtel() {
		return v_rcvtel;
	}

	public void setV_rcvtel(String v_rcvtel) {
		this.v_rcvtel = v_rcvtel;
	}

	public String getV_rcvpost() {
		return v_rcvpost;
	}

	public void setV_rcvpost(String v_rcvpost) {
		this.v_rcvpost = v_rcvpost;
	}

	public String getV_rcvemail() {
		return v_rcvemail;
	}

	public void setV_rcvemail(String v_rcvemail) {
		this.v_rcvemail = v_rcvemail;
	}

	public String getV_rcvmobile() {
		return v_rcvmobile;
	}

	public void setV_rcvmobile(String v_rcvmobile) {
		this.v_rcvmobile = v_rcvmobile;
	}

	public String getV_ordername() {
		return v_ordername;
	}

	public void setV_ordername(String v_ordername) {
		this.v_ordername = v_ordername;
	}

	public String getV_orderaddr() {
		return v_orderaddr;
	}

	public void setV_orderaddr(String v_orderaddr) {
		this.v_orderaddr = v_orderaddr;
	}

	public String getV_ordertel() {
		return v_ordertel;
	}

	public void setV_ordertel(String v_ordertel) {
		this.v_ordertel = v_ordertel;
	}

	public String getV_orderpost() {
		return v_orderpost;
	}

	public void setV_orderpost(String v_orderpost) {
		this.v_orderpost = v_orderpost;
	}

	public String getV_orderemail() {
		return v_orderemail;
	}

	public void setV_orderemail(String v_orderemail) {
		this.v_orderemail = v_orderemail;
	}

	public String getV_ordermobile() {
		return v_ordermobile;
	}

	public void setV_ordermobile(String v_ordermobile) {
		this.v_ordermobile = v_ordermobile;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
}
