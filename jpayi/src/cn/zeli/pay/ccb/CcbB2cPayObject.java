/**
 * 
 */
package cn.zeli.pay.ccb;

import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;

/**
 * 建设银行支付提交字段 参考文档：《建行网上商户接口文档2009-05-11.doc》
 * 
 * @author Administrator
 * 
 */
public class CcbB2cPayObject extends AbstractPayObject {

	private static final long serialVersionUID = -8017991859569536906L;

	/**
	 * 商户代码 CHAR(15) 由建行统一分配
	 */
	@PayField(required = true, max = 15)
	private String MERCHANTID;

	/**
	 * 商户柜台代码 CHAR(9) 由建行统一分配，缺省为000000000
	 */
	@PayField(required = true, max = 9)
	private String POSID = "000000000";

	/**
	 * 分行代码 CHAR(9) 由建行统一指定
	 */
	@PayField(required = true, max = 9)
	private String BRANCHID;

	/**
	 * 定单号 CHAR(30) 由商户提供，最长30位,按实际长度给出
	 */
	@PayField(required = true, max = 30)
	private String ORDERID;

	/**
	 * 付款金额 NUMBER(16,2) 由商户提供，按实际金额给出
	 */
	@PayField(required = true, max = 16)
	private String PAYMENT;

	/**
	 * 币种 CHAR(2) 缺省为01－人民币（只支持人民币支付）
	 */
	@PayField(required = true, max = 2)
	private String CURCODE = "01";

	/**
	 * 备注1 CHAR(30) 网银不处理，直接传到城综网
	 */
	@PayField(max = 30)
	private String REMARK1;

	/**
	 * 备注2 CHAR(30) 网银不处理，直接传到城综网
	 */
	@PayField(max = 30)
	private String REMARK2;

	/**
	 * 交易码 CHAR(6) 由建行统一分配为520100
	 */
	@PayField(max = 6)
	private String TXCODE = "520100";

	/**
	 * MAC校验域 CHAR(32) 采用标准MD5算法，由商户实现
	 */
	@PayField(required = true, length = 32)
	private String MAC;

	/**
	 * 根据文档介绍，将会有3中接口，V1、V2、V3. 此处我们暂实现最新接口V3.
	 * 
	 * @return
	 */
	public String sign() {
		// 字符串中变量名必须是大写字母
		StringBuffer sb = new StringBuffer();
		sb.append("MERCHANTID=").append(MERCHANTID).append("&POSID=")
				.append(POSID).append("&BRANCHID=").append(BRANCHID)
				.append("&ORDERID=").append(ORDERID).append("&PAYMENT=")
				.append(PAYMENT).append("&CURCODE=").append(CURCODE)
				.append("&TXCODE=").append(TXCODE).append("&REMARK1=")
				.append(REMARK1).append("&REMARK2=").append(REMARK2);

		return CcbUtil.getMAC(sb.toString());
	}
}
