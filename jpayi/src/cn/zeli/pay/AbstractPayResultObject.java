/**
 * 
 */
package cn.zeli.pay;

/**
 * @author Administrator
 *
 */
public abstract class AbstractPayResultObject implements PayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7044118935841561885L;
	
	protected float toFloat(String amount) {
		try {
			return Float.parseFloat(amount);
		} catch (Exception e) {
			return 0f;
		}
	}

}
