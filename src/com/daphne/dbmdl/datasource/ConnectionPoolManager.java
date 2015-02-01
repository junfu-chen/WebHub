package com.daphne.dbmdl.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.daphne.dbmdl.xml.mapping.DataSourceParam;
import com.mchange.v2.c3p0.DataSources;

public class ConnectionPoolManager {
	private DataSource dataSource;
	private DataSourceParam param;

	/**
	 * 此方法描述的是： 获得连接，此方法确保连接池的数据是同步的。
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:08:57
	 */

	public synchronized Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */

	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		DataSources.destroy(dataSource);
		super.finalize();
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSourceParam getParam() {
		return param;
	}

	public void setParam(DataSourceParam param) {
		this.param = param;
	}

}
