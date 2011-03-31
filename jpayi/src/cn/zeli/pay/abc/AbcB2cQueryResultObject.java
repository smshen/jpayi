/**
 * 
 */
package cn.zeli.pay.abc;

import cn.zeli.pay.AbstractQueryResultObject;
import cn.zeli.pay.QueryObject;

import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.XMLDocument;
import com.hitrust.trustpay.client.b2c.Order;
import com.hitrust.trustpay.client.b2c.QueryOrderRequest;

/**
 * @author Administrator
 * 
 */
public class AbcB2cQueryResultObject extends AbstractQueryResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990364879964984520L;

	private Order order = null;
	private TrxResponse tResponse = null;
	private AbcB2cQueryObject queryObject;

	public AbcB2cQueryResultObject() {
	}

	public void process() throws Exception {

		QueryOrderRequest tRequest = new QueryOrderRequest();
		tRequest.setOrderNo(queryObject.getOrderNo());
		tRequest.enableDetailQuery(queryObject.getEnableDetailQuery());
		tResponse = tRequest.extendPostRequest(Integer.parseInt(queryObject.getAbcB2cConfigObject().getSeq()));// .postRequest();

		if (tResponse.isSuccess()) {
			order = new Order(new XMLDocument(tResponse.getValue("Order")));

			// //6、取得订单明细
			// ArrayList tOrderItems = tOrder.getOrderItems();
			// for(int i = 0; i < tOrderItems.size(); i++) {
			// OrderItem tOrderItem = (OrderItem) tOrderItems.get(i);
			// out.println("ProductID   = [" + tOrderItem.getProductID () +
			// "]<br>");
			// out.println("ProductName = [" + tOrderItem.getProductName() +
			// "]<br>");
			// out.println("UnitPrice   = [" + tOrderItem.getUnitPrice () +
			// "]<br>");
			// out.println("Qty         = [" + tOrderItem.getQty () + "]<br>");
			// }
			if (null == order) {
				throw new Exception("农行B2C查询获取Order对象错误！");
			}
		} else {
			// log
			// 7、商户订单查询失败
			// out.println("ReturnCode   = [" + tResponse.getReturnCode () +
			// "]<br>");
			// out.println("ErrorMessage = [" + tResponse.getErrorMessage() +
			// "]<br>");
			throw new Exception("农行B2C商户订单查询失败：ReturnCode:["
					+ tResponse.getReturnCode() + "]; ErrorMessage:["
					+ tResponse.getErrorMessage() + "]");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	public String bankInfo() {
		return "ABC|B2C";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	public String orderId() {
		return order.getOrderNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	public float payAmount() {
		return toFloat(String.valueOf(order.getOrderAmount()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	public boolean success() {
		String status = order.getOrderStatus();
		return "03".equals(status) || "04".equals(status);
	}

	public String status() {
		return order.getOrderStatus();
	}

	public String statusRemark() {
		String status = order.getOrderStatus();
		if ("00".equals(status)) {
			return "订单已取消";
		}
		if ("01".equals(status)) {
			return "订单已建立，等待支付";
		}
		if ("02".equals(status)) {
			return "消费者已支付，等待支付结果";
		}
		if ("03".equals(status)) {
			return "订单已支付（支付成功）";
		}
		if ("04".equals(status)) {
			return "订单已结算（支付成功）";
		}
		if ("05".equals(status)) {
			return "订单已退款";
		}
		if ("99".equals(status)) {
			return "订单支付失败";
		}
		return null;
	}

	public void setQueryObject(QueryObject queryObject) {
		this.queryObject = (AbcB2cQueryObject) queryObject;
	}

	public static class AbcB2cQueryObject implements QueryObject {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7826222755800865340L;
		private String orderNo;
		private Boolean enableDetailQuery = false;

		private AbcB2cConfigObject abcB2cConfigObject;
		
		public AbcB2cQueryObject() {
		}

		public AbcB2cQueryObject(String orderNo, Boolean enableDetailQuery) {
			this.orderNo = orderNo;
			this.enableDetailQuery = enableDetailQuery;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Boolean getEnableDetailQuery() {
			return enableDetailQuery;
		}

		public void setEnableDetailQuery(Boolean enableDetailQuery) {
			this.enableDetailQuery = enableDetailQuery;
		}

		public AbcB2cConfigObject getAbcB2cConfigObject() {
			return abcB2cConfigObject;
		}

		public void setAbcB2cConfigObject(AbcB2cConfigObject abcB2cConfigObject) {
			this.abcB2cConfigObject = abcB2cConfigObject;
		}

		
	}

	public Object getDetailObject() {
		return tResponse;
	}

}
