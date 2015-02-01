package com.daphne.dbmdl.duty;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.core.Constant;
import com.daphne.dbmdl.core.DutiesChain;
import com.daphne.dbmdl.exception.MdlException;
import com.daphne.dbmdl.handler.DataHandler;
import com.daphne.dbmdl.util.EncryptUtil;
import com.daphne.dbmdl.xml.mapping.request.Action;
import com.daphne.dbmdl.xml.mapping.request.MdlRequest;

public class SecurityDuty extends AbstractDuty {
	private static Logger logger = Logger.getLogger(SecurityDuty.class);
	private final String REQUEST_IS_NULL = "do SecurityDuty failure because the mdlRequest or mdlResponse is null";

	@Override
	protected void before(DutiesChain chain) {
		// TODO Auto-generated method stub
		logger.debug("request security");
		MD5Validate(chain);
		Decrypt(chain);
	}

	

	@Override
	protected void after(DutiesChain chain) {
		// TODO Auto-generated method stub
		logger.debug("response security");
	}

	protected boolean isExecutable(DutiesChain chain) {
		if (chain.getMdlRequest() == null || chain.getMdlResponse() == null) {
			logger.error(REQUEST_IS_NULL);
			chain.setIsRolledBack(REQUEST_IS_NULL);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private  void MD5Validate(DutiesChain chain) {
		MdlRequest request=chain.getMdlRequest();
		List<Action> as = request.getRequest().getActionList();
		Iterator<Action> it = as.iterator();
		StringBuilder sb = new StringBuilder();
		boolean isSelect = request.getRequest().getAct()
				.equalsIgnoreCase(DataHandler.SELECT);
		while (it.hasNext()) {
			Action a = it.next();
			sb.append(request.getRequest().getAct());
			if (isSelect) {
				sb.append(a.getIsAll());
				sb.append(a.getPageIndex());
				sb.append(a.getPageNum());
			}
			sb.append(a.getContent());
		}
		try {
			 if(!request.getSign().equals(
						EncryptUtil.getMD5String(sb.toString().getBytes("UTF-8")))){
				 chain.setIsRolledBack("MD5 failure");
			 }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void Decrypt(DutiesChain chain) {
		List<Action> as = chain.getMdlRequest().getRequest().getActionList();
		Iterator<Action> it = as.iterator();
		while (it.hasNext()) {
			Action a = it.next();
			try {
				a.setContent(EncryptUtil.decrypt(a.getContent(), Constant.Key));
			} catch (MdlException e) {
				// TODO Auto-generated catch block
				chain.setIsRolledBack("decrypt failure");
			}
		}
	}
}
