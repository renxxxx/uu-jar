package com.inininininin.util;

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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class JdbcUtil {
	public static Logger logger = Logger.getLogger(JdbcUtil.class);

	public static void main(String[] args) {
		System.out.println(123123123);
	}

	public static List<Map> queryList(Connection conn, String sql, Object... sqlParams) throws Exception {
		logger.info("in " + RandomStringUtils.randomNumeric(5));
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return parseResultSetOfList(query(pst, sql, sqlParams));
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
			logger.info("out " + RandomStringUtils.randomNumeric(5));
		}
	}

	public static List<Object> queryThinList(Connection conn, String sql, Object... sqlParams) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return parseResultSetOfThinList(query(pst, sql, sqlParams));
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static InputStream queryStream(Connection conn, String sql, Object... sqlParams) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = query(pst, sql, sqlParams);
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

	public static Integer queryInteger(Connection conn, String sql, Object... sqlParams) throws Exception {
		return Value.toInteger(queryColumn(conn, sql, sqlParams));
	}

	public static String queryString(Connection conn, String sql, Object... sqlParams) throws Exception {
		return Value.toString(queryColumn(conn, sql, sqlParams));
	}

	public static BigDecimal queryDecimal(Connection conn, String sql, Object... sqlParams) throws Exception {
		return Value.toDecimal(queryColumn(conn, sql, sqlParams));
	}

	public static Long queryLong(Connection conn, String sql, Object... sqlParams) throws Exception {
		return Value.toLong(queryColumn(conn, sql, sqlParams));
	}

	public static Float queryFloat(Connection conn, String sql, Object... sqlParams) throws Exception {
		return Value.toFloat(queryColumn(conn, sql, sqlParams));
	}

	public static Date queryDate(Connection conn, String sql, Object... sqlParams) throws Exception {
		return Value.toDate(queryColumn(conn, sql, sqlParams));
	}

	public static Object queryColumn(Connection conn, String sql, Object... sqlParams) throws Exception {
		Map row = query(conn, sql, sqlParams);
		if (row == null)
			return null;
		return row.get(row.keySet().iterator().next());
	}

//	public static Map query(Connection conn, String sql, Object... sqlParams) throws Exception {
//		logger.info("in " + RandomStringUtils.randomNumeric(5));
//		PreparedStatement pst = null;
//		try {
//			pst = conn.prepareStatement(sql);
//			List<Map> list = parseResultSetOfList(query(pst, sql, sqlParams));
//			if (list == null || list.isEmpty())
//				return null;
//			return list.get(0);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			if (pst != null)
//				pst.close();
//			logger.info("out " + RandomStringUtils.randomNumeric(5));
//		}
//	}

	public static Map query(Connection conn, String sql, Object... sqlParams) throws Exception {
		logger.info("in " + RandomStringUtils.randomNumeric(5));
		Map item = null;
		List<Map> itemList = queryList(conn, sql, sqlParams);
		if (itemList != null && itemList.size() >= 0) {
			item = itemList.get(0);
		}
		logger.info("out " + RandomStringUtils.randomNumeric(5));
		return item;
	}

	public static ResultSet query(PreparedStatement pst, String sql, Object... sqlParams) throws SQLException {
		if (sqlParams == null)
			sqlParams = new Object[] {};
		logger.debug(sql);
		logger.debug(Arrays.toString(sqlParams));
		try {
			for (int i = 0; i < sqlParams.length; i++) {
				Object sqlParam = sqlParams[i];
				if (sqlParam instanceof Value) {
					sqlParam = ((Value) sqlParam).val();
				}
				pst.setObject(i + 1, sqlParam);
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

//	public static String toLikePart(String columnValue) {
//		return new StringBuilder("%").append(columnValue).append("%").toString();
//	}

	public static int update(Connection conn, String sql, Object... sqlParams) throws Exception {
		logger.info("in " + RandomStringUtils.randomNumeric(5));
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return update(pst, sql, sqlParams);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
			logger.info("out " + RandomStringUtils.randomNumeric(5));
		}

	}

	public static int updateGentle(Connection conn, String sql, Object... sqlParams) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return update(pst, sql, sqlParams);
		} catch (Exception e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
			return 0;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static int update(PreparedStatement pst, String sql, Object... sqlParams) throws Exception {
		if (sqlParams == null)
			sqlParams = new Object[] {};
		logger.debug(sql);
		logger.debug(Arrays.toString(sqlParams));
		int sqlN = 0;
		try {
			for (int i = 0; i < sqlParams.length; i++) {
				Object sqlParam = sqlParams[i];
				if (sqlParam instanceof Value) {
					sqlParam = ((Value) sqlParam).val();
				}
				pst.setObject(i + 1, sqlParam);
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

//	public static Integer runInsertOneGenKey(Connection conn, String sql, Object... sqlParams) throws Exception {
//		PreparedStatement pst = null;
//		try {
//			pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//			update(pst, sql, sqlParams);
//			return returnGeneratedKey(pst);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			if (pst != null)
//				pst.close();
//		}
//	}
//
//	public static Integer returnGeneratedKey(PreparedStatement pst) throws SQLException {
//		ResultSet rs = pst.getGeneratedKeys();
//		if (rs.next()) {
//			int id = rs.getInt(1);
//			rs.close();
//			return id;
//		} else
//			return null;
//	}
//
//	public static List<Integer> returnGeneratedKeys(PreparedStatement pst) throws SQLException {
//		ResultSet rs = pst.getGeneratedKeys();
//		List<Integer> keys = new ArrayList();
//		while (rs.next()) {
//			int key = rs.getInt(1);
//			keys.add(key);
//		}
//		rs.close();
//		return keys;
//	}

	public static int[] batch(Connection conn, String sql, Object... sqlParamBatches) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return batch(pst, sql, sqlParamBatches);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static int[] batch(PreparedStatement pst, String sql, Object... sqlParamBatches) throws Exception {
		if (sqlParamBatches == null)
			sqlParamBatches = new Object[] {};
		logger.debug(sql);
		int[] sqlNs = new int[] {};
		for (Object param : sqlParamBatches) {
			logger.debug(param);
			if (param instanceof List) {
				for (int i = 0; i < ((List) param).size(); i++) {
					pst.setObject(i + 1, ((List) param).get(i));
				}
			} else if (param instanceof Object[]) {
				for (int i = 0; i < ((Object[]) param).length; i++) {
					pst.setObject(i + 1, ((Object[]) param)[i]);
				}
			} else if (param instanceof Object) {
				pst.setObject(1, param);
			} else {
				pst.setObject(1, param);
			}
			pst.addBatch();
		}
		long s = System.nanoTime();
		sqlNs = pst.executeBatch();
		long e = System.nanoTime();
		logger.debug("takes: " + (e - s));
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
//
//	public static String buildSql_phraseListByOr(String partSql, int count) {
//		if (count == 0)
//			return "";
//		String sqlPart = " ";
//		for (int i = 0; i < count; i++) {
//			if (i == 0)
//				sqlPart += partSql;
//			else
//				sqlPart += " or " + partSql;
//		}
//		sqlPart += " ";
//		return sqlPart;
//	}
//
//	public static String buildSql_sqlParamsplit(String... sqlParams) {
//		if (sqlParams == null || sqlParams.length == 0)
//			return "";
//		String sqlPart = "  ";
//		for (int i = 0; i < sqlParams.length; i++) {
//			if (i == 0)
//				sqlPart += "'" + sqlParams[i] + "'";
//			else
//				sqlPart += " , '" + sqlParams[i] + "'";
//		}
//		sqlPart += " ";
//		return sqlPart;
//	}
//
//	public static String buildSql_placeholderList(int count) {
//		if (count == 0)
//			return "";
//		String sqlPart = "";
//		for (int i = 0; i < count; i++) {
//			if (i == 0)
//				sqlPart += " ?";
//			else
//				sqlPart += ",?";
//		}
//		sqlPart += " ";
//		return sqlPart;
//	}

//	public static String buildSql_orderBy(String[] sorts, String[] orders, String[] sortPool, String[] sortColumnPool,
//			String baseSort, String baseOrder) {
//		StringBuilder sqlB = new StringBuilder(" order by ");
//		if (sorts == null || sorts.length == 0)
//			sorts = new String[] {};
//		if (orders == null || sorts.length == 0) {
//			orders = new String[sorts.length];
//			for (int i = 0; i < orders.length; i++) {
//				orders[i] = "desc";
//			}
//		}
//
//		List<String> sortListPool = sortPool == null ? new ArrayList<String>()
//				: new ArrayList<String>(Arrays.asList(sortPool));
//		List<String> sortColumnListPool = sortColumnPool == null ? new ArrayList<String>()
//				: new ArrayList<String>(Arrays.asList(sortColumnPool));
//		for (int i = 0; i < sorts.length; i++) {
//			if (sorts[i] == null || sorts[i].trim().isEmpty())
//				continue;
//			int n = sortListPool.indexOf(sorts[i]);
//			if (n > -1)
//				sqlB.append(sortColumnListPool.get(n));
//			else
//				throw ModuleBreak.failure("排序字段有误");
//			sqlB.append(" ");
//			if (orders[i].equals("desc"))
//				sqlB.append("desc");
//			else if (orders[i].equals("asc"))
//				sqlB.append("asc");
//			else
//				throw ModuleBreak.failure("排序顺序有误");
//			sqlB.append(sorts.length == 0 ? "" : ",");
//		}
//		sqlB.append(baseSort).append(" ").append(baseOrder);
//		return sqlB.toString();
//	}

//	public static List addsqlParams(List sqlParams, String insSplit) {
//		if (insSplit == null)
//			return sqlParams;
//		String[] ss = insSplit.split(",");
//		sqlParams.addAll(Arrays.asList(ss));
//		return sqlParams;
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
