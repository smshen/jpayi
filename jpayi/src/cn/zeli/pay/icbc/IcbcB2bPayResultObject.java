/**
 * 
 */
package cn.zeli.pay.icbc;

import cn.com.infosec.icbc.ReturnValue;
import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.util.FileUtil;

/**
 * 工行反馈信息接口数据.
 * 参考文档：《网上银行系统B2B商户(新接口)手册.doc》<br/>
 * 注意：
 * 1. 当指令批复时结果发送类型默认为成功失败都发送。
 * 2. 当指令批复时接受交易的日期时间TranTime为空。
 * 
 * @author Administrator
 *
 */
public class IcbcB2bPayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6293497246318708262L;
	
	/**
	 * 接口名称
	 * 原输入值，签名
	 */
	private String APIName;

	/**
	 * 接口版本号
	 * 原输入值，签名
	 */
	private String APIVersion;

	/**
	 * 商户代码
	 * 原输入值，签名，唯一确定一个商户的代码，由商户在工行开户时，由工行告知商户
	 */
	private String Shop_code;

	/**
	 * 支付结果信息通知程序地址
	 * 原输入值，签名，使用HS接口模式的商户用来接收工行订单支付结果信息的程序名字和位置
	 */
	private String MerchantURL;

	/**
	 * 指令序号
	 * 必输，签名
	 */
	private String 	Serial_no;

	/**
	 * 订单处理状态<br/>
	 * 必输，签名<br/>
	 * 0----成功<br/>
	 * 1----失败<br/>
	 * 2----可疑交易<br/>
	 * 3----等待授权<br/>

	 */
	private String PayStatusZHCN;

	/**
	 * 错误代码
	 * 必输，签名
	 */
	private String TranErrorCode;

	/**
	 * 错误描述
	 * 必输，签名
	 */
	private String TranErrorMsg;

	/**
	 * 订单号
	 * 原输入值，签名，客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。工行通过订单号加订单日期来唯一确认一笔订单的重复性。
	 */
	private String ContractNo;

	/**
	 * 订单金额
	 * 原输入值，签名，客户支付订单的总金额，一笔订单一个，保留到分，无小数点，如金额为1.00元，上传数据为“100”
	 */
	private String ContractAmt;

	/**
	 * 支付币种
	 * 原输入值，签名，用来区分一笔支付的币种。目前工行只支持人民币（001）支付。定义如下：001      人民币
	 */
	private String Account_cur;

	/**
	 * 检验联名标志
	 * 原输入值
	 */
	private String JoinFlag;

	/**
	 * 商城联名标志
	 * （暂无该功能，预留）签名
	 */
	private String ShopJoinFlag;

	/**
	 * 客户联名标志
	 * （暂无该功能，预留）签名
	 */
	private String CustJoinFlag;

	/**
	 * CustJoinNumber
	 * （暂无该功能，预留）签名
	 */
	private String CustJoinNumber;

	/**
	 * 订单签名数据
	 * 必输，为字符串，已经过Base64编码，商户验证前需先解码
	 */
	private String NotifySign;

	/**
	 * 结果发送类型.
	 * 0 ----成功失败信息都发送；1 ---- 只发送成功信息<br/>
	 * 原输入值，签名，如果取0，工行向商户发送一笔订单的每一次交易结果，无论支付成功或者失败，如果取1，工行只向商户发送交易成功的通知信息。
	 */
	private String SendType;

	/**
	 * 接收交易日期时间
	 * 原输入值，签名，YYYYMMDDHHmmss。与系统当前时间差为：前1小时，后12小时
	 */
	private String TranTime;

	/**
	 * 返回通知日期时间
	 * 必输，签名，YYYYMMDDHHmmss。
	 */
	private String NotifyTime;

	/**
	 * 商城账号
	 * 原输入值，签名
	 */
	private String Shop_acc_num;

	/**
	 * 收款单位账号
	 * 原输入值，签名
	 */
	private String PayeeAcct;

	/**
	 * 收款单位名称
	 * 签名
	 */
	private String PayeeName;
	
	/**
	 * 商城备注字段
	 * 原输入值，最多100字符
	 */
	private String ShopRem;
	

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return ContractNo;
	}

	public String payAmount() {
		return ContractAmt;
	}

	public String bankInfo() {
		return "ICBC|B2C";
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.AbstractPayResultObject#success()
	 */
	public boolean success() {
		// 参考字段PayStatusZHCN值的定义
		if (null != PayStatusZHCN && "".equals(PayStatusZHCN)) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.zeli.pay.AbstractPayResultObject#verify()
	 */
	public boolean verify() {
		
		// 取原始数据
		String s = new StringBuffer().append("APIName").append("=").append(APIName)
		.append("&").append("APIVersion").append("=").append(APIVersion)
		.append("&").append("Shop_code").append("=").append(Shop_code)
		.append("&").append("MerchantURL").append("=").append(APIName)
		.append("&").append("Serial_no").append("=").append(Serial_no)
		.append("&").append("PayStatusZHCN").append("=").append(PayStatusZHCN)
		.append("&").append("TranErrorCode").append("=").append(TranErrorCode)
		.append("&").append("TranErrorMsg").append("=").append(TranErrorMsg)
		.append("&").append("ContractNo").append("=").append(ContractNo)
		.append("&").append("ContractAmt").append("=").append(ContractAmt)
		.append("&").append("Account_cur").append("=").append(Account_cur)
		.append("&").append("JoinFlag").append("=").append(JoinFlag)
		.append("&").append("ShopJoinFlag").append("=").append(ShopJoinFlag)
		.append("&").append("CustJoinFlag").append("=").append(CustJoinFlag)
		.append("&").append("CustJoinNumber").append("=").append(CustJoinNumber)
		.append("&").append("SendType").append("=").append(SendType)
		.append("&").append("TranTime").append("=").append(TranTime)
		.append("&").append("NotifyTime").append("=").append(NotifyTime)
		.append("&").append("Shop_acc_num").append("=").append(Shop_acc_num)
		.append("&").append("PayeeAcct").append("=").append(PayeeAcct)
		.append("&").append("PayeeName").append("=").append(PayeeName)
		.toString();
		
		System.out.println(s);
		
		// 调用API统一接口对签名数据进行BASE64解码，得到签名信息。
		byte[] decSign = ReturnValue.base64dec(NotifySign.getBytes());
		
		// 证书公钥BASE64解码成功，此处是银行公钥
		byte[] decCert = FileUtil.file2Byte(
				FileUtil.getClasspath() + "/cert/icbc/b2b/bank.crt");
		
		// 调用API统一接口验证BASE64解码后得到的签名信息
		try {
			// 返回验签结果，返回“0”表示验签成功；返回不为“0”表示验签失败。
			int r = ReturnValue.verifySign(s.getBytes(), s.getBytes().length, decCert, decSign);
			if (r == 0)
				return true;
		} catch (Exception e) {
			// log
			e.printStackTrace();
		}
		
		return false;
	}

	public String getAPIName() {
		return APIName;
	}

	public void setAPIName(String aPIName) {
		APIName = aPIName;
	}

	public String getAPIVersion() {
		return APIVersion;
	}

	public void setAPIVersion(String aPIVersion) {
		APIVersion = aPIVersion;
	}

	public String getShop_code() {
		return Shop_code;
	}

	public void setShop_code(String shop_code) {
		Shop_code = shop_code;
	}

	public String getMerchantURL() {
		return MerchantURL;
	}

	public void setMerchantURL(String merchantURL) {
		MerchantURL = merchantURL;
	}

	public String getSerial_no() {
		return Serial_no;
	}

	public void setSerial_no(String serial_no) {
		Serial_no = serial_no;
	}

	public String getPayStatusZHCN() {
		return PayStatusZHCN;
	}

	public void setPayStatusZHCN(String payStatusZHCN) {
		PayStatusZHCN = payStatusZHCN;
	}

	public String getTranErrorCode() {
		return TranErrorCode;
	}

	public void setTranErrorCode(String tranErrorCode) {
		TranErrorCode = tranErrorCode;
	}

	public String getTranErrorMsg() {
		return TranErrorMsg;
	}

	public void setTranErrorMsg(String tranErrorMsg) {
		TranErrorMsg = tranErrorMsg;
	}

	public String getContractNo() {
		return ContractNo;
	}

	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}

	public String getContractAmt() {
		return ContractAmt;
	}

	public void setContractAmt(String contractAmt) {
		ContractAmt = contractAmt;
	}

	public String getAccount_cur() {
		return Account_cur;
	}

	public void setAccount_cur(String account_cur) {
		Account_cur = account_cur;
	}

	public String getJoinFlag() {
		return JoinFlag;
	}

	public void setJoinFlag(String joinFlag) {
		JoinFlag = joinFlag;
	}

	public String getShopJoinFlag() {
		return ShopJoinFlag;
	}

	public void setShopJoinFlag(String shopJoinFlag) {
		ShopJoinFlag = shopJoinFlag;
	}

	public String getCustJoinFlag() {
		return CustJoinFlag;
	}

	public void setCustJoinFlag(String custJoinFlag) {
		CustJoinFlag = custJoinFlag;
	}

	public String getCustJoinNumber() {
		return CustJoinNumber;
	}

	public void setCustJoinNumber(String custJoinNumber) {
		CustJoinNumber = custJoinNumber;
	}

	public String getNotifySign() {
		return NotifySign;
	}

	public void setNotifySign(String notifySign) {
		NotifySign = notifySign;
	}

	public String getSendType() {
		return SendType;
	}

	public void setSendType(String sendType) {
		SendType = sendType;
	}

	public String getTranTime() {
		return TranTime;
	}

	public void setTranTime(String tranTime) {
		TranTime = tranTime;
	}

	public String getNotifyTime() {
		return NotifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		NotifyTime = notifyTime;
	}

	public String getShop_acc_num() {
		return Shop_acc_num;
	}

	public void setShop_acc_num(String shop_acc_num) {
		Shop_acc_num = shop_acc_num;
	}

	public String getPayeeAcct() {
		return PayeeAcct;
	}

	public void setPayeeAcct(String payeeAcct) {
		PayeeAcct = payeeAcct;
	}

	public String getPayeeName() {
		return PayeeName;
	}

	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}

	public String getShopRem() {
		return ShopRem;
	}

	public void setShopRem(String shopRem) {
		ShopRem = shopRem;
	}

}
