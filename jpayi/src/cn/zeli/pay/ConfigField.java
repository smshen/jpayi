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
 * @deprecated 暂未使用
 * 银行配置字段注解
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
@Documented  
@Inherited 
public @interface ConfigField {

	/**
	 * 字段是否必须
	 * @return
	 */
	public boolean required() default true;
	
	/**
	 * 表明改字段值是否为文件路径
	 * @return
	 */
	public boolean isFilePath() default false;
	
	
}
