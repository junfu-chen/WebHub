package com.daphne.dbmdl.duty;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.core.DutiesChain;

public abstract class AbstractDuty implements Duty {

	private static Logger logger = Logger.getLogger(AbstractDuty.class);
	protected final String HTTP_REQUEST_IS_NULL = "HttpRequest inputStream is null";
	protected final String HTTP_RESPONSE_IS_NULL = "HttpResponse outputStream is null";
	protected final String HANDLER_IS_NULL = "handler is null";

	public void doDuties(DutiesChain chain) {
		boolean isRolledBack = chain.getIsRolledBack();
		if (isRolledBack) {
			chain.invoke();
		} else {
			boolean isExe = isExecutable(chain);
			if (isExe) {
				before(chain);
			}
			chain.invoke();
			if (isExe) {
				after(chain);
			}
		}
	}

	/**
	 * 此方法描述的是： sql在被执行之前所做的操作
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 22:22:55
	 */

	protected abstract void before(DutiesChain chain);

	/**
	 * 此方法描述的是： sql在被执行之后所做的操作
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 22:24:13
	 */

	protected abstract void after(DutiesChain chain);

	/**
	 * 此方法描述的是：效验当前环境是否允许履行职责
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 22:26:20
	 */

	protected boolean isExecutable(DutiesChain chain) {
		if (chain.getHttpRequestIn() == null) {
			logger.error(HTTP_REQUEST_IS_NULL);
			chain.setXmlResponse(HTTP_REQUEST_IS_NULL);
			return Boolean.FALSE;
		}
		if (chain.getHttpResponseOut() == null) {
			logger.error(HTTP_RESPONSE_IS_NULL);
			chain.setXmlResponse(HTTP_RESPONSE_IS_NULL);
			return Boolean.FALSE;
		}
		if (chain.getHandler() == null) {
			logger.error(HANDLER_IS_NULL);
			chain.setXmlResponse(HANDLER_IS_NULL);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
