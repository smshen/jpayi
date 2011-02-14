/**
 * 
 */
package cn.zeli.pay;

import java.util.HashMap;
import java.util.Map;

import cn.zeli.pay.chinabank.ChinabankConfigObject;

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
		
		
		
	}
	
	public static ConfigObject getConfig(Class clazz) {
		
		// 先从模拟数据中读取
		
		// TODO 将从模拟数据读取配置，改成从缓存或持久层获取
		
		return configs.get(clazz);
	}
	
	
	
}
