/**
 * 
 */
package cn.zeli.pay.icbc;

import cn.zeli.pay.ConfigObject;

/**
 * 工行需要配置信息
 * @author Administrator
 *
 */
public class IcbcB2cConfigObject implements ConfigObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3509351940033019209L;

	/**
	 * 商户代码
	 */
	private String merID;
	
	/**
	 * 商城账号
	 */
	private String merAcct;
	
	private String merCert;
	
	private String password;

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

	public String getMerCert() {
		return merCert;
	}

	public void setMerCert(String merCert) {
		this.merCert = merCert;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
