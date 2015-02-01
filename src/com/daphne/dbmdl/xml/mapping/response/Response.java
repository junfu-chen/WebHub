package com.daphne.dbmdl.xml.mapping.response;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 此类描述的是：
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 19 Jan 2015 13:58:42
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

@XStreamAlias("response")
public class Response {
	@XStreamAsAttribute
	private String headerResult;
	private String error;
	@XStreamImplicit(itemFieldName = "dataset")
	private List<DataSet> dataSetList = new ArrayList<DataSet>();

	public String getHeaderResult() {
		return headerResult;
	}

	public void setHeaderResult(String headerResult) {
		this.headerResult = headerResult;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		if (this.error == null || this.error.equals(""))
			this.error = error;
	}

	public List<DataSet> getDataSetList() {
		return dataSetList;
	}

	public void setDataSetList(List<DataSet> dataSetList) {
		this.dataSetList = dataSetList;
	}

}
