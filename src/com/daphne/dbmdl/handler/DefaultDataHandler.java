package com.daphne.dbmdl.handler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.daphne.dbmdl.core.DutiesChain;
import com.daphne.dbmdl.exception.MdlException;
import com.daphne.dbmdl.util.JsonParser;
import com.daphne.dbmdl.xml.mapping.request.Action;
import com.daphne.dbmdl.xml.mapping.request.MdlRequest;
import com.daphne.dbmdl.xml.mapping.request.Param;
import com.daphne.dbmdl.xml.mapping.response.DataSet;
import com.daphne.dbmdl.xml.mapping.response.MdlResponse;

public class DefaultDataHandler implements DataHandler {
	private static Logger logger = Logger.getLogger(DefaultDataHandler.class);
	private static final String IN = "in";
	private static final String OUT = "out";
	private static final String STRING = "string";
	private static final String NUMBER = "number";
	private static final String TOJSONERROR = "2";
	private static final String SQLERROR = "1";
	private static final String PAGE_SELECT_PRE = " select * from (select rownum as iindex, t.* from ( ";
	private static final String PAGE_SELECT_SUF = " ) t where rownum <= ? ) where iindex > ?";

	@Override
	public void handler(MdlRequest request, MdlResponse response) {
		// TODO Auto-generated method stub
		String action = request.getRequest().getAct().trim();

		if (DataHandler.SELECT.equalsIgnoreCase(action)) {

			List<Action> as = request.getRequest().getActionList();
			Iterator<Action> it = as.iterator();
			while (it.hasNext()) {
				DataSet ds = new DataSet();
				Action a = it.next();
				try {
					Map<String, String> map = null;
					if ("1".equals(a.getIsAll())) {
						map = getJsonBySql(request.getConnection(),a.getContent());

					} else {
						String sql = this.PAGE_SELECT_PRE + a.getContent()
								+ this.PAGE_SELECT_SUF;
						ArrayList<Object> params = new ArrayList<Object>();
						int index = Integer.valueOf(a.getPageIndex());
						if (index < 1) {
							index = 1;
						}
						int num = Integer.valueOf(a.getPageNum());
						params.add(index * num);
						params.add((index - 1) * num);
						map = getJsonBySql(request.getConnection(),sql, params);
					}

					ds.setJsonData(map.get("jsonData"));
					ds.setScount(map.get("count"));
					ds.setSindex(a.getsIndex());
					ds.setDetailResult(DutiesChain.SUCCESS);
				} catch (MdlException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					e.printStackTrace();
					ds.setDetailResult(DutiesChain.FAILURE);
					ds.setSindex(a.getsIndex());
					ds.setErrorCode(e.getErrorCode());
					ds.setErrorDesc(e.getErrorDesc());
				}
				response.getResponse().getDataSetList().add(ds);
			}

		} else if (DataHandler.UPDATE.equalsIgnoreCase(action)) {
			Connection conn = request.getConnection();
			List<Action> as = request.getRequest().getActionList();
			Iterator<Action> it = as.iterator();

			while (it.hasNext()) {
				DataSet ds = new DataSet();
				int count = 0;
				Action a = it.next();
				try {
					PreparedStatement ps = conn
							.prepareStatement(a.getContent());
					count = ps.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					e.printStackTrace();
					ds.setErrorCode(DutiesChain.FAILURE);
					ds.setErrorDesc(e.getMessage());
					ds.setSindex(a.getsIndex());
				}
				ds.setScount(count + "");
				ds.setSindex(a.getsIndex());
				response.getResponse().getDataSetList().add(ds);
			}

		} else if (DataHandler.PROCEDURE.equalsIgnoreCase(action)) {
			Connection conn = request.getConnection();
			CallableStatement cs = null;
			try {
				cs = conn.prepareCall(request.getRequest().getProcName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Param> params = request.getRequest().getParamList();
			Iterator<Param> it = params.iterator();
			while (it.hasNext()) {
				Param param = it.next();
				if (IN.equalsIgnoreCase(param.getInout().trim())) {
					if (param.getDataType().equalsIgnoreCase(STRING)) {

					}
				}
			}

		}
		response.getResponse().setHeaderResult(DutiesChain.SUCCESS);
		//logger.info(request.getDataSourceName() + " access success!");
	}

	private ResultSet executeQuery(Connection conn,String sql, ArrayList<Object> params)
			throws MdlException {
		try {
			PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if (params != null) {
				for (int i = 1; i <= params.size(); i++) {
					ps.setObject(i, params.get(i - 1));
				}
			}
			ps.executeQuery();
			return ps.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new MdlException(DutiesChain.FAILURE, e.getMessage(), e);
		}

	}

	public Map<String, String> getJsonBySql(Connection conn,String sql) throws MdlException {
		return getJsonBySql(conn,sql, null);
	}

	public Map<String, String> getJsonBySql(Connection conn,String sql, ArrayList<Object> params)
			throws MdlException {
		try {

			ResultSet ds = executeQuery( conn,sql, params);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			int count = 0;
			while (ds.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= ds.getMetaData().getColumnCount(); i++) {
					map.put(ds.getMetaData().getColumnName(i), ds.getObject(i));
				}
				try {
					if(!ds.isLast()){
						sb.append(JsonParser.getInstance().toJson(map)+",");
					}
					else{
						sb.append(JsonParser.getInstance().toJson(map));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MdlException(TOJSONERROR, "to json error");
				}
				count++;
			}
			sb.append("]");
			Map<String, String> result = new HashMap<String, String>();
			result.put("jsonData", sb.toString());
			result.put("count", count + "");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MdlException(SQLERROR, e.getMessage(), e);
		}

	}

}
