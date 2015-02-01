package com.daphne.dbmdl.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.daphne.dbmdl.handler.DataHandler;
import com.daphne.dbmdl.util.DateUtil;
import com.daphne.dbmdl.xml.mapping.request.MdlRequest;
import com.daphne.dbmdl.xml.mapping.response.MdlResponse;

/**
 * 此类描述的是： 实现这个接口构建一个责任链调用。
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午2:18:46
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public interface DutiesChain extends Serializable {
	
	public static final String SUCCESS="success";
	public static final String FAILURE="failure";
	public void init(HttpServletRequest context);

	public void destory();

	/**
	 * 此方法描述的是：调用此方法执行下一个Duty，执行过最后一个Duty时执行 Handler 并逆向执行之前的Duty。
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午1:43:26
	 */

	public void invoke();

	/**
	 * 此方法描述的是： 获得一个包含请求信息的Object
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:15:23
	 */

	public MdlRequest getMdlRequest();

	public void setMdlRequest(MdlRequest mdlRequest);

	public void setMdlResponse(MdlResponse mdlResponse);

	public MdlResponse getMdlResponse();

	/**
	 * 此方法描述的是： 当前请求的数据处理器。
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:17:11
	 */

	public DataHandler getHandler();

	public void setHandler(DataHandler handler);

	public InputStream getHttpRequestIn();

	public void setHttpRequestIn(InputStream httpRequestIn);

	public OutputStream getHttpResponseOut();

	public void setHttpResponseOut(OutputStream httpResponseOut);

	/**
	 * 此方法描述的是： 获得一个包含请求信息的xml
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:16:36
	 */

	public String getXmlRequest();

	public void setXmlRequest(String xmlRequest);

	public String getXmlResponse();

	public void setXmlResponse(String xmlResponse);

	/**
	 * 此方法描述的是： 是否不再执行之后的所有
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 23:43:19
	 */

	public Boolean getIsRolledBack();

	/**
	 * 此方法描述的是： 设置一个反馈信息不再执行之后的所有
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 23:38:36
	 */

	public void setIsRolledBack(String msg);

}
