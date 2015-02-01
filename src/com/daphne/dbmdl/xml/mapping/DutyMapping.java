package com.daphne.dbmdl.xml.mapping;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("duty")
public class DutyMapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7174971343812948503L;
	@XStreamAsAttribute
	private String className;
	@XStreamAsAttribute
	private String description;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
