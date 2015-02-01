package com.daphne.dbmdl.duty;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.core.DutiesChain;
import com.daphne.dbmdl.util.ZipUtil;

/**
 * 此类描述的是： 请求：HttpRequest 的 inputStream 解压成字符串至xmlRequest 响应：xmlResponse
 * 压缩至HttpResponse 的outputStream
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午1:48:16
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public class ZipDuty extends AbstractDuty {

	private static Logger logger = Logger.getLogger(ZipDuty.class);
	
	@Override
	protected void before(DutiesChain chain) {
		// TODO Auto-generated method stub
		
		// request
		try {
			chain.setXmlRequest(ZipUtil.getFirstZipEentityContent(chain.getHttpRequestIn()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void after(DutiesChain chain) {
		// TODO Auto-generated method stub
		if (chain.getXmlResponse() != null) {
			logger.debug("response zip");
			ZipUtil.writeXmlToOutStream(chain.getXmlResponse(),
					chain.getHttpResponseOut());
			chain.setXmlResponse(null);//Optimization
		}
		
	}
}
