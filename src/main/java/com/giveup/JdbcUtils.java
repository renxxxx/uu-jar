package com.giveup;

import java.sql.Connection;
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

	public static List<Map> runQueryList(Connection conn, String sql, List<Object> params) throws Exception {
		return runQueryList(conn, sql, params.toArray());
	}

	public static List<Map> runQueryList(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return parseResultSetOfList(runQuery(pst, sql, params));
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static Map runQueryOne(Connection conn, String sql, List<Object> params) throws Exception {
		return runQueryOne(conn, sql, params.toArray());
	}

	public static Map runQueryOne(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			List<Map> list = parseResultSetOfList(runQuery(pst, sql, params));
			if (list == null || list.isEmpty())
				return null;
			return list.get(0);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static ResultSet runQuery(PreparedStatement pst, String sql) throws SQLException {
		return runQuery(pst, sql, new Object[] {});
	}

	public static ResultSet runQuery(PreparedStatement pst, String sql, List<Object> params) throws SQLException {
		return runQuery(pst, sql, params.toArray());
	}

	public static ResultSet runQuery(PreparedStatement pst, String sql, Object... params) throws SQLException {
		if (params == null)
			params = new Object[] {};
		logger.debug(sql);
		logger.debug(params);
		try {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
			return pst.executeQuery();
		} catch (Exception e) {
			throw new SQLException(e.getMessage() + " sql: " + sql, e);
		}
	}

	public static String toLikePart(String columnValue) {
		return new StringBuilder("%").append(columnValue).append("%").toString();
	}

	public static int runUpdate(Connection conn, String sql, List<Object> params) throws Exception {
		return runUpdate(conn, sql, params.toArray());
	}

	public static int runUpdate(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return runUpdate(pst, sql, params);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static int runUpdate(PreparedStatement pst, String sql) throws Exception {
		return runUpdate(pst, sql, new Object[] {});
	}

	public static int runUpdate(PreparedStatement pst, String sql, List<Object> params) throws Exception {
		return runUpdate(pst, sql, params.toArray());
	}

	public static int runUpdate(PreparedStatement pst, String sql, Object... params) throws Exception {
		if (params == null)
			params = new Object[] {};
		logger.debug(sql);
		logger.debug(params);
		int sqlN = 0;
		try {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
			sqlN = pst.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e.getMessage() + " sql: " + sql, e);
		}
		logger.debug("affected : " + sqlN);
		return sqlN;
	}

	public static Integer runInsertOneGenKey(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			runUpdate(pst, sql, params);
			return returnGeneratedKey(pst);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
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

	public static int[] runBatch(PreparedStatement pst, String sql, List<List<Object>> paramsBatch) throws Exception {
		if (paramsBatch == null)
			paramsBatch = new ArrayList<List<Object>>();
		logger.debug(sql);
		int[] sqlNs = new int[] {};
		try {
			for (List<Object> params : paramsBatch) {
				logger.debug(params);
				for (int i = 0; i < params.size(); i++) {
					pst.setObject(i + 1, params.get(i));
				}
				pst.addBatch();
			}
			sqlNs = pst.executeBatch();
		} catch (SQLException e) {
			throw new Exception(e.getMessage() + " sql: " + sql, e);
		}
		logger.debug("affected : " + Arrays.toString(sqlNs));
		return sqlNs;
	}

	public static int[] runThinBatch(PreparedStatement pst, String sql, List<Object> paramBatch) throws Exception {
		return runThinBatch(pst, sql, paramBatch.toArray());
	}

	public static int[] runThinBatch(PreparedStatement pst, String sql, Object... paramBatch) throws Exception {
		if (paramBatch == null)
			paramBatch = new Object[] {};
		logger.debug(sql);
		int[] sqlNs = new int[] {};
		try {
			for (int i = 0; i < paramBatch.length; i++) {
				logger.debug(paramBatch[i]);
				pst.setObject(1, paramBatch[i]);
				pst.addBatch();
			}
			sqlNs = pst.executeBatch();
		} catch (SQLException e) {
			throw new Exception(e.getMessage() + " sql: " + sql, e);
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
				Object value = rs.getObject(i);
				row.put(metaData.getColumnLabel(i), value);
			}
			rows.add(row);
			if (rows.size() <= 10)
				logger.debug(row);
		}
		logger.debug("affected : " + rows.size());
		return rows;
	}

	public static List<Map> parseResultSetOfList(ResultSet rs, String[] excludeColumns) throws SQLException {
		List<String> excludeColumnList = Arrays.asList(excludeColumns);
		List<Map> rows = new ArrayList();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCnt = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> row = new HashMap();
			for (int i = 1; i <= columnCnt; i++) {
				String column = metaData.getColumnLabel(i);
				if (excludeColumnList.contains(column))
					continue;
				Object value = rs.getObject(i);
				row.put(metaData.getColumnLabel(i), value);
			}
			rows.add(row);
			if (rows.size() <= 10)
				logger.debug(row);
		}
		logger.debug("affected : " + rows.size());
		return rows;
	}

	public static List<List<Object>> parseResultSetOfValueLists(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCnt = metaData.getColumnCount();

		List<List<Object>> valueLists = new ArrayList<List<Object>>();
		while (rs.next()) {
			List<Object> valueList = new ArrayList<Object>();
			for (int i = 1; i <= columnCnt; i++) {
				Object value = rs.getObject(i);
				valueList.add(value);
			}
			valueLists.add(valueList);
			if (valueLists.size() <= 10)
				logger.debug(valueList);
		}
		logger.debug("affected : " + valueLists.size());
		return valueLists;
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

	public static Integer parseResultSetOfOneInteger(ResultSet rs) throws SQLException {
		Object value = parseResultSetOfOneColumn(rs);
		if (value == null)
			return null;
		else {
			return new Integer(value.toString());
		}
	}

	// public static String buildSqlPart1(int cnt) throws SQLException {
	// StringBuilder ss = new StringBuilder("(");
	// for (int i = 0; i < cnt; i++) {
	// if (i == 0)
	// ss = ss.append("?");
	// else
	// ss = ss.append(",?");
	// }
	// ss.append(")");
	// return ss.toString();
	// }

	public static String buildSqlPartSqlListByOr(String sql, String[] params) {
		if (params == null || params.length == 0)
			return "";
		String sqlPart = " ";
		for (int i = 0; i < params.length; i++) {
			if (i == 0)
				sqlPart += sql;
			else
				sqlPart += " or " + sql;
		}
		sqlPart += " ";
		return sqlPart;
	}

	public static String buildSqlPartParamList(String[] params) {
		if (params == null || params.length == 0)
			return "";
		String sqlPart = "  ";
		for (int i = 0; i < params.length; i++) {
			if (i == 0)
				sqlPart += "'" + params[i] + "'";
			else
				sqlPart += " , '" + params[i] + "'";
		}
		sqlPart += " ";
		return sqlPart;
	}

	public static String buildSqlPartQuestionMarkList(int count) {
		if (count == 0)
			return "";
		String sqlPart = "";
		for (int i = 0; i < count; i++) {
			if (i == 0)
				sqlPart += " ?";
			else
				sqlPart += ",?";
		}
		sqlPart += " ";
		return sqlPart;
	}

	// public static String buildSqlPartOrderBy(String[] sorts, String[] orders)
	// {
	// String ss = " order by ";
	// for (int i = 0; i < sorts.length; i++) {
	// String order = null;
	// if ((orders.length - 1) < i)
	// order = "desc";
	// else
	// order = orders[i];
	// if (i == 0)
	// ss += sorts[i] + " " + order;
	// else
	// ss += " , " + sorts[i] + " " + order;
	// }
	// return ss;
	// }
}
