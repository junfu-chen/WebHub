package com.daphne.dbmdl.datasource;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.NameNotFoundException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.daphne.dbmdl.core.Constant;
import com.daphne.dbmdl.exception.MdlException;
import com.daphne.dbmdl.util.EncryptUtil;
import com.daphne.dbmdl.xml.mapping.DataSourceParam;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 此类描述的是： 管理所有数据源
 * 
 * @author: junfu.chen@mail.daphne.com.cn
 * @created: 2014年12月23日 下午2:02:32
 * @version: (根据上一个版本定义)
 * @updateHistory:
 */

public class ConnectionPoolFactory {
	private static Logger logger = Logger
			.getLogger(ConnectionPoolFactory.class);
	private static Hashtable<String, ConnectionPoolManager> connectionPools = new Hashtable<String, ConnectionPoolManager>();

	/**
	 * 此方法描述的是： dataSourceName在dbmdl.xml配置文件中dataSource的一个属性。通过他可以获得对应的数据源
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:03:20
	 */

	public static ConnectionPoolManager lookup(String dataSourceName)
			throws NameNotFoundException {
		return connectionPools.get(dataSourceName);
	}

	/**
	 * 此方法描述的是： 添加一个数据源通过DataSourceParam，你应该通过这个方法创建一个数据源以确保唯一。
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:04:43
	 */

	public static void bind(DataSourceParam param) throws Exception {
		if (connectionPools.get(param.getDataSourceName()) != null) {
			logger.error("dataSource bind failure connectionName already exists!");
		} else if (isExistsConnectionPool(param)) {
			logger.error("dataSource bind failure dataSource already exists!");

		} else {
			DataSource ds = createDataSource(param);
			ConnectionPoolManager cpm = new ConnectionPoolManager();
			cpm.setDataSource(ds);
			cpm.setParam(param);
			connectionPools.put(param.getDataSourceName(), cpm);
			logger.info("dataSource bind success!");
		}
	}

	/**
	 * 此方法描述的是： 从工厂中去除数据源，并销毁。
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:06:44
	 */

	public static void unbind(String name) throws Exception {
		if (connectionPools.get(name) != null) {
			try {
				connectionPools.get(name).finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPools.remove(name);
			logger.info("dataSource unbind success!");

		}
	}

	/**
	 * 此方法描述的是： 销毁所有数据源
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:07:37
	 */

	public static void destroyed() {
		Set<Entry<String, ConnectionPoolManager>> set = connectionPools
				.entrySet();
		Iterator<Entry<String, ConnectionPoolManager>> it = set.iterator();
		logger.info("destroyed datasource.......");
		while (it.hasNext()) {
			Entry<String, ConnectionPoolManager> e = it.next();
			ConnectionPoolManager cpm = e.getValue();

			try {
				cpm.finalize();
			} catch (Throwable f) {
				f.printStackTrace();
			}
		}
	}

	private static DataSource createDataSource(DataSourceParam param)
			throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setUser(EncryptUtil.decrypt(param.getUserName(), Constant.Key));
		} catch (MdlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ds.setPassword(EncryptUtil.decrypt(param.getPassWord(),Constant.Key));
		} catch (MdlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds.setJdbcUrl(param.getJdbcUrl());
		ds.setDriverClass(param.getDriverClass());
		ds.setInitialPoolSize(Integer.parseInt(param.getInitialPoolSize()));
		ds.setMinPoolSize(Integer.parseInt(param.getMinPoolSize()));
		ds.setMaxPoolSize(Integer.parseInt(param.getMaxPoolSize()));
		ds.setMaxStatements(Integer.parseInt(param.getMaxStatements()));
		ds.setMaxIdleTime(Integer.parseInt(param.getMaxIdleTime()));
		try {
			ds.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ds;
	}

	private static boolean isExistsConnectionPool(DataSourceParam param) {
		Set<Entry<String, ConnectionPoolManager>> set = connectionPools
				.entrySet();
		Iterator<Entry<String, ConnectionPoolManager>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, ConnectionPoolManager> e = it.next();
			ConnectionPoolManager cpm = e.getValue();
			if (cpm.getParam().equals(param)) {
				return true;
			}

		}
		return false;
	}
}
