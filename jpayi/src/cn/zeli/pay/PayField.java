/**
 * 
 */
package cn.zeli.pay;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支付对象的字段注解
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
@Documented  
@Inherited 
public @interface PayField {
	
	/**
	 * 字段是否必须
	 * @return
	 */
	public boolean required() default false;
	
	/**
	 * 字段值长度最长限制
	 * @return
	 */
	public int max() default 0;
	
	/**
	 * 字段值长度最短限制
	 * @return
	 */
	public int min() default 0;
	
	/**
	 * 字段值固定长度.
	 * 优先于max()和min()
	 * @return
	 */
	public int length() default 0;
	
	/**
	 * Regular expression.
	 * 根据正则表达式，约束字段值
	 * @return
	 */
	public String regex() default "";
	
	/**
	 * 该字段是否需要提交到支付网关
	 * @return
	 */
	public boolean submit() default true;
}
