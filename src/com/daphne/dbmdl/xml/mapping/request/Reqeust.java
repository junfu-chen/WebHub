package com.daphne.dbmdl.xml.mapping.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("request")
public class Reqeust {
	@XStreamAlias("acttype")
	@XStreamAsAttribute
	private String actType;
	@XStreamAsAttribute
	private String act;
	@XStreamAlias("procname")
	@XStreamAsAttribute
	private String procName;
	@XStreamImplicit(itemFieldName = "action")
	private List<Action> actionList;
	@XStreamImplicit(itemFieldName = "param")
	private List<Param> paramList;

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public List<Action> getActionList() {
		return actionList;
	}

	public void setActionList(List<Action> actionList) {
		this.actionList = actionList;
	}

	public List<Param> getParamList() {
		return paramList;
	}

	public void setParamList(List<Param> paramList) {
		this.paramList = paramList;
	}

}
