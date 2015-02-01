package com.daphne.dbmdl.duty;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.core.DutiesChain;
import com.daphne.dbmdl.util.DateUtil;
import com.daphne.dbmdl.util.XmlParser;
import com.daphne.dbmdl.xml.mapping.request.MdlRequest;
import com.daphne.dbmdl.xml.mapping.response.MdlResponse;

/**
 * 此类描述的是： 请求：xmlRequest的内容转换成MdlRequest对象， 响应：在返回阶段将MdlResponse转换成XmlResponse
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午1:46:41
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public class XmlDuty extends AbstractDuty {
	private static Logger logger = Logger.getLogger(XmlDuty.class);
	private final String errorMsg = "Xml content is null or empty";

	@Override
	protected void before(DutiesChain chain) {
		// TODO Auto-generated method stub
		logger.debug("request xml duties");
		MdlRequest request = XmlParser.getInstance().toObject(chain.getXmlRequest(),
				MdlRequest.class);
		chain.setMdlRequest(request);
		chain.setMdlResponse(generateResponse(request));
	}

	@Override
	protected void after(DutiesChain chain) {
		// TODO Auto-generated method stub
		logger.debug("response xml duties");
		chain.setXmlResponse(XmlParser.getInstance().toXml(chain.getMdlResponse()));
		chain.setMdlResponse(null);//Optimization
	}

	private MdlResponse generateResponse(MdlRequest request) {
		MdlResponse response = generateResponse();
		response.setsVers(request.getsVers());
		return response;
	}

	private MdlResponse generateResponse() {
		MdlResponse response = new MdlResponse();
		response.setsDate(DateUtil.getInstance().formatCurrentDateToString());
		return response;
	}

	protected boolean isExecutable(DutiesChain chain) {
		if (chain.getXmlRequest() == null
				|| chain.getXmlRequest().trim().equals("")) {
			logger.error(errorMsg);
			chain.setIsRolledBack(errorMsg);
			chain.setXmlResponse(XmlParser.getInstance().toXml(chain.getMdlResponse()));
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
