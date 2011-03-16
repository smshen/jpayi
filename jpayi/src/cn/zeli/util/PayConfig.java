/**
 * 
 */
package cn.zeli.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Administrator
 *
 */
public final class PayConfig {

//	final static Logger log = Logger.getLogger(Config.class);
	
	public final static String _porpertiesFileName = "pay-config";
	public static Map<String, String> config = null;
	
	static PayConfig instance = null;

	/**
	 * 固定可供读取的参数，包括带默认值的
	 */
	public static String PAY_BASE_RESULT_ATTRIBUTE = getStringWithOutSpace(
			"pay.result.do.attribute", "PAY_BASE_RESULT");
	public static String PAY_RESULT_DO_PARAM_ORDERID = getStringWithOutSpace(
			"pay.result.do.param.order.id", "orderId");
	public static String PAY_RESULT_DO_PARAM_ORDERAMOUNT = getStringWithOutSpace(
			"pay.result.do.param.order.amount", "orderAmount");
	public static String PAY_RESULT_DO_PARAM_BANK = getStringWithOutSpace(
			"pay.result.do.param.bank", "bankInfo");
	public static String PAY_RESULT_DO_PARAM_CONTENT = getStringWithOutSpace(
			"pay.result.do.param.content", "content");

//	public static String PAY_RESULT_DO_SUCCESS_URL = getStringWithOutSpace(
//			"pay.result.do.success.url", "");
//	public static String PAY_RESULT_DO_FAIL_URL = getStringWithOutSpace(
//			"pay.result.do.fail.url", "");

	public static String PAY_SUCCESS_URL = getStringWithOutSpace(
			"pay.result.success.url", "");
	public static String PAY_FAIL_URL = getStringWithOutSpace(
			"pay.result.fail.url", "");

	public static String ICBC_B2C_PRIVATE_KEY_PATH = getStringWithOutSpace("icbc.b2c.user.key.private", "");
	public static String ICBC_B2C_PUBLIC_KEY_PATH = getStringWithOutSpace("icbc.b2c.user.key.public", "");
	public static String ICBC_B2C_BANK_PUBLIC_KEY_PATH = getStringWithOutSpace("icbc.b2c.bank.key.public", "");

	public static String ICBC_B2B_PRIVATE_KEY_PATH = getStringWithOutSpace("icbc.b2b.user.key.private", "");
	public static String ICBC_B2B_PUBLIC_KEY_PATH = getStringWithOutSpace("icbc.b2b.user.key.public", "");
	public static String ICBC_B2B_BANK_PUBLIC_KEY_PATH = getStringWithOutSpace("icbc.b2b.bank.key.public", "");

	public static String CCB_B2B_PUBLIC_KEY = getStringWithOutSpace("ccb.b2b.key.public", "");
	public static String CCB_B2C_PUBLIC_KEY = getStringWithOutSpace("ccb.b2c.key.public", "");
	
	
	public static String getString(String key) {
		return getString(key, null);
	}
	
	public static String getString(String key, String defaultValue) {
		if(null == instance){       
			instance = new PayConfig();      
        } 
		
		String s = (null == config) ? null : config.get(key);
		return null == s ? defaultValue : s;
	}
	
	public static String getStringWithOutSpace(String key, String defaultValue) {
		String s = getString(key, defaultValue);
		if ("".equals(s.trim())) {
			s = defaultValue;
		}
		return s;
	}
	
	public static int getInt(String key) {
		return getInt(key, 0);
	}
	
	public static int getInt(String key, int defaultValue) {
		return getIntValue(getString(key), defaultValue);
	}
	
	/**
	 * 将1、true、on全部表示为true，不区分大小写，其余均为false
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	/**
	 * 将1、true、on全部表示为true，不区分大小写，其余均为false
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		String s = getString(key);
		if (null == s)
			return false;
		
		return getBooleanValue(s.trim(), defaultValue);
	}
	
	private PayConfig() {
		instance();
//		log.info("Get " + getClass().getName() + "Config instance.");
	}
	
	private void instance() {
		if (null == config) {
			refresh();
		}
	}
	
	public void refresh() {
		
		config = new HashMap<String, String>();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(_porpertiesFileName);
			Enumeration<String> e = bundle.getKeys();
			while (e.hasMoreElements()) {
				String key = e.nextElement().toString();
				config.put(key, bundle.getString(key));
			}
			
		} catch (Throwable t) {
			config = null;
//			log.warn("Load 'pay-config.properties' Error", t);
//			t.printStackTrace();
		}
	}
	
	private static int getIntValue(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	private static boolean getBooleanValue(String s, boolean defaultValue) {
		return (null != s && 
				("1".equals(s = s.trim().toLowerCase()) 
						|| "true".equals(s)
						|| "on".equals(s)));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(PayConfig.getString("xxx"));
//
//		System.out.println(getBooleanValue(null, false));
//		System.out.println(getBooleanValue("1", false));
//		System.out.println(getBooleanValue("0", false));
//		System.out.println(getBooleanValue(" true    ", false));
//		System.out.println(getBooleanValue("true", false));
//		System.out.println(getBooleanValue("True", false));
//		System.out.println(getBooleanValue("TRUE", false));
//		System.out.println(getBooleanValue("TRUE1", false));
//		System.out.println(getBooleanValue("on", false));
//		System.out.println(getBooleanValue("off", false));
//		System.out.println(getBooleanValue("On", false));
		
	}

}

