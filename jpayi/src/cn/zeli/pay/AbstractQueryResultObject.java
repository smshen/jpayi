/**
 * 
 */
package cn.zeli.pay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author lz
 *
 */
public abstract class AbstractQueryResultObject implements QueryResultObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6188333912730095302L;

	/* (non-Javadoc)
	 * @see cn.zeli.pay.PayResultObject#verify()
	 */
	public boolean verify() {
		return true;
	}
	
	protected float toFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return 0f;
		}
	}
	
	protected String object2Xml(Object object) {
//		XStream xstream = new XStream();
		XStream xstream = new XStream(new DomDriver());// does not require XPP3 library
		
		return null;
	}
	
	protected Object xml2Object(String xml) {
		XStream xstream = new XStream(new DomDriver());
		Object object = (Object) xstream.fromXML(xml);
		return object;
	}

}
