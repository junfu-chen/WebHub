package com.daphne.dbmdl.xml.mapping.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("dataset")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = { "jsonData" })
public class DataSet {
	@XStreamAsAttribute
	private String sindex;
	@XStreamAsAttribute
	private String detailResult;
	@XStreamAsAttribute
	private String errorCode;
	@XStreamAsAttribute
	private String errorDesc;
	@XStreamAsAttribute
	private String scount;

	private String jsonData;

	public String getSindex() {
		return sindex;
	}

	public void setSindex(String sindex) {
		this.sindex = sindex;
	}

	public String getDetailResult() {
		return detailResult;
	}

	public void setDetailResult(String detailResult) {
		this.detailResult = detailResult;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getScount() {
		return scount;
	}

	public void setScount(String scount) {
		this.scount = scount;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}
