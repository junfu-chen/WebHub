package com.daphne.dbmdl.xml.mapping.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("param")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = { "value" })
public class Param {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String inout;
	@XStreamAlias("datatype")
	@XStreamAsAttribute
	private String dataType;
	@XStreamAsAttribute
	private String size;

	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInout() {
		return inout;
	}

	public void setInout(String inout) {
		this.inout = inout;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
