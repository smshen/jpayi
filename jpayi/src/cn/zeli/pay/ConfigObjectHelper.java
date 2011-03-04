/**
 * 
 */
package cn.zeli.pay;

import java.util.HashMap;
import java.util.Map;

import cn.zeli.pay.chinabank.ChinabankConfigObject;
import cn.zeli.pay.icbc.IcbcB2bConfigObject;
import cn.zeli.pay.icbc.IcbcB2cConfigObject;

/**
 * 配置文件读取类
 * @author Administrator
 *
 */
public class ConfigObjectHelper {

	// 模拟数据 TODO delete
	private static Map<Class, ConfigObject> configs = new HashMap<Class, ConfigObject>();
	
	// 模拟数据 TODO delete
	static {
		// 
		ChinabankConfigObject cco = new ChinabankConfigObject();
		cco.setKey("123456");
		cco.setV_mid("22023664");
		configs.put(ChinabankConfigObject.class, cco);
		
		IcbcB2cConfigObject icbcB2co = new IcbcB2cConfigObject();
		icbcB2co.setMerID("1611EC23589693");
		icbcB2co.setMerAcct("1611002319022123647");
		icbcB2co.setPassword("123456");
		configs.put(IcbcB2cConfigObject.class, icbcB2co);
		
		IcbcB2bConfigObject icbcB2bo = new IcbcB2bConfigObject();
		icbcB2bo.setShop_code("1611EC13401181");
		icbcB2bo.setShop_acc_num("1611002319022123647");
		icbcB2bo.setPayeeAcct("1611002309024596339");
		configs.put(IcbcB2bConfigObject.class, icbcB2bo);
		
	}
	
	public static ConfigObject getConfig(Class clazz) {
		
		// 先从模拟数据中读取
		
		// TODO 将从模拟数据读取配置，改成从缓存或持久层获取
		
		return configs.get(clazz);
	}
	
	
	
}
