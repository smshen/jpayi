/**
 * CopyRight(c) zeli.cn 2011
 * 2011-5-11 下午12:43:43
 */
package cn.zeli.pay.icbc;

import java.util.ArrayList;
import java.util.List;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.pay.ValidationMsg;

/**
 * 工行数据定义.参考文档《中国工商银行网上银行新B2C在线支付接口说明V1.0.0.11.doc》中的tranData字段说明<br/>
 * XML格式定义标签：B2CReq <br/>
 * <pre>
 * tranData格式(xml格式固定，选输字段的取值可以为空，标签需保留)
&lt;?xml version="1.0" encoding="GBK" standalone="no"?&gt;
&lt;B2CReq&gt;
&lt;interfaceName&gt;ICBC_PERBANK_B2C&lt;/interfaceName&gt;
&lt;interfaceVersion&gt;1.0.0.11&lt;/interfaceVersion&gt;
&lt;orderInfo&gt;
	&lt;orderDate&gt;20100308141629&lt;/orderDate&gt;
	&lt;curType&gt;001&lt;/curType&gt;
	&lt;merID&gt;0200EC20001119&lt;/merID&gt;
	&lt;subOrderInfoList&gt;
	&lt;subOrderInfo&gt;
		&lt;orderid&gt;201003081416290&lt;/orderid&gt;
		&lt;amount&gt;1&lt;/amount&gt;
		&lt;installmentTimes&gt;1&lt;/installmentTimes&gt;
		&lt;merAcct&gt;0200026009018372212&lt;/merAcct&gt;
		&lt;goodsID&gt;001&lt;/goodsID&gt;
		&lt;goodsName&gt;威尼熊&lt;/goodsName&gt;
		&lt;goodsNum&gt;2&lt;/goodsNum&gt;
		&lt;carriageAmt&gt;20&lt;/carriageAmt&gt;
	&lt;/subOrderInfo&gt;
	&lt;subOrderInfo&gt;
		&lt;orderid&gt;201003081416291&lt;/orderid&gt;
		&lt;amount&gt;1&lt;/amount&gt;
		&lt;installmentTimes&gt;1&lt;/installmentTimes&gt;
		&lt;merAcct&gt;0200026009018372212&lt;/merAcct&gt;
		&lt;goodsID&gt;001&lt;/goodsID&gt;
		&lt;goodsName&gt;威尼熊&lt;/goodsName&gt;
		&lt;goodsNum&gt;2&lt;/goodsNum&gt;
		&lt;carriageAmt&gt;20&lt;/carriageAmt&gt;
	&lt;/subOrderInfo&gt;
	&lt;/subOrderInfoList&gt;
&lt;/orderInfo&gt;
&lt;custom&gt;
	&lt;verifyJoinFlag&gt;0&lt;/verifyJoinFlag&gt;
	&lt;Language&gt;ZH_CN&lt;/Language&gt;
&lt;/custom&gt;
&lt;message&gt;
	&lt;creditType&gt;2&lt;/creditType&gt;
	&lt;notifyType&gt;AG&lt;/notifyType&gt;
	&lt;resultType&gt;1&lt;/resultType&gt;
	&lt;merReference&gt;localhost&lt;/merReference&gt;
	&lt;merCustomIp&gt;127.0.0.1&lt;/merCustomIp&gt;
	&lt;goodsType&gt;1&lt;/goodsType&gt;
	&lt;merCustomID&gt;123456&lt;/merCustomID&gt;
	&lt;merCustomPhone&gt;13466780886&lt;/merCustomPhone&gt;
	&lt;goodsAddress&gt;三里屯&lt;/goodsAddress&gt;
	&lt;merOrderRemark&gt;防欺诈接口专用&lt;/merOrderRemark&gt;
	&lt;merHint&gt;请保留包装&lt;/merHint&gt;
	&lt;remark1&gt;&lt;/remark1&gt;
	&lt;remark2&gt;&lt;/remark2&gt;
	&lt;merURL&gt;http://localhost:9080/EbizSimulate/emulator/Newb2c_Pay_Mer.jsp&lt;/merURL&gt;
	&lt;merVAR&gt;test&lt;/merVAR&gt;
&lt;/message&gt;
&lt;/B2CReq&gt;
注：红色字体部分，即&lt;subOrderInfo&gt;是循环部分，最大5次，超过则接口校验报错。

 * </pre>
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11TranData extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2544414214557873589L;

	/**
	 * [接口名称] MAX(30) 必输，取值：“ICBC_PERBANK_B2C”
	 */
	@PayField(required = true, length = 16)
	private String interfaceName = "ICBC_PERBANK_B2C";

	/**
	 * [接口版本号] MAX(15) 必输，取值：“1.0.0.11”
	 */
	@PayField(required = true, max = 15)
	private String interfaceVersion = "1.0.0.11";


	
	/**
	 * =======================================
	 * tranData数据封装的XML标签
	 * =======================================
	 */
	
	/**
	 * orderInfo标签
	 */
	private IcbcB2cV1_0_0_11TranDataOrderInfo orderInfo;
	
	/**
	 * custom标签
	 */
	private IcbcB2cV1_0_0_11TranDataCustom custom;

	/**
	 * message标签
	 */
	private IcbcB2cV1_0_0_11TranDataMessage message;


	public static void main(String[] args) {
		IcbcB2cV1_0_0_11TranData td = new IcbcB2cV1_0_0_11TranData();

		IcbcB2cV1_0_0_11TranDataOrderInfo oi = new IcbcB2cV1_0_0_11TranDataOrderInfo();
		List<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo> ls = new ArrayList<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo>();
		ls.add(new IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo());
		ls.add(new IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo());
		oi.setSubOrderInfoList(ls);
		td.setOrderInfo(oi);
		
		IcbcB2cV1_0_0_11TranDataCustom c = new IcbcB2cV1_0_0_11TranDataCustom();
		td.setCustom(c);
		c.setVerifyJoinFlag("1");
		
		
		ValidationMsg vm = td.validation();
		System.out.println(vm);
	}
	
	
	
	/**
	 * @return the interfaceName
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * @param interfaceName the interfaceName to set
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * @return the interfaceVersion
	 */
	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	/**
	 * @param interfaceVersion the interfaceVersion to set
	 */
	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	/**
	 * @return the orderInfo
	 */
	public IcbcB2cV1_0_0_11TranDataOrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(IcbcB2cV1_0_0_11TranDataOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @return the custom
	 */
	public IcbcB2cV1_0_0_11TranDataCustom getCustom() {
		return custom;
	}

	/**
	 * @param custom the custom to set
	 */
	public void setCustom(IcbcB2cV1_0_0_11TranDataCustom custom) {
		this.custom = custom;
	}

	/**
	 * @return the message
	 */
	public IcbcB2cV1_0_0_11TranDataMessage getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(IcbcB2cV1_0_0_11TranDataMessage message) {
		this.message = message;
	}
	
}
