/**
 * 
 */
package cn.zeli.pay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zeli.pay.ValidationMsg.Msg;

/**
 * @author Administrator
 *
 */
public class AbstractPayObject implements PayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7362295489680722142L;

	/**
	 * 对子类的进行过注解的属性进行验证
	 * 
	 * @return
	 */
	public ValidationMsg validation() {
		Class<?> clazz = this.getClass();
		ValidationMsg msg = new ValidationMsg(true);
		try {
			BeanInfo bi = Introspector.getBeanInfo(this.getClass());
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();

			for (PropertyDescriptor pd : pds) {
				String itemName = pd.getName();

				if ("class".equals(itemName)) {
					continue;// class是关键字,被忽略
				}

				Field field = null;//clazz.getDeclaredField(itemName);

				try {
					field = clazz.getDeclaredField(itemName);
				} catch (Exception e) {
					// 由于对于变量名称为ABC的情况正确，对于Abc则有误，故作此处理
					itemName = itemName.substring(0, 1).toUpperCase(Locale.ENGLISH) + itemName.substring(1);
					try {
						field = clazz.getDeclaredField(itemName);
					} catch (Exception e1) {
						e.printStackTrace();
						continue;
					}
				}
				
				PayField f = field.getAnnotation(PayField.class);

				field.setAccessible(true);
				
//				System.out.println(this.getClass().getSimpleName() + "   ->" + itemName);
				/*
				 * 2011-5-11 修改 
				 * 判断内部属性是否为该类的子类，若是，且不为空，则必须进行验证；若是，却为空，则也验证不通过
				 */
				if (Collection.class.isAssignableFrom(field.getType())) {
					Collection c = (Collection) field.get(this);
					if (null != c && !c.isEmpty()) {
						Iterator itr = c.iterator();
						while (itr.hasNext()) {
							Object it = itr.next();
							if (null != it && it instanceof AbstractPayObject) {
								ValidationMsg vm = ((AbstractPayObject) it).validation();
								if (!vm.isSuccess()) {
									msg.setSuccess(false);
									msg.addAll(vm.getList());
									break;// 是否需要全验证？若需要则去除该句
								}
							}
						}
					}
					continue;
				}
				if (AbstractPayObject.class.isAssignableFrom(field.getType())) {
					AbstractPayObject apo = (AbstractPayObject) field.get(this);
					if (null != apo) {
						ValidationMsg vm = apo.validation();
						if (!vm.isSuccess()) {
							msg.setSuccess(false);
							msg.addAll(vm.getList());
						}
					} else {// 若是，却为空，则也验证不通过
						msg.setSuccess(false);
						msg.addList(new Msg(itemName, Msg.ERROR_REQUIRED,
								new StringBuffer().append(itemName)
										.append(" <").append(field.getType().getSimpleName()).append("> can not be null.").toString()));
					}
					continue;
				}
				
				

				String value = (String) field.get(this);

				if (null == f)
					continue;

				// 判断是否必须
				if ((null == value || "".equals(value)) && f.required()) {
					// log 缺少值
					msg.setSuccess(false);
					msg.addList(new Msg(itemName, Msg.ERROR_REQUIRED,
							new StringBuffer().append(itemName)
									.append(" is required.").toString()));
				}

				if (null != value && !"".equals(value)) {
					// 值长度不正确
					if (f.length() > 0 && value.length() != f.length()) {
						msg.setSuccess(false);
						msg.addList(new Msg(itemName, Msg.ERROR_LENGTH,
								new StringBuffer().append(itemName)
										.append(" is too long. Content = [")
										.append(value)
										.append("], Max Length is ")
										.append(f.length()).toString()));
					}

					// 值长度超过最大长度
					if (f.max() > 0 && value.length() > f.max()) {
						msg.setSuccess(false);
						msg.addList(new Msg(itemName, Msg.ERROR_LENGTH,
								new StringBuffer().append(itemName)
										.append(" [").append(value)
										.append("], can not be greater than ")
										.append(f.length())
										.append(" characters.").toString()));
					}

					// 值长度小于最小长度
					if (f.min() > 0 && value.length() < f.min()) {
						msg.setSuccess(false);
						msg.addList(new Msg(itemName, Msg.ERROR_LENGTH,
								new StringBuffer().append(itemName)
										.append(" [").append(value)
										.append("] can not be less than ")
										.append(f.length())
										.append(" characters.").toString()));
					}

					// 正则表达式匹配不符合规则
					if (null != f.regex() && f.regex().length() > 0
							&& !regEx(f.regex(), value)) {
						msg.setSuccess(false);
						msg.addList(new Msg(itemName, Msg.ERROR_REGEX,
								new StringBuffer().append(itemName)
										.append(" [").append(value)
										.append("] is invalid.").toString()));
					}

				}

			}

		} /*
		 * catch (IntrospectionException e) { e.printStackTrace(); } catch
		 * (SecurityException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (NoSuchFieldException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalArgumentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public String generateFormInputsHtml() {
		StringBuffer buffer = new StringBuffer();
		Class<?> clazz = this.getClass();
		try {
			BeanInfo bi = Introspector.getBeanInfo(this.getClass());
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();

			for (PropertyDescriptor pd : pds) {
				String itemName = pd.getName();

				if ("class".equals(itemName)) {
					continue;// class是关键字,被忽略
				}
				Field field = null;

				try {
					field = clazz.getDeclaredField(itemName);
				} catch (Exception e) {
					// 由于对于变量名称为ABC的情况正确，对于Abc则有误，故作此处理
					itemName = itemName.substring(0, 1).toUpperCase(Locale.ENGLISH) + itemName.substring(1);
					try {
						field = clazz.getDeclaredField(itemName);
					} catch (Exception e1) {
						e1.printStackTrace();
						continue;
					}
				}
				

				PayField f = field.getAnnotation(PayField.class);

				field.setAccessible(true);

//				if (null == f || (null != f && !f.submit()))
				if (null != f && !f.submit())
					continue;
				
				// 2011-5-11 
				String value = (String) field.get(this);

				if (null == value)
					value = "";
				
				buffer.append("<input type=\"hidden\" name=\"")
						.append(itemName).append("\" value=\"").append(value)
						.append("\">");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static boolean regEx(String regex, String src) {
		try {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(src);
			return m.matches();
		} catch (Exception e) {
			return false;
		}
	}
}
