package com.daphne.dbmdl.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import com.daphne.dbmdl.xml.mapping.re.Ausapi;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.ByteArrayInputStream;

public class XmlParser {
	protected static String PREFIX_CDATA = "<![CDATA[";
	protected static String SUFFIX_CDATA = "]]>";
	private  static XmlParser  instance= null;
	private  XStream xstreamDom = null;
	private  XStream xstreamXpp = null;
	public static XmlParser getInstance() {
		if (instance == null) {
			instance = new XmlParser();
			instance.initXstream(true);
		}
		return instance;
	}
	
	/**
	 * 此方法描述的是： ausapi 转 xml
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:28:51
	 */

	public  String toXml(Ausapi o) {
		xstreamXpp.processAnnotations(o.getClass());
		return xstreamXpp.toXML(o);
	}

	/**
	 * 此方法描述的是： 将xml转换成指定的class的实例
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:29:45
	 */

	public  <T extends Ausapi> T toObject(String xml,
			Class<? extends Ausapi> clazz) {
		xstreamDom.processAnnotations(clazz);
		//xstreamDom.fromXML(new ByteArrayInputStream(xml));
		return (T) xstreamDom.fromXML(xml);

	}

	/**
	 * 此方法描述的是： 将指定的xml文件转换成指定class的实例.
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:31:02
	 */

	 public <T> T toEntity(String uri, Class<T> c) {
		InputStream in;
		in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(uri);
		if (in == null)
			in = ClassLoader.getSystemResourceAsStream(uri);
		xstreamDom.processAnnotations(c);
		T t = (T) xstreamDom.fromXML(in);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 此方法描述的是： 初始化 XStream对象. 是否需要cdata支持.
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:31:42
	 */

	public  void initXstream(boolean isAddCDATA) {

			xstreamXpp = new XStream(new XppDriver() {
				@Override
				public HierarchicalStreamWriter createWriter(Writer out) {
					// TODO Auto-generated method stub
					return new PrettyPrintWriter(out) {
						boolean isCdata = false;

						@Override
						public void startNode(String name) {
							// TODO Auto-generated method stub

							super.startNode(name);
							isCdata = name.equalsIgnoreCase("dataset");
						}

						@Override
						protected void writeText(QuickWriter writer, String text) {
							// TODO Auto-generated method stub
							if (isCdata) {
								writer.write(PREFIX_CDATA);
								writer.write(text);
								writer.write(SUFFIX_CDATA);
							} else {
								super.writeText(writer, text);
							}
						}
					};
				}
			});

			xstreamDom = new XStream(new DomDriver());

		
	}

}
