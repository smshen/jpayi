/**
 * 
 */
package cn.zeli.pay.chinabank;

import cn.zeli.pay.ConfigObject;

/**
 * 网银配置信息.
 * 这些具体参数都和网银本身参数名一致。并未遵守变量命名规范。
 * @author Administrator
 *
 */
public class ChinabankConfigObject implements ConfigObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -706591008926572546L;

	private String v_mid;
	
	private String key;

	public String getV_mid() {
		return v_mid;
	}

	public void setV_mid(String v_mid) {
		this.v_mid = v_mid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
