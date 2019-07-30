package com.giveup;

import java.io.InputStream;
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

	public static InputStream runQueryOneStream(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = runQuery(pst, sql, params);
			if (rs.next()) {
				return rs.getBinaryStream(1);
			}
			return null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static Integer runQueryOneInteger(Connection conn, String sql, Object... params) throws Exception {
		return Value.toInteger(runQueryOneColumn(conn, sql, params));
	}

	public static String runQueryOneString(Connection conn, String sql, Object... params) throws Exception {
		return Value.toString(runQueryOneColumn(conn, sql, params));
	}

	public static BigDecimal runQueryOneDecimal(Connection conn, String sql, Object... params) throws Exception {
		return Value.toDecimal(runQueryOneColumn(conn, sql, params));
	}

	public static Long runQueryOneLong(Connection conn, String sql, Object... params) throws Exception {
		return Value.toLong(runQueryOneColumn(conn, sql, params));
	}

	public static Float runQueryOneFloat(Connection conn, String sql, Object... params) throws Exception {
		return Value.toFloat(runQueryOneColumn(conn, sql, params));
	}

	public static Date runQueryOneDate(Connection conn, String sql, Object... params) throws Exception {
		return Value.toDate(runQueryOneColumn(conn, sql, params));
	}

	public static Object runQueryOneColumn(Connection conn, String sql, Object... params) throws Exception {
		Map row = runQueryOne(conn, sql, params);
		if (row == null)
			return null;
		return row.get(row.keySet().iterator().next());
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

	public static int[] runBatch(Connection conn, String sql, List<Object> paramsBatch) throws Exception {
		if (paramsBatch == null)
			paramsBatch = new ArrayList<Object>();
		PreparedStatement pst = null;
		logger.debug(sql);
		int[] sqlNs = new int[] {};
		try {
			pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			for (Object params : paramsBatch) {
				logger.debug(params);
				if (params instanceof List) {
					for (int i = 0; i < ((List) params).size(); i++) {
						pst.setObject(i + 1, ((List) params).get(i));
					}
				} else if (params instanceof Object[]) {
					for (int i = 0; i < ((Object[]) params).length; i++) {
						pst.setObject(i + 1, ((Object[]) params)[i]);
					}
				} else if (params instanceof Object) {
					pst.setObject(1, params);
					pst.addBatch();
				} else {
					pst.setObject(1, params);
					pst.addBatch();
				}
				pst.addBatch();
			}
			long s = System.nanoTime();
			sqlNs = pst.executeBatch();
			long e = System.nanoTime();
			logger.debug("takes: " + (e - s));
		} catch (SQLException e) {
			throw new Exception(e.getMessage() + " sql: " + sql, e);
		} finally {
			if (pst != null)
				pst.close();
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

	public static String buildSql_phraseListByOr(String partSql, int count) {
		if (count == 0)
			return "";
		String sqlPart = " ";
		for (int i = 0; i < count; i++) {
			if (i == 0)
				sqlPart += partSql;
			else
				sqlPart += " or " + partSql;
		}
		sqlPart += " ";
		return sqlPart;
	}

	public static String buildSql_paramSplit(String... params) {
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

	public static String buildSql_placeholderList(int count) {
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

	public static String buildSql_orderBy(String[] sorts, String[] orders, String[] sortPool, String[] sortColumnPool,
			String baseSort, String baseOrder) {
		StringBuilder sqlB = new StringBuilder(" order by ");
		List<String> sortListPool = new ArrayList<String>(Arrays.asList(sortPool));
		List<String> sortColumnListPool = new ArrayList<String>(Arrays.asList(sortColumnPool));
		for (int i = 0; i < sorts.length; i++) {
			if (sorts[i] == null || sorts[i].trim().isEmpty())
				continue;
			int n = sortListPool.indexOf(sorts[i]);
			if (n > -1)
				sqlB.append(sortColumnListPool.get(n));
			else
				throw new InteractRuntimeException("排序字段有误");
			sqlB.append(" ");
			if (orders[i].equals("desc"))
				sqlB.append("desc");
			else if (orders[i].equals("asc"))
				sqlB.append("asc");
			else
				throw new InteractRuntimeException("排序顺序有误");
			sqlB.append(sorts.length == 0 ? "" : ",");
		}
		sqlB.append(baseSort).append(" ").append(baseOrder);
		return sqlB.toString();
	}

//	public static List addParams(List params, String insSplit) {
//		if (insSplit == null)
//			return params;
//		String[] ss = insSplit.split(",");
//		params.addAll(Arrays.asList(ss));
//		return params;
//	}

//	public static String buildConditional(String andOr, String column, String columnEq, String insSplit) {
//		if (insSplit == null)
//			return "";
//		return buildConditional(andOr, column, columnEq, insSplit.split(","));
//	}
//
//	public static String buildConditional(String andOr, String column, String columnEq, String... ins) {
//		String sqlPart = " ";
//
//		if (ins == null || ins.length == 0)
//			return "";
//		columnEq = (columnEq == null || columnEq.trim().isEmpty()) ? "1" : columnEq;
//		if (!columnEq.equals("1") && !columnEq.equals("0"))
//			return "";
//
//		andOr = (andOr == null || andOr.trim().isEmpty()) ? "and" : andOr;
//		if (!andOr.equalsIgnoreCase("and") && !andOr.equalsIgnoreCase("or"))
//			return "";
//		if (ins.length == 1) {
//			sqlPart = andOr + " " + column + (columnEq.equals("1") ? " = " : " != ") + " ? ";
//		} else {
//			sqlPart = andOr + " " + column + (columnEq.equals("1") ? " in " : " not in ") + " (";
//			for (int i = 0; i < ins.length; i++) {
//				if (i == 0)
//					sqlPart += "?";
//				else
//					sqlPart += " , ?";
//			}
//			sqlPart = sqlPart + " )";
//		}
//
//		return sqlPart;
//	}
}
