package com.giveup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class JdbcUtils {
	public static Logger logger = Logger.getLogger(JdbcUtils.class);

	public static ResultSet runQuery(PreparedStatement pst, String sql, Object param) throws SQLException {
		return runQuery(pst, sql, Arrays.asList(new Object[] { param }));
	}

	public static ResultSet runQuery(PreparedStatement pst, String sql, List<Object> params) throws SQLException {
		if (params == null)
			params = new ArrayList<Object>();
		logger.debug(sql);
		logger.debug(params);
		for (int i = 0; i < params.size(); i++) {
			pst.setObject(i + 1, params.get(i));
		}
		try {
			return pst.executeQuery();
		} catch (SQLException e) {
			if (e.getMessage().contains("You have an error in your SQL syntax"))
				throw new SQLException(e.getMessage() + " sql: " + sql);
			else
				throw e;
		}
	}

	public static int runUpdate(PreparedStatement pst, String sql, Object param) throws SQLException {
		return runUpdate(pst, sql, Arrays.asList(new Object[] { param }));
	}

	public static int runUpdate(PreparedStatement pst, String sql, List<Object> params) throws SQLException {
		if (params == null)
			params = new ArrayList<Object>();
		logger.debug(sql);
		logger.debug(params);
		for (int i = 0; i < params.size(); i++) {
			pst.setObject(i + 1, params.get(i));
		}
		int sqlN = 0;
		try {
			sqlN = pst.executeUpdate();
		} catch (SQLException e) {
			if (e.getMessage().contains("You have an error in your SQL syntax"))
				throw new SQLException(e.getMessage() + " sql: " + sql);
			else
				throw e;
		}
		logger.debug("affected : " + sqlN);
		return sqlN;
	}

	public static Integer returnGeneratedKey(PreparedStatement pst) throws SQLException {
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(1);
			rs.close();
			return id;
		} else
			return null;
	}

	public static List<Integer> returnGeneratedKeys(PreparedStatement pst) throws SQLException {
		ResultSet rs = pst.getGeneratedKeys();
		List<Integer> keys = new ArrayList();
		while (rs.next()) {
			int key = rs.getInt(1);
			keys.add(key);
		}
		rs.close();
		return keys;
	}

	public static int[] runBatch(PreparedStatement pst, String sql, List<List<Object>> paramBatches)
			throws SQLException {
		if (paramBatches == null)
			paramBatches = new ArrayList<List<Object>>();
		logger.debug(sql);
		for (List<Object> params : paramBatches) {
			logger.debug(params);
			for (int i = 0; i < params.size(); i++) {
				pst.setObject(i + 1, params.get(i));
			}
			pst.addBatch();
		}
		int[] sqlNs = new int[] {};
		try {
			sqlNs = pst.executeBatch();
		} catch (SQLException e) {
			if (e.getMessage().contains("You have an error in your SQL syntax"))
				throw new SQLException(e.getMessage() + " sql: " + sql);
			else
				throw e;
		}
		logger.debug("affected : " + Arrays.toString(sqlNs));
		return sqlNs;
	}

	public static int[] runThinBatch(PreparedStatement pst, String sql, List<Object> paramBatches) throws SQLException {
		if (paramBatches == null)
			paramBatches = new ArrayList<Object>();
		logger.debug(sql);
		for (int i = 0; i < paramBatches.size(); i++) {
			logger.debug(paramBatches.get(i));
			pst.setObject(1, paramBatches.get(i));
			pst.addBatch();
		}

		int[] sqlNs = new int[] {};
		try {
			sqlNs = pst.executeBatch();
		} catch (SQLException e) {
			if (e.getMessage().contains("You have an error in your SQL syntax"))
				throw new SQLException(e.getMessage() + " sql: " + sql);
			else
				throw e;
		}
		logger.debug("affected : " + Arrays.toString(sqlNs));
		return sqlNs;
	}

	public static List<Map> parseResultSetOfList(ResultSet rs) throws SQLException {
		List<Map> rows = new ArrayList();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCnt = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> row = new HashMap();
			for (int i = 1; i <= columnCnt; i++) {
				row.put(metaData.getColumnLabel(i), rs.getObject(i));
			}
			rows.add(row);
			if (rows.size() <= 10)
				logger.debug(row);
		}
		logger.debug("affected : " + rows.size());
		return rows;
	}

	public static List<Object> parseResultSetOfThinList(ResultSet rs) throws SQLException {
		List<Object> rows = new ArrayList();
		ResultSetMetaData metaData = rs.getMetaData();
		while (rs.next()) {
			Object value = rs.getObject(1);
			rows.add(value);
			if (rows.size() <= 10)
				logger.debug(new StringBuilder("[").append(value).append("]").toString());
		}
		logger.debug("affected : " + rows.size());
		return rows;
	}

	public static Map parseResultSetOfOne(ResultSet rs) throws SQLException {
		List<Map> rows = parseResultSetOfList(rs);
		if (rows.size() > 0)
			return rows.get(0);
		else
			return null;
	}

	public static Object parseResultSetOfOneColumn(ResultSet rs) throws SQLException {
		List<Map> rows = parseResultSetOfList(rs);
		if (rows.size() > 0)
			return rows.get(0).get(rows.get(0).keySet().iterator().next());
		else
			return null;
	}

	public static String buildSqlPart1(int cnt) throws SQLException {
		StringBuilder ss = new StringBuilder("(");
		for (int i = 0; i < cnt; i++) {
			if (i == 0)
				ss = ss.append("?");
			else
				ss = ss.append(",?");
		}
		ss.append(")");
		return ss.toString();
	}
}
