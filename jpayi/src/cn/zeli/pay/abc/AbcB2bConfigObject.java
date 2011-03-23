/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.ConfigObject;

/**
 * @author Administrator
 *
 */
public class AbcB2bConfigObject implements ConfigObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7271623982779067823L;

	private String tAccountDBNo;
	
	private String tAccountDBName;
	
	private String tAccountDBBank;

	public String gettAccountDBNo() {
		return tAccountDBNo;
	}

	public void settAccountDBNo(String tAccountDBNo) {
		this.tAccountDBNo = tAccountDBNo;
	}

	public String gettAccountDBName() {
		return tAccountDBName;
	}

	public void settAccountDBName(String tAccountDBName) {
		this.tAccountDBName = tAccountDBName;
	}

	public String gettAccountDBBank() {
		return tAccountDBBank;
	}

	public void settAccountDBBank(String tAccountDBBank) {
		this.tAccountDBBank = tAccountDBBank;
	}
	
}
