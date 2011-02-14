/**
 * 
 */
package cn.zeli.pay;

import java.util.Map;

/**
 * 支付成功或失败的业务逻辑调用类.
 * 先以简单实现为主。可改写支持spring应用
 * TODO 等待具体实现PayResultLogicService
 * @author Administrator
 *
 */
public class PayResultLogicServiceInvoker {

	public void doSuccess(Map map) {
		// 调用 PayResultLogicService 实现
		System.out.println("============= SUCCESS LOGIC============");
	}
	
	public void doFail(Map map) {
		// 调用 PayResultLogicService 实现
		System.out.println("============= Fail LOGIC============");
	}
}
