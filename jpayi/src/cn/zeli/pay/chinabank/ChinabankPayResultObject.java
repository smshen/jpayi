/**
 * 
 */
package cn.zeli.pay.chinabank;

import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.pay.ConfigObjectHelper;
import cn.zeli.util.EncodeUtil;

/**
 * @author Administrator
 * 
 */
public class ChinabankPayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6720436618177613373L;

	private String v_oid;
	private String v_pstatus;
	private String v_pstring;
	private String v_pmode;
	private String v_md5str;
	private String v_amount;
	private String v_moneytype;
	private String remark1;
	private String remark2;

	public boolean success() {
		// 20（表示支付成功）
		// 30（表示支付失败）
		if (null != v_pstatus && "20".equals(v_pstatus)) {
			return true;
		}
		return false;
	}

	public boolean verify() {
		// XXX key 值的注入？
		String md5 = EncodeUtil.MD5Encode(new StringBuffer()
				.append(v_oid)
				.append(v_pstatus)
				.append(v_amount)
				.append(v_moneytype)
				.append(((ChinabankConfigObject) ConfigObjectHelper
						.getConfig(ChinabankConfigObject.class)).getKey())
				.toString());
		return v_md5str.equals(md5.toUpperCase());
	}

	public String orderId() {
		return v_oid;
	}

	public float payAmount() {
		return toFloat(v_amount);
	}

	public String bankInfo() {
		return "chinabank|B2C";
	}

	public String getV_oid() {
		return v_oid;
	}

	public void setV_oid(String v_oid) {
		this.v_oid = v_oid;
	}

	public String getV_pstatus() {
		return v_pstatus;
	}

	public void setV_pstatus(String v_pstatus) {
		this.v_pstatus = v_pstatus;
	}

	public String getV_pstring() {
		return v_pstring;
	}

	public void setV_pstring(String v_pstring) {
		this.v_pstring = v_pstring;
	}

	public String getV_pmode() {
		return v_pmode;
	}

	public void setV_pmode(String v_pmode) {
		this.v_pmode = v_pmode;
	}

	public String getV_md5str() {
		return v_md5str;
	}

	public void setV_md5str(String v_md5str) {
		this.v_md5str = v_md5str;
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
