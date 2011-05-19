/**
 * Copyright(c) 2011 zeli.cn. All rights reserved.
 * 2011-5-19 下午02:17:17
 */
package cn.zeli.pay.icbc;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.com.infosec.icbc.ReturnValue;
import cn.zeli.pay.AbstractPayResultObject;
import cn.zeli.util.FileUtil;

/**
 * @author lz
 * 
 */
public class IcbcB2cV1_0_0_11PayResultObject extends AbstractPayResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6540656639851352088L;

	/**
	 * 返回商户变量 无限制 取值：商户提交接口中merVAR字段当返回银行结果时，作为一个隐藏域变量，商户可以用此变量维护session等等。
	 * 由客户端浏览器支付完成后提交通知结果时是明文传输，建议商户对此变量使用额外安全防范措施，如签名、base64，银行端将此字段原样返回
	 */
	private String merVAR;

	/**
	 * 通知结果数据 无限制 银行通知消息，xml格式定义见下文，提交商户时对xml明文串进行了base64编码；
	 * xml串中没有回车换行和多余空格；包含xml头属性，且格式固定；
	 */
	private String notifyData;

	/**
	 * 银行对通知结果的签名数据 无限制
	 * 银行使用自己证书对商户通知消息notifyData字段的xml格式明文串进行的签名，然后进行BASE64编码后的字符串。
	 * 注意：签名是对notifyData的xml明文进行签名
	 * ，不是其BASE64编码后的串；签名后得到二进制数据，对此数据进行BASE64编码得到signMsg
	 */
	private String signMsg;

	/**
	 * xml转换后的properties
	 */
	private Properties pros;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#success()
	 */
	@Override
	public boolean success() {
		return "1".equals(pros.get("tranStat"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	@Override
	public boolean verify() {
		String s;
		try {
			s = new String(ReturnValue.base64dec(notifyData.getBytes("GBK")),"GBK");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return false;
		}

		// 调用API统一接口验证BASE64解码后得到的签名信息
		try {
			// 调用API统一接口对签名数据进行BASE64解码，得到签名信息。
			byte[] decSign = ReturnValue.base64dec(signMsg.getBytes());//s.getBytes());
			
			// 证书公钥BASE64解码成功，此处是银行公钥
//			byte[] decCert = ReturnValue.base64dec(FileUtil.file2Byte(
//					FileUtil.getClasspath() + "/cert/icbc/b2c/bank.crt"));//lxhg.crt
			byte[] decCert = FileUtil.file2Byte(
					FileUtil.getClasspath() + "/cert/icbc/b2c/bank.crt");

			// 返回验签结果，返回“0”表示验签成功；返回不为“0”表示验签失败。
			int r = ReturnValue.verifySign(s.getBytes(), s.getBytes().length, decCert, decSign);
			
			if (r == 0)
				return true;
		} catch (Exception e) {
			// log
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#orderId()
	 */
	@Override
	public String orderId() {
		return pros.getProperty("orderid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#payAmount()
	 */
	@Override
	public float payAmount() {
		return toFloat(pros.getProperty("amount"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeli.pay.PayResultObject#bankInfo()
	 */
	@Override
	public String bankInfo() {
		return "ICBC|B2C";
	}

	public void convertNotifyData() throws Exception {
		pros = xmlParser(new String(ReturnValue.base64dec(notifyData.getBytes("GBK")),"GBK"));
	}
	
	/**
	 * 返回数据,解析函数.
	 * 由于订单均为单笔交易提交，故此处也简单处理成单笔交易
	 */
	Properties xmlParser(String srcStr) throws Exception {
		System.out.println("\n==============NotifyData===start\n" + srcStr + "\n==============NotifyData===end\n");
		if (null == srcStr || "".equals(srcStr.trim())) {
			throw new Exception("NotifyData为空");
		}
		Properties pr = null;
		StringReader StrReader = null;
		String propertieNames[] = new String[] { "interfaceName",
				"interfaceVersion", "orderDate", "orderid", "amount",
				"installmentTimes", "merAcct", "tranSerialNo", "curType",
				"merID", "verifyJoinFlag", "JoinFlag", "UserNum",
				"TranBatchNo", "notifyDate", "tranStat", "comment" };

		try {
			DocumentBuilderFactory domFac = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder domBuilder = domFac.newDocumentBuilder();
			StrReader = new StringReader(srcStr);
			InputSource is = new InputSource(StrReader);
			Document doc = domBuilder.parse(is);
			pr = new Properties();

			for (int i = 0; i < propertieNames.length; i++) {
				Element ele = (Element) doc.getElementsByTagName(
						propertieNames[i]).item(0);
				if (ele.hasChildNodes()) {
					System.out.println(propertieNames[i] + "=="
							+ ele.getFirstChild().getNodeValue());
					pr.put(propertieNames[i], ele.getFirstChild()
							.getNodeValue());
				}
			}
		} catch (ParserConfigurationException e) {
			throw new Exception("配置信息错误", e);
		} catch (SAXException e) {
			throw new Exception("SAX错误", e);
		} catch (IOException e) {
			throw new Exception("读取流文件异常", e);
		} finally {
			if (StrReader != null) {
				StrReader.close();
			}
		}
		return pr;
	}

	/**
	 * @return the merVAR
	 */
	public String getMerVAR() {
		return merVAR;
	}

	/**
	 * @param merVAR the merVAR to set
	 */
	public void setMerVAR(String merVAR) {
		this.merVAR = merVAR;
	}

	/**
	 * @return the notifyData
	 */
	public String getNotifyData() {
		return notifyData;
	}

	/**
	 * @param notifyData the notifyData to set
	 */
	public void setNotifyData(String notifyData) {
		this.notifyData = notifyData;
	}

	/**
	 * @return the signMsg
	 */
	public String getSignMsg() {
		return signMsg;
	}

	/**
	 * @param signMsg the signMsg to set
	 */
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	
}
