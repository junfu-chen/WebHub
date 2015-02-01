package com.daphne.dbmdl.xml.mapping.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * 此类描述的是：
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 12 Jan 2015 22:03:54
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

@XStreamAlias("action")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = { "content" })
public class Action {
	@XStreamAlias("sindex")
	@XStreamAsAttribute
	private String sIndex;
	@XStreamAlias("isall")
	@XStreamAsAttribute
	private String isAll;
	@XStreamAlias("pageindex")
	@XStreamAsAttribute
	private String pageIndex;
	@XStreamAlias("pagenum")
	@XStreamAsAttribute
	private String pageNum;

	private String content;

	public String getsIndex() {
		return sIndex;
	}

	public void setsIndex(String sIndex) {
		this.sIndex = sIndex;
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	public String getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * 此方法描述的是： 获得要执行的sql
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 12 Jan 2015 22:03:59
	 */

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
