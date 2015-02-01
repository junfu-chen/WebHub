package com.daphne.dbmdl.xml.mapping.request;

import java.sql.Connection;

import com.daphne.dbmdl.xml.mapping.re.Ausapi;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ausapi")
public class MdlRequest extends Ausapi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6282439349886326969L;

	private Reqeust request;
	@XStreamAlias("signtype")
	private String signType;
	private String sign;
	private Connection connection;

	public Reqeust getRequest() {
		return request;
	}

	public void setRequest(Reqeust request) {
		this.request = request;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
