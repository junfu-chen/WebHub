package com.daphne.dbmdl.exception;

public class MdlException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6334445129713771371L;
	private String errorCode;
	private String errorDesc;

	public MdlException(String eCode, String eDesc) {
		// TODO Auto-generated constructor stub
		super();
		this.errorCode = eCode;
		this.errorDesc = eDesc;

	}

	public MdlException(String eCode, String eDesc, Throwable t) {
		// TODO Auto-generated constructor stub
		super(t);
		this.errorCode = eCode;
		this.errorDesc = eDesc;

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

}
