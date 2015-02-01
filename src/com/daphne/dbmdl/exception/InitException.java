package com.daphne.dbmdl.exception;

public class InitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3975347153918211449L;
	private static String appName = "DBMDL";

	public InitException(MsgType m, String msg) {
		// TODO Auto-generated constructor stub
		super(appName + " : " + "" + m.getContent() + "   " + msg);
	}

	public enum MsgType {

		ERROR("error"), WARN("warn"), INFO("info");
		private String content;

		public String getContent() {
			return this.content;
		}

		private MsgType(String s) {
			this.content = s;
		}

	}

}
