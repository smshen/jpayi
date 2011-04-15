/**
 * 
 */
package cn.zeli.pay.icbc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import cn.zeli.util.HttpUtils;

/**
 * @author lz
 * 
 */
public class IcbcHelper {

	public static final String SUCCESS = "success";
	public static final String CONTENT = "content";
	
	/**
	 * 所有主机默认通过
	 */
	private static HostnameVerifier hnv = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			return;
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			return;
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	} };

	public static Map<Object, Object> queryHelper(String pksFile, String password, Map params, String apiUrl, String charset) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		//String keyf = path + "***.e***";

		SSLSocketFactory ssf = null;
//		PrintWriter out = null;
		OutputStream out = null;
		BufferedReader in = null;
		String result = "";

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("PKCS12");

			// load keystore
			ks.load(new FileInputStream(pksFile), password.toCharArray());

			kmf.init(ks, password.toCharArray());

			ctx.init(kmf.getKeyManagers(), trustAllCerts, null);

			// System.out.println("load keystore success.");
			ssf = ctx.getSocketFactory();

			HttpsURLConnection.setDefaultSSLSocketFactory(ssf);
			HttpsURLConnection.setDefaultHostnameVerifier(hnv);
			URL realUrl = new URL(apiUrl);

			// 打开和URL之间的连接/servlet/ICBCINBSEBusinessServlet
			HttpsURLConnection conn = (HttpsURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn
					.setRequestProperty(
							"user-agent",
							"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 发送请求参数
			String query = HttpUtils.buildQuery(params, charset);
			byte[] content = {};
			if (null != query) {
				content = query.getBytes(charset);
			}
//			System.out.println(java.net.URLDecoder.decode(query));
			
			// 获取URLConnection对象对应的输出流
//			out = new PrintWriter(conn.getOutputStream());
//			out.print(query);
			out = conn.getOutputStream();
//
			out.write(content);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}

			result = java.net.URLDecoder.decode(result, charset).trim();

//			System.out.println(result);
			
			if (result.startsWith("<?xml")) {
				map.put(SUCCESS, true);
			} else {
				map.put(SUCCESS, false);
			}
			map.put(CONTENT, result);

		} catch (Exception e) {
//			e.printStackTrace();// log
			map.put(SUCCESS, false);
			map.put(CONTENT, e.toString());
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				
				if (null != in) {
					in.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
