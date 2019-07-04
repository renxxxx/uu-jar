package com.giveup;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

	public static List<Object> runQueryThinList(Connection conn, String sql, List<Object> params) throws Exception {
		return runQueryThinList(conn, sql, params.toArray());
	}

	public static List<Object> runQueryThinList(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return parseResultSetOfThinList(runQuery(pst, sql, params));
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static Object runQueryOneColumn(Connection conn, String sql, List<Object> params) throws Exception {
		return runQueryOneColumn(conn, sql, params.toArray());
	}

	public static Integer runQueryOneInteger(Connection conn, String sql, List<Object> params) throws Exception {
		return ValueUtils.toInteger(runQueryOneColumn(conn, sql, params));
	}

	public static Integer runQueryOneInteger(Connection conn, String sql, Object... params) throws Exception {
		return ValueUtils.toInteger(runQueryOneColumn(conn, sql, params));
	}

	public static String runQueryOneString(Connection conn, String sql, List<Object> params) throws Exception {
		return ValueUtils.toString(runQueryOneColumn(conn, sql, params));
	}

	public static String runQueryOneString(Connection conn, String sql, Object... params) throws Exception {
		return ValueUtils.toString(runQueryOneColumn(conn, sql, params));
	}

	public static BigDecimal runQueryOneDecimal(Connection conn, String sql, List<Object> params) throws Exception {
		return ValueUtils.toDecimal(runQueryOneColumn(conn, sql, params));
	}

	public static BigDecimal runQueryOneDecimal(Connection conn, String sql, Object... params) throws Exception {
		return ValueUtils.toDecimal(runQueryOneColumn(conn, sql, params));
	}

	public static Long runQueryOneLong(Connection conn, String sql, List<Object> params) throws Exception {
		return ValueUtils.toLong(runQueryOneColumn(conn, sql, params));
	}

	public static Long runQueryOneLong(Connection conn, String sql, Object... params) throws Exception {
		return ValueUtils.toLong(runQueryOneColumn(conn, sql, params));
	}

	public static Float runQueryOneFloat(Connection conn, String sql, List<Object> params) throws Exception {
		return ValueUtils.toFloat(runQueryOneColumn(conn, sql, params));
	}

	public static Float runQueryOneFloat(Connection conn, String sql, Object... params) throws Exception {
		return ValueUtils.toFloat(runQueryOneColumn(conn, sql, params));
	}

	public static Date runQueryOneDate(Connection conn, String sql, List<Object> params) throws Exception {
		return ValueUtils.toDate(runQueryOneColumn(conn, sql, params));
	}

	public static Date runQueryOneDate(Connection conn, String sql, Object... params) throws Exception {
		return ValueUtils.toDate(runQueryOneColumn(conn, sql, params));
	}

	public static Object runQueryOneColumn(Connection conn, String sql, Object... params) throws Exception {
		Map row = runQueryOne(conn, sql, params);
		if (row == null)
			return null;
		return row.get(row.keySet().iterator().next());
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
		logger.debug(Arrays.toString(params));
		try {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
			long s = System.nanoTime();
			ResultSet rs = pst.executeQuery();
			long e = System.nanoTime();
			logger.debug("takes: " + (e - s));
			return rs;
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

	public static int runUpdateGentle(Connection conn, String sql, List<Object> params) throws Exception {
		return runUpdateGentle(conn, sql, params.toArray());

	}

	public static int runUpdateGentle(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return runUpdate(pst, sql, params);
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
			return 0;
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
		logger.debug(Arrays.toString(params));
		int sqlN = 0;
		try {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
			long s = System.nanoTime();
			sqlN = pst.executeUpdate();
			long e = System.nanoTime();
			logger.debug("takes: " + (e - s));
		} catch (Exception e) {
			throw new Exception(e.getMessage() + " sql: " + sql, e);
		}
		logger.debug("affected: " + sqlN);
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
			long s = System.nanoTime();
			sqlNs = pst.executeBatch();
			long e = System.nanoTime();
			logger.debug("takes: " + (e - s));
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
			long s = System.nanoTime();
			sqlNs = pst.executeBatch();
			long e = System.nanoTime();
			logger.debug("takes: " + (e - s));
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
			Map<String, Object> row = new LinkedHashMap();
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
			Map<String, Object> row = new LinkedHashMap();
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

	public static List addParams(List params, String insSplit) {
		if (insSplit == null)
			return params;
		String[] ss = insSplit.split(",");
		params.addAll(Arrays.asList(ss));
		return params;
	}

	public static String buildConditional(String andOr, String column, String columnEq, String insSplit) {
		if (insSplit == null)
			return "";
		return buildConditional(andOr, column, columnEq, insSplit.split(","));
	}

	public static String buildConditional(String andOr, String column, String columnEq, String... ins) {
		String sqlPart = " ";

		if (ins == null || ins.length == 0)
			return "";
		columnEq = (columnEq == null || columnEq.trim().isEmpty()) ? "1" : columnEq;
		if (!columnEq.equals("1") && !columnEq.equals("0"))
			return "";

		andOr = (andOr == null || andOr.trim().isEmpty()) ? "and" : andOr;
		if (!andOr.equalsIgnoreCase("and") && !andOr.equalsIgnoreCase("or"))
			return "";
		if (ins.length == 1) {
			sqlPart = andOr + " " + column + (columnEq.equals("1") ? " = " : " != ") + " ? ";
		} else {
			sqlPart = andOr + " " + column + (columnEq.equals("1") ? " in " : " not in ") + " (";
			for (int i = 0; i < ins.length; i++) {
				if (i == 0)
					sqlPart += "?";
				else
					sqlPart += " , ?";
			}
			sqlPart = sqlPart + " )";
		}

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
