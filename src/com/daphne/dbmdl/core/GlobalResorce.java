package com.daphne.dbmdl.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.datasource.ConnectionPoolFactory;
import com.daphne.dbmdl.duty.Duty;
import com.daphne.dbmdl.exception.InitException;
import com.daphne.dbmdl.handler.DataHandler;
import com.daphne.dbmdl.util.XmlParser;
import com.daphne.dbmdl.xml.mapping.DataSourceParam;
import com.daphne.dbmdl.xml.mapping.DbMdlMapping;
import com.daphne.dbmdl.xml.mapping.DutyMapping;

/**
 * 此类描述的是： 在程序启动是加载配置的资源
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午1:44:16
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public class GlobalResorce implements ServletContextListener {

	private static Logger logger = Logger.getLogger(GlobalResorce.class);
	public static final String DUTIES_LIST="dutiesList";
	public static final String HANDLER="handler";
	ServletContext context = null;
	
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ConnectionPoolFactory.destroyed();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.info("init  DBMDL GlobalResorce..................");
		context = arg0.getServletContext();
		try {
			DbMdlMapping ddm = readConfig();
			initConnectionPoolFactory(ddm);
			context.setAttribute(DUTIES_LIST, loadDuty(ddm));
			context.setAttribute(HANDLER,loadHandler(ddm) );
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DbMdlMapping readConfig() {
		return XmlParser.getInstance().toEntity("dbmdl.xml", DbMdlMapping.class);
	}

	@SuppressWarnings("unchecked")
	private List<Duty> loadDuty(DbMdlMapping ddm)
			throws InstantiationException, IllegalAccessException,
			InitException {

		Iterator<DutyMapping> it = ddm.getDuties().iterator();
		List<Duty> resultList = new ArrayList<Duty>();
		while (it.hasNext()) {
			DutyMapping dm = it.next();
			// Class c=
			// ClassLoader.getSystemClassLoader().loadClass(dm.getClassName());
			Class<Duty> c;
			try {
				c = (Class<Duty>) Class.forName(dm.getClassName());
				Duty duty = c.newInstance();
				resultList.add(duty);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error(dm.getDescription() + "指定类名不存在或配置错误");
			}

		}
		if (logger.isDebugEnabled()) {
			String msg = "注册组件： [";
			it = ddm.getDuties().iterator();
			while (it.hasNext()) {
				DutyMapping dm = it.next();
				msg += dm.getClassName() + ":" + dm.getDescription() + ",";
			}
			msg = msg.substring(0, msg.length() - 1);
			msg += "]";
			logger.debug(msg);
		}
		return resultList;
	}

	private void initConnectionPoolFactory(DbMdlMapping ddm) {
		List<DataSourceParam> dataSourceParams = ddm.getDataSourceParam();
		Iterator<DataSourceParam> it = dataSourceParams.iterator();
		while (it.hasNext()) {
			try {
				ConnectionPoolFactory.bind(it.next());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	private DataHandler loadHandler(DbMdlMapping ddm){
		Class<DataHandler> c;
		DataHandler handler = null;
		try {
			c = (Class<DataHandler>) Class.forName(ddm.getHandler().getClassName());
			 handler=c.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return handler;
	}

}
