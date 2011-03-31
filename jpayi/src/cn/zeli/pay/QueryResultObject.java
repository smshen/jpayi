/**
 * 
 */
package cn.zeli.pay;

/**
 * 查询订单结果
 * @author lz
 *
 */
public interface QueryResultObject extends PayResultObject {

	/**
	 * 订单状态说明.
	 * 通常为银行的直接说明
	 * @return
	 */
	public String statusRemark();
	
	/**
	 * 银行订单状态
	 * @return
	 */
	public String status();

	/**
	 * 设定查询对象
	 * @param queryObject
	 */
	public void setQueryObject(QueryObject queryObject);
	
	/**
	 * 处理
	 */
	public void process() throws Exception;
	
	/**
	 * 得到支付详情信息对象
	 * @return
	 */
	public Object getDetailObject();
}
