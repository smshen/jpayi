/**
 * CopyRight(c) zeli.cn 2011
 * 2011-5-11 上午11:22:49
 */
package cn.zeli.pay.icbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.infosec.icbc.ReturnValue;
import cn.com.infosec.util.Base64;
import cn.zeli.pay.AbstractPayObject;
import cn.zeli.pay.PayField;
import cn.zeli.util.FileUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 工商银行B2C网银支付对象. 参考文档：《中国工商银行网上银行新B2C在线支付接口说明V1.0.0.11.doc》
 * @author lz
 *
 */
public class IcbcB2cV1_0_0_11PayObject extends AbstractPayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725284448362477204L;


	/**
	 * [接口名称] MAX(30) 必输，取值：“ICBC_PERBANK_B2C”
	 */
	@PayField(required = true, max = 30)
	private String interfaceName = "ICBC_PERBANK_B2C";

	/**
	 * [接口版本号] MAX(15) 必输，取值：“1.0.0.11”
	 */
	@PayField(required = true, max = 15)
	private String interfaceVersion = "1.0.0.11";
	
	/**
	 * [交易数据] 无限制，必输，签名；
	 * 整合所有交易数据形成的xml明文串，并做BASE64编码；
	 * 具体格式定义见下文；
	 * 注意：需有xml头属性；
	 * 整个字段使用BASE64编码；
	 * xml明文中没有回车换行和多余空格；
	 */
	@PayField(required = true)
	private String tranData;
	

	/**
	 * [订单签名数据] 无限制 必输
	 * 商户使用工行提供的签名API和商户证书将tranData的xml明文串进行签名，得到二进制签名数据，然后进行BASE64编码后得到可视的merSignMsg；
	 * 注意：签名时是针对tranData的xml明文，不是将tranData进行BASE64编码后的串；
	 */
	@PayField(required = true)
	private String merSignMsg = sign();

	/**
	 * [商城证书公钥] 无限制 必输 商户用二进制方式读取证书公钥文件后，进行BASE64编码后产生的字符串。
	 */
	@PayField(required = true)
	private String merCert = cert();
	
	/**
	 * tranData数据对象，将此对象转换成xml填入tranDate字符串中
	 */
	@PayField(submit = false)
	private IcbcB2cV1_0_0_11TranData tranDataObject;
	
	/**
	 * 商户提交表单签名merSignMsg格式. 组织要签名的数据串 (顺序固定；被商户签名的串为各输入项的值)：<br/>
	 * 接口名称的值+接口版本号的值+商城代码的值+商城账号的值+通知地址的值+结果发送类型的值+订单号的值+订单金额的值+支付币种的值+通知类型的值+
	 * 交易日期时间的值+校验联名标志的值
	 * 
	 * @return
	 */
	public String sign() {

		if (null == tranDataObject) {
			return null;
		}
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("subOrderInfo", IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo.class);
		xstream.alias("orderInfo", IcbcB2cV1_0_0_11TranDataOrderInfo.class);
		xstream.alias("custom", IcbcB2cV1_0_0_11TranDataCustom.class);
		xstream.alias("message", IcbcB2cV1_0_0_11TranDataMessage.class);
		xstream.alias("B2CReq", IcbcB2cV1_0_0_11TranData.class);

		String xmlString =  "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>\n"
				+ xstream.toXML(tranDataObject)
				.replaceAll("\n", "")
						.replaceAll("\r", "").replaceAll(" ", "")
						;
		;
		this.setTranData(xmlString);
		
		byte[] src = xmlString.getBytes();
		byte[] bs = null;
		try {// TODO 将 key 路径 和 password 均在配置里面读取
			bs = ReturnValue.sign(src, src.length, FileUtil.file2Byte(
					FileUtil.getClasspath() + "/cert/icbc/b2c/lxhg.key"), "123456".toCharArray());
			return new String(ReturnValue.base64enc(bs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String cert() {
		// merCert为（用测试证书user.crt）
		// BASE64编码
//		return new String(ReturnValue.base64enc(FileUtil.file2Byte(
//				FileUtil.getClasspath() + "/cert/icbc/b2c/lxhg.crt")));// TODO 将 key 路径 在配置里面读取
		
		FileInputStream fis = null;
        String retStr = "";        

        try
        {
            fis = new FileInputStream(FileUtil.getClasspath() + "/cert/icbc/b2c/lxhg.crt");
            byte[] bcrt = new byte[fis.available()];
            fis.read(bcrt);
            
            retStr = Base64.encode(bcrt);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return retStr;
		
	}
	
	public static void main(String[] args) {
		IcbcB2cV1_0_0_11PayObject po = new IcbcB2cV1_0_0_11PayObject();
		
		IcbcB2cV1_0_0_11TranData td = new IcbcB2cV1_0_0_11TranData();

		IcbcB2cV1_0_0_11TranDataOrderInfo oi = new IcbcB2cV1_0_0_11TranDataOrderInfo();
		List<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo> ls = new ArrayList<IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo>();
		ls.add(new IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo());
		ls.add(new IcbcB2cV1_0_0_11TranDataOrderInfoSubOrderInfo());
		oi.setSubOrderInfoList(ls);
		td.setOrderInfo(oi);
		
		IcbcB2cV1_0_0_11TranDataCustom c = new IcbcB2cV1_0_0_11TranDataCustom();
		td.setCustom(c);
		c.setVerifyJoinFlag("1");
		
		IcbcB2cV1_0_0_11TranDataMessage m = new IcbcB2cV1_0_0_11TranDataMessage();
		td.setMessage(m);
		
		po.setTranDataObject(td);
		
		System.out.println(po.getTranData());
	}

	/**
	 * @return the interfaceName
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * @param interfaceName the interfaceName to set
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * @return the interfaceVersion
	 */
	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	/**
	 * @param interfaceVersion the interfaceVersion to set
	 */
	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	/**
	 * @return the tranData
	 */
	public String getTranData() {
		return tranData;
	}

	/**
	 * @param tranData the tranData to set
	 */
	public void setTranData(String tranData) {
		try {
			this.tranData = cn.com.infosec.util.Base64.encode(tranData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the merSignMsg
	 */
	public String getMerSignMsg() {
		return merSignMsg;
	}

	/**
	 * @param merSignMsg the merSignMsg to set
	 */
	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	/**
	 * @return the merCert
	 */
	public String getMerCert() {
		return merCert;
	}

	/**
	 * @param merCert the merCert to set
	 */
	public void setMerCert(String merCert) {
		this.merCert = merCert;
	}

	/**
	 * @return the tranDataObject
	 */
	public IcbcB2cV1_0_0_11TranData getTranDataObject() {
		return tranDataObject;
	}

	/**
	 * @param tranDataObject the tranDataObject to set
	 */
	public void setTranDataObject(IcbcB2cV1_0_0_11TranData tranDataObject) {
		this.tranDataObject = tranDataObject;
	}
	
	
}
