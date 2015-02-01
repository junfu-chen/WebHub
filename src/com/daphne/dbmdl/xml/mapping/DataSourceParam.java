package com.daphne.dbmdl.xml.mapping;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("dataSource")
public class DataSourceParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6153795896516915124L;
	@XStreamAsAttribute
	private String dataSourceName;
	@XStreamAsAttribute
	private String userName;
	@XStreamAsAttribute
	private String passWord;
	@XStreamAsAttribute
	private String JdbcUrl;
	@XStreamAsAttribute
	private String driverClass;
	@XStreamAsAttribute
	private String initialPoolSize;
	@XStreamAsAttribute
	private String minPoolSize;
	@XStreamAsAttribute
	private String maxPoolSize;
	@XStreamAsAttribute
	private String maxStatements;
	@XStreamAsAttribute
	private String maxIdleTime;

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getJdbcUrl() {
		return JdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		JdbcUrl = jdbcUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(String initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public String getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(String maxStatements) {
		this.maxStatements = maxStatements;
	}

	public String getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(String maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (super.equals(obj))
			return true;
		else {
			DataSourceParam p = (DataSourceParam) obj;
			if (this.JdbcUrl.trim().equals(p.getJdbcUrl().trim())) {
				if (this.userName.trim().equals(p.getUserName().trim())) {
					return true;
				}
				return false;
			}
			return false;
		}

	}
}
