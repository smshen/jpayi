/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.ConfigObject;

/**
 * @author Administrator
 *
 */
public class AbcB2cConfigObject implements ConfigObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5785130222227511606L;
	/**
	 * 配置文件账号顺序
	 */
	private String seq;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	
}
