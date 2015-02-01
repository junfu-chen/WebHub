package com.daphne.dbmdl.xml.mapping;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("p:dbmdl")
public class DbMdlMapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8358115934732420044L;
	@XStreamImplicit(itemFieldName = "dataSource")
	private List<DataSourceParam> dataSourceParam;
	@XStreamImplicit(itemFieldName = "duty")
	private List<DutyMapping> duties;
	private HandlerMapping handler;
	public List<DataSourceParam> getDataSourceParam() {
		return dataSourceParam;
	}

	public void setDataSourceParam(List<DataSourceParam> dataSourceParam) {
		this.dataSourceParam = dataSourceParam;
	}

	public List<DutyMapping> getDuties() {
		return duties;
	}

	public void setDuties(List<DutyMapping> duties) {
		this.duties = duties;
	}

	public HandlerMapping getHandler() {
		return handler;
	}

	public void setHandler(HandlerMapping handler) {
		this.handler = handler;
	}
	

}
