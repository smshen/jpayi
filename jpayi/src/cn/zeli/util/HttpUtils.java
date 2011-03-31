/**
 * 
 */
package cn.zeli.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 
 */
public class HttpUtils {

	public static final String DEFAULT_CHARSET = Constants.CHARSET_UTF8;
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";

	public static final String ISO88591 = "ISO-8859-1";

	/**
	 * 获取编码字符集<br/>
	 * 分别从request和response中尝试获取
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		if (null == request || null == response) {
			return Constants.DEFAULT_ENCODING;
		}
		String encode = null;
		return (StringUtils.isEmpty(encode = request.getCharacterEncoding()) ? (StringUtils
				.isEmpty(encode = response.getCharacterEncoding()) ? encode
				: Constants.DEFAULT_ENCODING)
				: encode);

	}

	public static <T> void bindBeanOnlyString(HttpServletRequest request,
			T object) throws IntrospectionException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {

		bindBeanOnlyString(request, object, null);
	}

	public static <T> void bindBeanOnlyString(HttpServletRequest request,
			T object, String charset) throws IntrospectionException,
			SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Class<?> clazz = object.getClass();
		BeanInfo bi = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		Map paramsMap = paramMap(request, charset);
		for (PropertyDescriptor pd : pds) {
			String itemName = pd.getName();
			if ("class".equals(itemName)) {
				continue;// class是关键字,被忽略
			}

			Field field = null;
			try {
				field = clazz.getDeclaredField(itemName);
			} catch (Exception e) {
				itemName = itemName.substring(0, 1).toUpperCase(Locale.ENGLISH)
						+ itemName.substring(1);
				try {
					field = clazz.getDeclaredField(itemName);
				} catch (Exception e1) {
					e1.printStackTrace();
					continue;
				}
			}

			field.setAccessible(true);

			if ("_all_params_map".equals(itemName)) {// 特例参数，将所有request设值
				field.set(object, paramsMap);
			} else {
				Object tmp = paramsMap.get(itemName);
				// if (null == tmp)
				// field.set(object, "");
				// else
				field.set(object, tmp);
			}

		}
	}

	public static Map paramMap(HttpServletRequest request, String charset) {
		Map<String, String> result = new HashMap<String, String>();
		Map map = request.getParameterMap();
		for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
			String name = (String) itr.next();
			String[] values = (String[]) map.get(name);

			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}

			if (null != charset) {// 若有charset转换要求，则直接转换
				try {
					result.put(name, new String(valueStr.getBytes(ISO88591),
							charset));
				} catch (UnsupportedEncodingException e) {
					result.put(name, valueStr);
				}
			} else {
				// GET 参数乱麻解决
				if (request.getMethod().equals("GET")) {
					try {
						result.put(name, new String(
								valueStr.getBytes(ISO88591),
								Constants.DEFAULT_CHARSET));
					} catch (UnsupportedEncodingException e) {
						// e.printStackTrace();
						result.put(name, valueStr);
					}
				} else {
					result.put(name, valueStr);
				}
			}
		}
		return result;
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params,
			int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params,
			String charset, int connectTimeout, int readTimeout)
			throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		byte[] content = {};
		if (null != query) {
			content = query.getBytes(charset);
		}
		return doPost(url, ctype, content, connectTimeout, readTimeout);
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param ctype
	 *            请求类型
	 * @param content
	 *            请求字节数组
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost(String url, String ctype, byte[] content,
			int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				conn = getConnection(new URL(url), METHOD_POST, ctype);
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				// TODO log ：url content
				throw e;
			}
			try {
				out = conn.getOutputStream();
				out.write(content);
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				// TODO log ：url conn content
				throw e;
			}

		} finally {
			if (null != out) {
				out.close();
			}
			if (null != conn) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params)
			throws IOException {
		return doGet(url, params, DEFAULT_CHARSET);
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params,
			String charset) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;

		try {
			String ctype = "application/x-www-form-urlencoded;charset="
					+ charset;
			String query = buildQuery(params, charset);
			try {
				conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				// TODO log ：url params
				throw e;
			}

			try {
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				// TODO log ：url conn params
				throw e;
			}

		} finally {
			if (null != conn) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static HttpURLConnection getConnection(URL url, String method,
			String ctype) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "u-pay-java");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}

	private static URL buildGetUrl(String strUrl, String query)
			throws IOException {
		URL url = new URL(strUrl);
		if (StringUtils.isEmpty(query)) {
			return url;
		}

		if (StringUtils.isEmpty(url.getQuery())) {
			if (strUrl.endsWith("?")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "?" + query;
			}
		} else {
			if (strUrl.endsWith("&")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "&" + query;
			}
		}

		return new URL(strUrl);
	}

	public static String buildQuery(Map<String, String> params, String charset)
			throws IOException {
		if (null == params || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (StringUtils.areNotEmpty(name, value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=").append(
						URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
	}

	protected static String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		if (null == es) {
			return getStreamAsString(conn.getInputStream(), charset);
		} else {
			String msg = getStreamAsString(es, charset);
			if (StringUtils.isEmpty(msg)) {
				throw new IOException(conn.getResponseCode() + ":"
						+ conn.getResponseMessage());
			} else {
				throw new IOException(msg);
			}
		}
	}

	private static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (null != stream) {
				stream.close();
			}
		}
	}

	private static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * 使用默认的UTF-8字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 反编码后的参数值
	 */
	public static String decode(String value) {
		return decode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用默认的UTF-8字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 编码后的参数值
	 */
	public static String encode(String value) {
		return encode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用指定的字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 反编码后的参数值
	 */
	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 使用指定的字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 编码后的参数值
	 */
	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	private static Map<String, String> getParamsFromUrl(String url) {
		Map<String, String> map = null;
		if (null != url && url.indexOf('?') != -1) {
			map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
		}
		if (null == map) {
			map = new HashMap<String, String>();
		}
		return map;
	}

	/**
	 * 从URL中提取所有的参数。
	 * 
	 * @param query
	 *            URL地址
	 * @return 参数映射
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (null != pairs && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (null != param && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	//
	// }


}
