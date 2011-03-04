/**
 * 
 */
package cn.zeli.pay.icbc;

import cn.com.infosec.icbc.ReturnValue;
import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.util.DateUtil;
import cn.zeli.util.FileUtil;

/**
 * 工商银行B2C网银支付对象. 参考文档：《中国工商银行网上银行B2C在线支付接口说明.doc》<br/>
 * // 取最新版本1.0.0.11。参考文档《中国工商银行网上银行新B2C在线支付接口说明V1.0.0.11.doc》
 * @author Administrator
 * 
 */
public class IcbcB2cPayObject extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5679273714089678308L;

	/**
	 * [接口名称] MAX(30) 必输，签名，取值：“ICBC_PERBANK_B2C”
	 */
	@PayField(required = true, max = 30)
	private String interfaceName = "ICBC_PERBANK_B2C";

	/**
	 * [接口版本号] MAX(15) 必输，签名，取值：“1.0.0.0”
	 */
	@PayField(required = true, max = 15)
	private String interfaceVersion = "1.0.0.0";

//	/**
//	 * 交易数据.
//	 * 从版本1.0.0.11上增加
//	 * 
//	 */
//	@PayField(required = true)
//	private String tranData;
//	
	/**
	 * [订单号] MAX(30)
	 * 必输，签名，客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。工行通过订单号加订单日期来唯一确认一笔订单的重复性。
	 */
	@PayField(required = true, max = 30)
	private String orderid;

	/**
	 * [订单金额] MAX(10) 必输，签名，客户支付订单的总金额，一笔订单一个，以分为单位。不可以为零，必需符合金额标准。
	 */
	@PayField(required = true, max = 10)
	private String amount;

	/**
	 * [支付币种] =3 必输，签名，用来区分一笔支付的币种，目前工行只支持使用人民币（001）支付。取值： “001”
	 */
	@PayField(required = true, length = 3)
	private String curType = "001";

	/**
	 * [商户代码] MAX(20) 必输，签名，唯一确定一个商户的代码，由商户在工行开户时，由工行告知商户。
	 */
	@PayField(required = true, max = 20)
	private String merID;

	/**
	 * [商城账号] MAX(19) 必输，签名，商城收费入账账号 （只能交易时指定）。
	 */
	@PayField(required = true, max = 19)
	private String merAcct;

	/**
	 * [检验联名标志] =1
	 * 必输，签名，取值“1”：客户支付时，网银判断该客户是否与商户联名，是则按上送金额扣帐，否则展现未联名错误；取值“0”：不检验客户是否与商户联名
	 * ，按上送金额扣帐。
	 */
	@PayField(required = true, length = 1)
	private String verifyJoinFlag;

	/**
	 * [通知类型] =2
	 * 必输，签名，在交易转账处理完成后把交易结果通知商户的处理模式。取值“HS”：在交易完成后实时将通知信息以HTTP协议POST方式
	 * ，主动发送给商户，发送地址为商户端随订单数据提交的接收工行支付结果的URL即表单中的merURL字段
	 * ；取值“AG”：在交易完成后不通知商户。商户需使用浏览器登录工行的B2C商户服务网站，或者使用工行提供的客户端程序API主动获取通知信息。
	 */
	@PayField(required = true, length = 2)
	private String notifyType;

	/**
	 * [接收支付结果信息通知程序地址] MAX(200)
	 * 选输，签名，使用HS通知类型的商户用来接收工行订单支付结果的URL；银行使用HTTP协议POST方式向此地址发送通知信息
	 * ；目前只支持80端口。使用“
	 * AG”通知类型的商户,该字段可以为空或者不上送该字段；但在签名数据中必须包含此项，取值可为空。取值举例：http://www
	 * .mer.com/getICBCPayResult.jsp
	 */
	@PayField(required = false, max = 200)
	private String merURL;

	/**
	 * [结果发送类型] =1
	 * 选输，签名，取值“0”：无论支付成功或者失败，银行都向商户发送交易通知信息；取值“1”，银行只向商户发送交易成功的通知信息。
	 * 只有通知方式为HS时此值有效，如果使用AG方式，可不上送此项，但签名数据中必须包含此项，取值可为空。
	 */
	@PayField(required = false, length = 1)
	private String resultType;

	/**
	 * [商品编号] MAX(30) 选输
	 */
	@PayField(required = false, max = 30)
	private String goodsID;

	/**
	 * [商品名称] MAX(60) 选输
	 */
	@PayField(required = false, max = 60)
	private String goodsName;

	/**
	 * [商品数量] MAX(10) 选输
	 */
	@PayField(required = false, max = 10)
	private String goodsNum;

	/**
	 * [已含运费金额] MAX(10) 选输
	 */
	@PayField(required = false, max = 10)
	private String carriageAmt;

	/**
	 * [商城提示] MAX(120) 选输
	 */
	@PayField(required = false, max = 120)
	private String merHint;

	/**
	 * [交易日期时间] =14 必输，签名，格式为：YYYYMMDDHHmmss
	 * 要求在银行系统当前时间的前1小时和后12小时范围内，否则判定交易时间非法。
	 */
	@PayField(required = true, length = 14, regex = "^[1-9]{1}[0-9]{3}(0[1-9]{1}|1[0-2]{1})([0-2]{1}[0-9]{1}|3[0-1]{1})([0-1]{1}[0-9]{1}|2[0-3]{1})[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}$")
	private String orderDate = DateUtil
			.getCurrentDate(DateUtil.SHORT_FORMAT_TIME);;

	/**
	 * [订单签名数据] 无限制 必输
	 * 商户使用工行提供的签名API接口和商户证书将交易数据按一定格式进行签名，然后进行BASE64编码后得到的字符串。（格式单独说明）
	 */
	@PayField(required = true)
	private String merSignMsg = sign();

	/**
	 * [商城证书公钥] 无限制 必输 商户用二进制方式读取证书公钥文件后，进行BASE64编码后产生的字符串。
	 */
	@PayField(required = true)
	private String merCert = cert();

	/**
	 * [备注字段1] MAX(100) 选输
	 */
	@PayField(required = false, max = 100)
	private String remark1;

	/**
	 * [备注字段2] MAX(100) 选输
	 */
	@PayField(required = false, max = 100)
	private String remark2;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getMerID() {
		return merID;
	}

	public void setMerID(String merID) {
		this.merID = merID;
	}

	public String getMerAcct() {
		return merAcct;
	}

	public void setMerAcct(String merAcct) {
		this.merAcct = merAcct;
	}

	public String getVerifyJoinFlag() {
		return verifyJoinFlag;
	}

	public void setVerifyJoinFlag(String verifyJoinFlag) {
		this.verifyJoinFlag = verifyJoinFlag;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getMerURL() {
		return merURL;
	}

	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getCarriageAmt() {
		return carriageAmt;
	}

	public void setCarriageAmt(String carriageAmt) {
		this.carriageAmt = carriageAmt;
	}

	public String getMerHint() {
		return merHint;
	}

	public void setMerHint(String merHint) {
		this.merHint = merHint;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getMerSignMsg() {
		return merSignMsg;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	public String getMerCert() {
		return merCert;
	}

	public void setMerCert(String merCert) {
		this.merCert = merCert;
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

	/**
	 * 商户提交表单签名merSignMsg格式. 组织要签名的数据串 (顺序固定；被商户签名的串为各输入项的值)：<br/>
	 * 接口名称的值+接口版本号的值+商城代码的值+商城账号的值+通知地址的值+结果发送类型的值+订单号的值+订单金额的值+支付币种的值+通知类型的值+
	 * 交易日期时间的值+校验联名标志的值
	 * 
	 * @return
	 */
	public String sign() {
		StringBuffer sb = new StringBuffer();
		// sb.append("interfaceName=").append(interfaceName)
		// .append("&interfaceVersion=").append(interfaceVersion)
		// .append("&merID=").append(merID).append("&merAcct=")
		// .append(merAcct).append("&merURL=").append(merURL)
		// .append("&notifyType=").append(notifyType).append("&orderid=")
		// .append(orderid).append("&amount=").append(amount)
		// .append("&curType=").append(curType).append("&resultType=")
		// .append(resultType).append("&orderDate=").append(orderDate)
		// .append("&verifyJoinFlag=").append(verifyJoinFlag);
		sb.append(interfaceName).append(interfaceVersion).append(merID)
				.append(merAcct).append(merURL).append(notifyType)
				.append(orderid).append(amount).append(curType)
				.append(resultType).append(orderDate).append(verifyJoinFlag);

		String s = sb.toString();
		byte[] bs = null;
		try {// TODO 将 key 路径 和 password 均在配置里面读取
			bs = ReturnValue.sign(s.getBytes(), s.getBytes().length, FileUtil.file2Byte(
					FileUtil.getClasspath() + "/cert/icbc/b2c/lxhg.key"), "123456".toCharArray());
			return new String(ReturnValue.base64enc(bs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String cert() {
		// merCert为（用测试证书user.crt）
		// BASE64编码
		return new String(ReturnValue.base64enc(FileUtil.file2Byte(
				FileUtil.getClasspath() + "/cert/icbc/b2c/lxhg.crt")));// TODO 将 key 路径 在配置里面读取
	}

	public static void main(String[] args) {
		System.out.println(new IcbcB2cPayObject().sign());
//		System.out.println(new IcbcB2CPayObject().cert());
	}

	@Override
	public String toString() {
		return "IcbcB2cPayObject [amount=" + amount + ", carriageAmt="
				+ carriageAmt + ", curType=" + curType + ", goodsID=" + goodsID
				+ ", goodsName=" + goodsName + ", goodsNum=" + goodsNum
				+ ", interfaceName=" + interfaceName + ", interfaceVersion="
				+ interfaceVersion + ", merAcct=" + merAcct + ", merCert="
				+ merCert + ", merHint=" + merHint + ", merID=" + merID
				+ ", merSignMsg=" + merSignMsg + ", merURL=" + merURL
				+ ", notifyType=" + notifyType + ", orderDate=" + orderDate
				+ ", orderid=" + orderid + ", remark1=" + remark1
				+ ", remark2=" + remark2 + ", resultType=" + resultType
				+ ", verifyJoinFlag=" + verifyJoinFlag + "]";
	}

}
