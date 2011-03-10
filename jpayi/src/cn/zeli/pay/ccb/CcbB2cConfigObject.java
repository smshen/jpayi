/**
 * 
 */
package cn.zeli.pay.ccb;

import cn.zeli.pay.ConfigObject;

/**
 * @author Administrator
 *
 */
public class CcbB2cConfigObject implements ConfigObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7507389854895756760L;

	private String MERCHANTID;
	
	private String POSID;
	
	private String BRANCHID;

	public String getMERCHANTID() {
		return MERCHANTID;
	}

	public void setMERCHANTID(String mERCHANTID) {
		MERCHANTID = mERCHANTID;
	}

	public String getPOSID() {
		return POSID;
	}

	public void setPOSID(String pOSID) {
		POSID = pOSID;
	}

	public String getBRANCHID() {
		return BRANCHID;
	}

	public void setBRANCHID(String bRANCHID) {
		BRANCHID = bRANCHID;
	}
	
	
	
}
