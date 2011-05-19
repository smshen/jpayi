/**
 * 
 */
package cn.zeli.pay.icbc;

import cn.com.infosec.icbc.ReturnValue;
import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.util.FileUtil;

/**
 * 工行通知商户交易结果接口定义. 参考文档：《中国工商银行网上银行B2C在线支付接口说明》<br/>
 * 
 * @author Administrator
 * 
 */
public class IcbcB2cPayResultObject extends AbstractPayResultObject {

	public static final boolean URL_ENCODE = true;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4615079954209267094L;

	/**
	 * 接口名称 取值：“ICBC_PERBANK_B2C”
	 */
	private String interfaceName;

	/**
	 * 接口版本号 取值：“1.0.0.0”
	 */
	private String interfaceVersion;

	/**
	 * 订单号 客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。工行通过订单号加订单日期来唯一确认一笔订单的重复性。
	 */
	private String orderid;

	/**
	 * 银行指令序号 银行端指令流水号
	 */
	private String TranSerialNo;

	/**
	 * 订单金额 客户支付订单的总金额，一笔订单一个，以分为单位。不可以为零，必需符合金额标准。
	 */
	private String amount;

	/**
	 * 支付币种 用来区分一笔支付的币种，目前工行只支持使用人民币（001）支付。取值： “001”
	 */
	private String curType;

	/**
	 * 商户代码 唯一确定一个商户的代码，由商户在工行开户时，由工行告知商户
	 */
	private String merID;

	/**
	 * 商城账号 商城收费入账账号 （只能交易时指定）
	 */
	private String merAcct;

	/**
	 * 检验联名标志 取值“1”：客户支付时，网银判断该客户是否与商户联名，是则按上送金额扣帐，否则展现未联名错误；取值“0”：不检验客户是否与商户联名，
	 * 按上送金额扣帐。
	 */
	private String verifyJoinFlag;

	/**
	 * 客户联名标志 客户在银行端是否与商城联名标志位。1客户联名 0客户未联名
	 */
	private String JoinFlag;

	/**
	 * 联名会员号 联名客户在商户的会员号
	 */
	private String UserNum;

	/**
	 * 结果发送类型 取值“0”：无论支付成功或者失败，银行都向商户发送交易通知信息；取值“1”，银行只向商户发送交易成功的通知信息
	 */
	private String resultType;

	/**
	 * 交易日期时间 格式为：YYYYMMDDHHmmss 要求在银行系统当前时间的前1小时和后12小时范围内，否则判定交易时间非法。
	 */
	private String orderDate;

	/**
	 * 返回通知日期时间 格式为：YYYYMMDDHHmmss
	 */
	private String notifyDate;

	/**
	 * 订单处理状态<br/>
	 * 1-“交易成功，已清算”<br/>
	 * 2-“交易失败”<br/>
	 * 3-“交易可疑”
	 */
	private String tranStat;

	/**
	 * 错误描述
	 */
	private String comment;

	/**
	 * 备注1
	 */
	private String remark1;

	/**
	 * 备注2
	 */
	private String remark2;

	/**
	 * 通知消息银行签名数据 银行使用自己证书对商户通知消息按照一定格式进行的签名,然后进行BASE64编码后的字符串。（格式单独描述）
	 */
	private String signMsg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		// 参考 tranStat
		// 1-“交易成功，已清算”；
		// 2-“交易失败”；
		// 3-“交易可疑”

		return "1".equals(tranStat.trim());
	}

	public String bankInfo() {
		return "ICBC|B2C";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		String s = new StringBuffer().append("interfaceName").append("=").append(interfaceName)
				.append("&").append("interfaceVersion").append("=").append(interfaceVersion)
				.append("&").append("orderid").append("=").append(orderid)
				.append("&").append("TranSerialNo").append("=").append(TranSerialNo)
				.append("&").append("amount").append("=").append(amount)
				.append("&").append("curType").append("=").append(curType)
				.append("&").append("merID").append("=").append(merID)
				.append("&").append("merAcct").append("=").append(merAcct)
				.append("&").append("verifyJoinFlag").append("=").append(verifyJoinFlag)
				.append("&").append("JoinFlag")
				.append("=").append(JoinFlag).append("&").append("UserNum")
				.append("=").append(UserNum).append("&").append("resultType")
				.append("=").append(resultType).append("&").append("orderDate")
				.append("=").append(orderDate).append("&").append("notifyDate")
				.append("=").append(notifyDate).append("&").append("tranStat")
				.append("=").append(tranStat).append("&").append("comment")
				.append("=").append(comment).append("&").append("remark1")
				.append("=").append(remark1).append("&").append("remark2")
				.append("=").append(remark2).toString();

		
		// 调用API统一接口验证BASE64解码后得到的签名信息
		try {
			// 调用API统一接口对签名数据进行BASE64解码，得到签名信息。
			byte[] decSign = ReturnValue.base64dec(signMsg.getBytes());//s.getBytes());
			
			// 证书公钥BASE64解码成功，此处是银行公钥
//			byte[] decCert = ReturnValue.base64dec(FileUtil.file2Byte(
//					FileUtil.getClasspath() + "/cert/icbc/b2c/bank.crt"));//lxhg.crt
			byte[] decCert = FileUtil.file2Byte(
					FileUtil.getClasspath() + "/cert/icbc/b2c/bank.crt");

			// 返回验签结果，返回“0”表示验签成功；返回不为“0”表示验签失败。
			int r = ReturnValue.verifySign(s.getBytes(), s.getBytes().length, decCert, decSign);
			
			return (r == 0);
//				return true;
		} catch (Exception e) {
			// log
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return orderid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#orderAmount()
	 */
	public float payAmount() {
		return toFloat(amount) / 100;
	}

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

	public String getTranSerialNo() {
		return TranSerialNo;
	}

	public void setTranSerialNo(String tranSerialNo) {
		TranSerialNo = tranSerialNo;
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

	public String getJoinFlag() {
		return JoinFlag;
	}

	public void setJoinFlag(String joinFlag) {
		JoinFlag = joinFlag;
	}

	public String getUserNum() {
		return UserNum;
	}

	public void setUserNum(String userNum) {
		UserNum = userNum;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getTranStat() {
		return tranStat;
	}

	public void setTranStat(String tranStat) {
		this.tranStat = tranStat;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}


}
