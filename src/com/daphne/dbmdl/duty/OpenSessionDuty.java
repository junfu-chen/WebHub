package com.daphne.dbmdl.duty;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NameNotFoundException;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.core.DutiesChain;
import com.daphne.dbmdl.datasource.ConnectionPoolFactory;
import com.daphne.dbmdl.xml.mapping.request.MdlRequest;

/**
 * 此类描述的是：请求： 为每个请求创建一个数据库连接 响应： 响应阶段关闭连接
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午1:44:59
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public class OpenSessionDuty extends AbstractDuty {
	private static Logger logger = Logger.getLogger(OpenSessionDuty.class);
	private final String errorMsg = "parse  xml not success";
	private static String SID="DPOS";
	@Override
	public void doDuties(DutiesChain chain) {
		// TODO Auto-generated method stub
		// open connection for request
		boolean isRolledBack = chain.getIsRolledBack();
		if (isRolledBack) {
			chain.invoke();
		} else {
			boolean isExe = isExecutable(chain);
			if (isExe) {
				try {
					before(chain);
					chain.invoke();
					after(chain);
				} catch (Exception e) {
					Connection conn = chain.getMdlRequest().getConnection();
					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// TODO: handle exception
				} finally {
					// close connection
					Connection conn = chain.getMdlRequest().getConnection();
					if (conn != null) {
						try {
							logger.debug("close db session");
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}

	}

	@Override
	protected void before(DutiesChain chain) {
		// TODO Auto-generated method stub
		logger.debug("open db session");
		try {
			MdlRequest request = chain.getMdlRequest();
			Connection conn = null;
			// 临时使用指定数据库名。
			request.setDataSourceName(SID);
			conn = ConnectionPoolFactory.lookup(request.getDataSourceName())
					.getConnection();
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			request.setConnection(conn);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void after(DutiesChain chain) {
		// TODO Auto-generated method stub
		try {
			if(chain.getMdlRequest().getRequest().getAct().equalsIgnoreCase("update")){
				chain.getMdlRequest().getConnection().commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean isExecutable(DutiesChain chain) {
		if (chain.getMdlRequest() == null) {
			logger.error(errorMsg);
			chain.setIsRolledBack(errorMsg);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}
