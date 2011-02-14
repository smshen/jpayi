/**
 * 
 */
package cn.zeli.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author Administrator
 *
 */
public class FileUtil {
	
	public static String getClasspath() {
        String path = "";
		try {
			path = java.net.URLDecoder.decode(new FileUtil().getClass().getResource("/").getPath(), "UTF-8");

	        path = path.replace('\\', '/');
	        if (!path.endsWith("/")) {
	            path += "/";
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return path;
    }
	
	
	public static byte[] file2Byte(String fileName) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			byte[] b = new byte[in.available()];
			in.read(b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getClasspath());
	}

}
