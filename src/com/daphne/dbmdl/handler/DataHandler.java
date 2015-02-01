package com.daphne.dbmdl.handler;

import com.daphne.dbmdl.xml.mapping.request.MdlRequest;
import com.daphne.dbmdl.xml.mapping.response.MdlResponse;

/**
 * 此类描述的是： 执行sql，并返回数据.
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午2:21:46
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public interface DataHandler {
	public final String SELECT = "select";
	public final String UPDATE = "update";
	public final String PROCEDURE = "proc";

	public void handler(MdlRequest r, MdlResponse response);

}
