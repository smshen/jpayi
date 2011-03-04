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
 * 工商银行B2B网银支付对象. 参考文档：网上银行商户手册v3.0《网上银行系统B2B商户(新接口)手册.doc》<br/>
 * 字符编码：GBK
 * 
 * @author Administrator
 * 
 */
public class IcbcB2bPayObject extends AbstractPayObject {

	private static final long serialVersionUID = 1658225068280311497L;

	/**
	 * 接口名称 MAX (30) 必输，签名，上送“B2B”，区别大小写！
	 */
	@PayField(required = true, max = 30)
	private String APIName = "B2B";

	/**
	 * 接口版本号 =15 必输，签名,上送”001.001.001.001”
	 */
	@PayField(required = true, length = 15)
	private String APIVersion = "001.001.001.001";

	/**
	 * 商户代码 =14 必输，签名，唯一确定一个商户的代码，由商户在工行开户时，由工行告知商户
	 */
	@PayField(required = true, length = 14)
	private String Shop_code;

	/**
	 * 支付结果信息通知程序地址 MAX (200) 签名，使用HS接口模式的商户用来接收工行订单支付结果信息的程序名字和位置。
	 */
	@PayField(required = false, max = 200)
	private String MerchantURL;

	/**
	 * 订单号 MAX(30)
	 * 必输，签名，客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。工行通过订单号加订单日期来唯一确认一笔订单的重复性。
	 */
	@PayField(required = true, max = 30)
	private String ContractNo;
	

	/**
	 * 订单金额 MAX(18) 必输，签名，客户支付订单的总金额，一笔订单一个，保留到分，无小数点，如金额为1.00元，上传数据为“100” <br/>
	 * <b><font color="red">精确到分</font><b>
	 */
	@PayField(required = true, max = 18)
	private String ContractAmt;

	/**
	 * 支付币种 = 3 必输，签名，用来区分一笔支付的币种。目前工行只支持人民币（001）支付。定义如下：001 人民币
	 */
	@PayField(required = true, length = 3)
	private String Account_cur = "001";

	/**
	 * 检验联名标志 ? 必输，签名，固定上送2
	 */
	@PayField(required = true)
	private String JoinFlag = "2";

	/**
	 * 订单签名数据 无限制 生成签名数据的方法见后面的说明
	 */
	@PayField(required = true)
	private String Mer_Icbc20_signstr;

	/**
	 * 商城证书数据 无限制 必输，商户端读取本地商户证书文件后，再使用工行提供的API进行Base64编码后产生的商户证书数据字串。
	 */
	@PayField(required = true)
	private String Cert = cert();

	/**
	 * 结果发送类型 =1 [0 ----成功失败信息都发送 ; 1 ---- 只发送成功信息]
	 * 必输，签名，如果取0，工行向商户发送一笔订单的每一次交易结果，无论支付成功或者失败，如果取1，工行只向商户发送交易成功的通知信息。
	 */
	@PayField(required = true, length = 1)
	private String SendType;

	/**
	 * 交易日期时间 =14 必输，签名，YYYYMMDDHHmmss。与工行系统当前时间差为：前1小时，后12小时
	 */
	@PayField(required = true, length = 14, regex = "^[1-9]{1}[0-9]{3}(0[1-9]{1}|1[0-2]{1})([0-2]{1}[0-9]{1}|3[0-1]{1})([0-1]{1}[0-9]{1}|2[0-3]{1})[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}$")
	private String TranTime = DateUtil
			.getCurrentDate(DateUtil.SHORT_FORMAT_TIME);

	/**
	 * 商城账号 MAX (19) 必输，签名
	 */
	@PayField(required = true, max = 19)
	private String Shop_acc_num;

	/**
	 * 收款单位账号 MAX (19) 必输，签名
	 */
	@PayField(required = true, max = 19)
	private String PayeeAcct;

	/**
	 * 商品编号 MAX (30) 选输
	 */
	@PayField(required = false, max = 30)
	private String GoodsCode;

	/**
	 * 商品名称 MAX (60) 选输
	 */
	@PayField(required = false, max = 60)
	private String GoodsName;

	/**
	 * 订单数量 MAX (10) 选输
	 */
	@PayField(required = false, max = 10)
	private String Amount;

	/**
	 * 已含运费金额 MAX (18) 选输
	 */
	@PayField(required = false, max = 18)
	private String TransFee;

	/**
	 * 商城提示 MAX (120) 选输，最多120字符
	 */
	@PayField(required = false, max = 120)
	private String ShopRemark;

	/**
	 * 商城备注字段 MAX (100) 选输，最多100字符
	 */
	@PayField(required = false, max = 100)
	private String ShopRem;

	/**
	 * 签名信息字段Mer_Icbc20_signstr的产生
	 * 通过调用工行提供的签名API，用商户自己的私钥对交易数据和商户信息数据进行数字签名，产生签名结果
	 * @return
	 */
	public String sign() {
		
		// 组织要签名的数据串（串的顺序不可改变）. 注意：串中间不要有空格
		StringBuffer sb = new StringBuffer();
		sb.append("APIName").append("=").append(APIName)
			.append("&").append("APIVersion").append("=").append(APIVersion)
			.append("&").append("Shop_code").append("=").append(Shop_code)
			.append("&").append("MerchantURL").append("=").append(MerchantURL)
			.append("&").append("ContractNo").append("=").append(ContractNo)
			.append("&").append("ContractAmt").append("=").append(ContractAmt)
			.append("&").append("Account_cur").append("=").append(Account_cur)
			.append("&").append("JoinFlag").append("=").append(JoinFlag)
			.append("&").append("SendType").append("=").append(SendType)
			.append("&").append("TranTime").append("=").append(TranTime)
			.append("&").append("Shop_acc_num").append("=").append(Shop_acc_num)
			.append("&").append("PayeeAcct").append("=").append(PayeeAcct);
		
		// 调用签名接口对数据签名
		String s = sb.toString();
		byte[] bs = null;
		try {
			// TODO 将 key 路径 和 password 均在配置里面读取
			bs = ReturnValue.sign(s.getBytes(), s.getBytes().length, FileUtil.file2Byte(
					FileUtil.getClasspath() + "/cert/icbc/b2b/lxhg.key"), "123456".toCharArray());
			
			// 然后调用BASE64编码API对产生的签名数据编码
			return new String(ReturnValue.base64enc(bs));
		} catch (Exception e) {
			// log
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * 获取本地商户证书base64编码字符串
	 * @return
	 */
	public String cert() {
		// 商户证书字段cert的BASE64编码
		return new String(ReturnValue.base64enc(FileUtil.file2Byte(
				FileUtil.getClasspath() + "/cert/icbc/b2b/lxhg.crt")));// TODO 将 key 路径 在配置里面读取
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

	public String getMer_Icbc20_signstr() {
		return Mer_Icbc20_signstr;
	}

	public void setMer_Icbc20_signstr(String mer_Icbc20_signstr) {
		Mer_Icbc20_signstr = mer_Icbc20_signstr;
	}

	public String getCert() {
		return Cert;
	}

	public void setCert(String cert) {
		Cert = cert;
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

	public String getGoodsCode() {
		return GoodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		GoodsCode = goodsCode;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getTransFee() {
		return TransFee;
	}

	public void setTransFee(String transFee) {
		TransFee = transFee;
	}

	public String getShopRemark() {
		return ShopRemark;
	}

	public void setShopRemark(String shopRemark) {
		ShopRemark = shopRemark;
	}

	public String getShopRem() {
		return ShopRem;
	}

	public void setShopRem(String shopRem) {
		ShopRem = shopRem;
	}

	@Override
	public String toString() {
		return "IcbcB2bPayObject [APIName=" + APIName + ", APIVersion="
				+ APIVersion + ", Account_cur=" + Account_cur + ", Amount="
				+ Amount + ", Cert=" + Cert + ", ContractAmt=" + ContractAmt
				+ ", ContractNo=" + ContractNo + ", GoodsCode=" + GoodsCode
				+ ", GoodsName=" + GoodsName + ", JoinFlag=" + JoinFlag
				+ ", Mer_Icbc20_signstr=" + Mer_Icbc20_signstr
				+ ", MerchantURL=" + MerchantURL + ", PayeeAcct=" + PayeeAcct
				+ ", SendType=" + SendType + ", ShopRem=" + ShopRem
				+ ", ShopRemark=" + ShopRemark + ", Shop_acc_num="
				+ Shop_acc_num + ", Shop_code=" + Shop_code + ", TranTime="
				+ TranTime + ", TransFee=" + TransFee + "]";
	}
	
}
