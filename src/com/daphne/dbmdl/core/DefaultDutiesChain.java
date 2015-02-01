package com.daphne.dbmdl.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.duty.Duty;
import com.daphne.dbmdl.handler.DataHandler;
import com.daphne.dbmdl.util.DateUtil;
import com.daphne.dbmdl.xml.mapping.request.MdlRequest;
import com.daphne.dbmdl.xml.mapping.response.MdlResponse;

/**
 * 此类描述的是：
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 12 Jan 2015 23:37:32
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public class DefaultDutiesChain implements DutiesChain {

	private static Logger logger = Logger.getLogger(GlobalResorce.class);

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * 
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -4477788368652335634L;
	private Iterator<Duty> duties;
	private DataHandler handler;
	private MdlRequest mdlRequest;
	private MdlResponse mdlResponse;
	private InputStream httpRequestIn;
	private OutputStream httpResponseOut;
	private String xmlRequest;
	private String xmlResponse;
	private Boolean isRolledBack = Boolean.FALSE;

	/**
	 * 创建一个新的实例 DefaultDutiesChain.
	 * 
	 * @param request
	 */

	@SuppressWarnings("unchecked")
	public DefaultDutiesChain(HttpServletRequest request) {

		// init dutiesList
		Object o = request.getSession().getServletContext()
				.getAttribute(GlobalResorce.DUTIES_LIST);
		if (o == null)
			duties = new ArrayList<Duty>().iterator();
		else {
			duties = ((ArrayList<Duty>) o).iterator();
		}
		Object o1=request.getSession().getServletContext().getAttribute(GlobalResorce.HANDLER);
		handler=(DataHandler) o1;
	}

	public void invoke() {
		// TODO Auto-generated method stub

		if (duties.hasNext()) {
			// 有下一个duty。
			Duty duty = duties.next();

			duty.doDuties(DefaultDutiesChain.this);

		} else {
			// 最后一个duty.
			if (!isRolledBack && mdlRequest != null)
				handler.handler(mdlRequest, mdlResponse);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// init dutiesList
		logger.info("Initialization DefaultDutiesChain.....");
		Object o = request.getSession().getServletContext()
				.getAttribute(GlobalResorce.DUTIES_LIST);
		if (o == null)
			duties = new ArrayList<Duty>().iterator();
		else {
			duties = ((ArrayList<Duty>) o).iterator();
		}
		Object o1=request.getSession().getServletContext().getAttribute(GlobalResorce.HANDLER);
		handler=(DataHandler) o1;
	}

	public void destory() {
		// TODO Auto-generated method stub
		this.duties=null;
		this.handler=null;
		this.httpRequestIn=null;
		this.httpResponseOut=null;
		this.isRolledBack=null;
		this.mdlRequest=null;
		this.mdlResponse=null;
		this.xmlRequest=null;
		this.xmlResponse=null;
	}

	@Override
	public DataHandler getHandler() {
		return handler;
	}

	@Override
	public void setHandler(DataHandler handler) {
		this.handler = handler;
	}

	@Override
	public MdlRequest getMdlRequest() {
		return mdlRequest;
	}

	@Override
	public void setMdlRequest(MdlRequest mdlRequest) {
		this.mdlRequest = mdlRequest;
	}

	@Override
	public MdlResponse getMdlResponse() {
		return mdlResponse;
	}

	@Override
	public void setMdlResponse(MdlResponse mdlResponse) {
		this.mdlResponse = mdlResponse;
	}

	@Override
	public InputStream getHttpRequestIn() {
		return httpRequestIn;
	}

	@Override
	public void setHttpRequestIn(InputStream httpRequestIn) {
		this.httpRequestIn = httpRequestIn;
	}

	@Override
	public OutputStream getHttpResponseOut() {
		return httpResponseOut;
	}

	@Override
	public void setHttpResponseOut(OutputStream httpResponseOut) {
		this.httpResponseOut = httpResponseOut;
	}

	@Override
	public String getXmlRequest() {
		return xmlRequest;
	}

	@Override
	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	@Override
	public String getXmlResponse() {
		return xmlResponse;
	}

	@Override
	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	/**
	 * 此方法描述的是： 是否不再执行之后的所有
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 23:43:19
	 */

	public Boolean getIsRolledBack() {
		return isRolledBack;
	}

	/**
	 * 此方法描述的是： 设置一个反馈信息不再执行之后的所有
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 23:38:36
	 */

	public void setIsRolledBack(String msg) {
		if (this.mdlResponse == null) {
			this.mdlResponse = new MdlResponse();
		}
		this.mdlResponse.setsDate(DateUtil.getInstance().formatCurrentDateToString());
		this.mdlResponse.getResponse().setHeaderResult(DutiesChain.FAILURE);
		this.mdlResponse.getResponse().setError(msg);
		this.isRolledBack = true;
	}

}
