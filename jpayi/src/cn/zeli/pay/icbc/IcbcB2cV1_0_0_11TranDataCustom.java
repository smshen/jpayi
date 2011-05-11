/**
 * CopyRight(c) zeli.cn 2011
 * 2011-5-11 下午01:29:05
 */
package cn.zeli.pay.icbc;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;

/**
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11TranDataCustom extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294869530408393482L;

	/**
	 * [检验联名标志] =1
	 * 必输，签名，取值“1”：客户支付时，网银判断该客户是否与商户联名，
	 * 是则按上送金额扣帐，否则展现未联名错误；取值“0”：不检验客户是否与商户联名
	 * ，按上送金额扣帐。
	 */
	@PayField(required = true, length = 1)
	private String verifyJoinFlag = "1";

	/**
	 * 选输，默认为中文版取值：“EN_US”为英文版；取值：“ZH_CN”或其他为中文版。
	 * 注意：大小写敏感。
	 */
	@PayField(required = true, max = 10)
	private String Language = "ZH_CN";

	/**
	 * @return the verifyJoinFlag
	 */
	public String getVerifyJoinFlag() {
		return verifyJoinFlag;
	}

	/**
	 * @param verifyJoinFlag the verifyJoinFlag to set
	 */
	public void setVerifyJoinFlag(String verifyJoinFlag) {
		this.verifyJoinFlag = verifyJoinFlag;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return Language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		Language = language;
	}
	
}
