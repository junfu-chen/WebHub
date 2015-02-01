package com.daphne.dbmdl.xml.mapping.re;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Ausapi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313973685639031146L;
	@XStreamAlias("svers")
	@XStreamAsAttribute
	private String sVers;
	@XStreamAlias("sdate")
	@XStreamAsAttribute
	private String sDate;
	@XStreamAsAttribute
	private String dataSourceName;

	public String getsVers() {
		return sVers;
	}

	public void setsVers(String sVers) {
		this.sVers = sVers;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

}
