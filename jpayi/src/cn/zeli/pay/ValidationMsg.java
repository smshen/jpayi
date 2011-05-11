/**
 * 
 */
package cn.zeli.pay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 验证消息.
 * 用于支付对象正确验证后消息封装
 * @author Administrator
 *
 */
public class ValidationMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8178800906539767842L;

	
	private boolean success;
	
	private List<Msg> list;

	public ValidationMsg() {
	}
	
	public ValidationMsg(boolean success) {
		this.success = success;
	}
	
	public ValidationMsg(boolean success, String field, Integer type, String msg) {
		this(success);
		addList(new Msg(field, type, msg));
	}
	

	public List<Msg> getList() {
		return list;
	}

	public void setList(List<Msg> list) {
		this.list = list;
	}
	
	public boolean addList(Msg msg) {
		if (null == this.list)
			this.list = new ArrayList<Msg>();
		
		return this.list.add(msg);
	}
	
	public boolean addAll(Collection<Msg> all) {
		if (null == this.list)
			this.list = new ArrayList<Msg>();
		
		return this.list.addAll(all);
	}



	public static class Msg {
		public static final int ERROR_REQUIRED = 1;
		public static final int ERROR_LENGTH = 2;
		public static final int ERROR_TOO_LONG = 3;
		public static final int ERROR_TOO_SHORT = 4;
		public static final int ERROR_REGEX = 5;
		public static final int ERROR_CUSTOM = 9;
		private String field;
		private Integer type;
		private String msg;
		
		public Msg(String field, Integer type, String msg) {
			this.field = field;
			this.type = type;
			this.msg = msg;
		}
		
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}

		@Override
		public String toString() {
			return "Msg [field=" + field + ", type=" + type + ", msg=" + msg
					+ "]";
		}
		
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "ValidationMsg [success=" + success + ", list=" + list + "]";
	}
	
	
}
