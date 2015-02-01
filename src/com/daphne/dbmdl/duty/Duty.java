package com.daphne.dbmdl.duty;

import com.daphne.dbmdl.core.DutiesChain;

/**
 * 此类描述的是： 所有职责都应实现此接口
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午1:23:48
 * @version: 1.0 (根据上一个版本定义)
 * @updateHistory:
 */

public interface Duty {

	/**
	 * 此方法描述的是： 方法参数chain包含 一个方法MdlRequest getMdlRequest()包含所有请求信息
	 * 在此方法中可对请求信息做任何你想要的操作。 你必需在方法中调用 chain.invoke(); 以保证程序继续执行。 之后可以调用方法
	 * MdlResponse getMdlResponse()对响应进行处理
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午1:25:34
	 */

	public void doDuties(DutiesChain chain);

}
