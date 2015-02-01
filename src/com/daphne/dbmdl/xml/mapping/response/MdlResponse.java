package com.daphne.dbmdl.xml.mapping.response;

import com.daphne.dbmdl.xml.mapping.re.Ausapi;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ausapi")
public class MdlResponse extends Ausapi {
	/**
	 * 
	 */
	private static final long serialVersionUID = -269877390236822384L;
	private Response response = new Response();

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
