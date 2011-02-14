/**
 * 
 */
package cn.zeli.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DateUtil {

	private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String SHORT_FORMAT_TIME = "yyyyMMddHHmmss";
	public final static String SHORT_FORMAT_DAY = "yyyyMMdd";
	
	/**  
	 * 获得当前时间，默认格式  
	 * @param format  
	 * @return  
	 */
	public static String getCurrentDate() {
		return getCurrentDate(DEFAULT_FORMAT);
	}

	/**  
	 * 获得当前时间，格式自定义  
	 * @param format  
	 * @return  
	 */
	public static String getCurrentDate(String format) {
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, 0);
		return getDateFormat(day.getTime(), format);
	}
	
	/**
	 * 返回指定时间指定格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
