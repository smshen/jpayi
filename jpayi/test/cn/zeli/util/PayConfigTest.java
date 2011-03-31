package cn.zeli.util;

import java.io.File;

import junit.framework.TestCase;

public class PayConfigTest extends TestCase {

	
	public static void main(String[] args) {
		File f = new File(PayConfig.ICBC_B2B_BANK_PUBLIC_KEY_PATH);
		System.out.println(f.getPath());
		System.out.println(f.isFile());
		System.out.println(f.length());
	}
	
	
}
