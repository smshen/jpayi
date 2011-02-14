/**
 * 
 */
package cn.zeli.util;

/**
 * @author Administrator
 *
 */
public class StringUtils {

	public static String toString(Object o) {
		return null == o ? "" : o.toString();
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>StringUtil.isEmpty(null) = true</li>
	 * <li>StringUtil.isEmpty("") = true</li>
	 * <li>StringUtil.isEmpty("   ") = true</li>
	 * <li>StringUtil.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (null == value || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 * 
	 * @param values 字符串列表
	 * @return true/false
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (null == values || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
	
	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 * 
	 * @param unicode 通用字符编码的字符串
	 * @return 汉字
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	public static String firstUpper(String str) {
		if (null == str)
			return "";

		if (str.length() == 1) {
			return str.toUpperCase();
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("[" + toString("123") + "]");
//		System.out.println("[" + toString(null) + "]");
//		System.out.println("[" + toString("") + "]");
//		System.out.println("[" + toString("  ") + "]");
//		System.out.println("[" + toString("abc") + "]");
	}

}
