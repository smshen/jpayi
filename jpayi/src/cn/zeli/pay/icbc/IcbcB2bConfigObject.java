/**
 * 
 */
package cn.zeli.pay.icbc;

import cn.zeli.pay.ConfigObject;

/**
 * @author Administrator
 *
 */
public class IcbcB2bConfigObject implements ConfigObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7289552401011382952L;

	private String Shop_code;
	private String Shop_acc_num;
	private String PayeeAcct;
	
	public String getShop_code() {
		return Shop_code;
	}
	public void setShop_code(String shopCode) {
		Shop_code = shopCode;
	}
	public String getShop_acc_num() {
		return Shop_acc_num;
	}
	public void setShop_acc_num(String shopAccNum) {
		Shop_acc_num = shopAccNum;
	}
	public String getPayeeAcct() {
		return PayeeAcct;
	}
	public void setPayeeAcct(String payeeAcct) {
		PayeeAcct = payeeAcct;
	}
	
	
}
