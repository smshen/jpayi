/**
 * 
 */
package cn.zeli.pay.ccb;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

/**
 * 建行接口相关扩展工具类
 * 参考：http://jameswxx.javaeye.com/blog/592379 系列（《建设银行对接（一）》），共5篇
 * @author Administrator
 *
 */
public class CcbUtil {
	
	private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
		 '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	/**
	 * 计算得到MAC
	 * @param str
	 * @return
	 */
	public static String getMAC(String sendData) {
		try {
			return byte2Char(Encoder.encryptMD5(sendData.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对建行返回的数据进行数字签名校验，校验算法如下:<br>
	 * 1:将签名字符串和公钥转换成二进制格式<br>
	 * 2:使用公钥进行签名的逆运算<br>
	 * 3:使用标准MD5算法运算原文<br>
	 * 比较1、2结果
	 * @param signature 数字签名字符串
	 * @param content 原文字符串
	 * @param publicKey 公钥字符串
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySignature(String signature,String content,String publicKey) {
		boolean status = false;
		
		try {
			//对原文进行MD5加密
			byte[] md5bytes = Encoder.encryptMD5(content.getBytes());
			
			//对签名字符串进行逆运算，将16进制字符串按照约定算法转换为二进制数据
			byte[] signbytes = char2Byte(signature);
			
			//对公钥字符串进行逆运算，将16进制字符串按照约定算法转换为二进制数据
			byte[] keybytes = char2Byte(publicKey);
			
			//校验
			status = RSAEncoder.verify(md5bytes, keybytes, signbytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 将字节转化成字符串，转换算法如下:<br>
	 * 1:每个字节长度为8位，分割为两个4位，高四位和低四位<br>
	 * 2:将每个四位换算成16进制，并且对应ascii码，如0x01对应1,0x0d对应d,具体对应关系请见数组hexDigits[]<br> 
	 * 3:将得到的字符拼成字符串 
	 * @param bytes
	 * @return
	 */
	public static String byte2Char(byte[] bytes) {
		// 每个字节用 16 进制表示的话，使用两个字符，所以字符数组长度是字节数字长度的2倍  
		char[] str = new char[bytes.length * 2];
		// 表示转换结果中对应的字符位置
		int k = 0;
		// 每一个字节转换成 16 进制字符
		for (int i = 0; i < bytes.length; i++) {
			byte byte0 = bytes[i]; // 取第 i 个字节
			// 取字节中高 4 位(左边四位)的数字转换,>>>为逻辑右移，右移后，高四位变成低四位，需要对低四位之外的值进行消零运算
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			// 取字节中低 4 位(右边四位)的数字转换，并且和0xf进行"逻辑与"运算，以消除高位的值，得到纯净的低四位值 
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
	
	public static byte[] char2Byte(String str) {
		char[] chars = str.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int k = 0;
		for (int i = 0; i < chars.length; i = i + 2) {
			// 得到索引值
			byte high = (byte) getIndex(chars[i]);
			byte low = (byte) getIndex(chars[i + 1]);
			// 第一个字符索引逻辑左移四位,并进行或运算,将低四位设置为1
			high = (byte) ((high << 4) | 0xf);
			// 第二个字符索引进行或运算,将高四位设置为1
			low = (byte) (low | 0xf0);
			// 两个字节进行与运算 
			bytes[k++] = (byte) (high & low);
		}
		return bytes;
	}
	
	/**
	 * return p;
	 * @param c
	 * @return
	 */
	private static int getIndex(char c) {
		int p = -1;
		for (int i = 0; i < hexDigits.length; i++) {
			if (hexDigits[i] == c) {
				p = i;
				break;
			}
		}
		return p;
	}
	
	/**
	 * @deprecated 建行提供示例
	 * @param src
	 * @return
	 */
	public static String byte2Ascii(byte[] src) {
		int len, i;
		byte tb;
		char high, tmp, low;
		String result = new String();
		len = src.length;
		for (i = 0; i < len; i++) {
			tb = src[i];
			tmp = (char) ((tb >>> 4) & 0x000f);
			if (tmp >= 10) {
				high = (char) ('a' + tmp - 10);
			} else {
				high = (char) ('0' + tmp);
			}
			result += high;
			tmp = (char) (tb & 0x000f);
			if (tmp >= 10) {
				low = (char) ('a' + tmp - 10);
			} else {
				low = (char) ('0' + tmp);
			}
			result += low;
		}
		return result;
	}
	

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 */
	public static void main(String[] args) throws Exception {
		String inputStr = "2fsfa但是方式sdfs ";

        Map<String, Object> keyMap = RSAEncoder.initKey();
        byte[] publicKey = RSAEncoder.getPublicKey(keyMap);
        byte[] privateKey = RSAEncoder.getPrivateKey(keyMap);

        String publicKeyX16 = byte2Char(publicKey);
        String privateKeyX16 = byte2Char(privateKey);
        System.out.println("------------------------------------------------------");
        System.out.println("1:初始化RSA密钥对,16进制字符如下");
        System.out.println("公钥: \n" + publicKeyX16);
        System.out.println("私钥： \n" + privateKeyX16);
        
        System.out.println("-------------------------------------------------------");
        System.out.println("2:模拟建行进行数字签名");
        byte[] inputData = Encoder.encryptMD5(inputStr.getBytes());
        System.out.println("先对原文进行MD5加密,16进制字符如下:");
        System.out.println(byte2Char(inputData));

        System.out.println("再对MD5加密后的数据进行签名,签名16进制字符如下:");
        byte[] sign=RSAEncoder.sign(inputData, privateKey);
        String signx16 = byte2Char(sign);
        System.out.println(signx16);
        
        System.out.println("-------------------------------------------------------");
        System.out.println("3:校验数字签名");
        boolean status = verifySignature(signx16, inputStr, publicKeyX16);
        System.out.println("状态:" + status);
        System.out.println("--------------------------------------------------------");
	}

}
