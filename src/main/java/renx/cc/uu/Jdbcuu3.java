package renx.cc.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Jdbcuu3 {
	private static Logger logger = LoggerFactory.getLogger(Jdbcuu3.class);

	public static void main(String[] args) {
		Object value = "";
		String sql = "";
		System.out.println(sql += value == null ? ""
				: value.toString().isEmpty() ? "`" + null + "`" + "=null," : "`" + null + "`" + "=?,");
	}

	public static LList<Map> rows(Connection conn, String sql, LList params) throws Exception {
		return rows(conn, sql, params.toArray());
	}

	public static LList<Map> rows(Connection conn, String sql, List params) throws Exception {
		return rows(conn, sql, LList.build(params));
	}

	public static LList<Map> rows(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return rows(selectSql(conn, pst, sql, params));
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

//	public static List<Object> thinRows(Connection conn, String sql, Object... params) throws Exception {
//		PreparedStatement pst = null;
//		try {
//			pst = conn.prepareStatement(sql);
//			return thinRows(select(conn, pst, sql, params));
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			if (pst != null)
//				pst.close();
//		}
//	}

	public static InputStream getStream(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = selectSql(conn, pst, sql, params);
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

	public static Integer getInteger(Connection conn, String sql, Object... params) throws Exception {
		return Var.toInteger(getObject(conn, sql, params));
	}

	public static String getString(Connection conn, String sql, Object... params) throws Exception {
		return Var.toString(getObject(conn, sql, params));
	}

	public static LList getList(Connection conn, String sql, Object... params) throws Exception {
		return Var.toList(getObject(conn, sql, params));
	}

	public static MMap getMap(Connection conn, String sql, Object... params) throws Exception {
		return Var.toMap(getObject(conn, sql, params));
	}

	public static BigDecimal getDecimal(Connection conn, String sql, Object... params) throws Exception {
		return Var.toDecimal(getObject(conn, sql, params));
	}

	public static Long getLong(Connection conn, String sql, Object... params) throws Exception {
		return Var.toLong(getObject(conn, sql, params));
	}

	public static Float getFloat(Connection conn, String sql, Object... params) throws Exception {
		return Var.toFloat(getObject(conn, sql, params));
	}

	public static Date getDate(Connection conn, String sql, Object... params) throws Exception {
		return Var.toDate(getObject(conn, sql, params));
	}

	public static Var get(Connection conn, String sql, Object... params) throws Exception {
		return Var.build(getObject(conn, sql, params));
	}

	public static Object getObject(Connection conn, String sql, Object... params) throws Exception {
		MMap row = row(conn, sql, params);
		if (row.isEmpty())
			return null;
		return row.get(row.keySet().iterator().next());
	}

//	public static Mappquery(Connection conn, String sql, Object... params) throws Exception {
//		logger.info("in " + RandomStringUtils.randomNumeric(5));
//		PreparedStatement pst = null;
//		try {
//			pst = conn.prepareStatement(sql);
//			List<Map> list = parseResultSetOfList(query(pst, sql, params));
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

	public static MMap row(Connection conn, String sql, LList params) throws Exception {
		return row(conn, sql, params.toArray());
	}

	public static MMap row(Connection conn, String sql, List params) throws Exception {
		return row(conn, sql, LList.build(params));
	}

	public static MMap row(Connection conn, String sql, Object... params) throws Exception {
		MMap item = MMap.build();
		LList itemList = rows(conn, sql, params);
		if (itemList.size() == 1) {
			item = itemList.getMap(0);
		}
		return item;
	}

	public static LList select(Connection conn, Object table, Object order, Object limit, Object[] columns,
			MMap conditionm) throws Exception {
		if (limit == null || limit.toString() == null || limit.toString().isEmpty())
			limit = "1,20";
		String sql = "";
		sql += "select ";
		for (int i = 0; i < columns.length; i++) {
			sql += " `" + columns[i] + "` " + ",";
		}
		sql = sql.substring(0, sql.lastIndexOf(","));
		sql += " from ";
		sql += "`" + table + "`" + " ";

		LList params = LList.build();
		if (conditionm.isExisting()) {
			sql += " where 1=1 and ";
			for (Iterator iterator = conditionm.map.keySet().iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				Object value = conditionm.get(key);
				sql += value == null || value.toString() == null || value.toString().isEmpty() ? ""
						: "`" + key + "`" + "=? and ";
				params.addIf(value, value != null && value.toString() != null && !value.toString().isEmpty());
			}
			sql = sql.substring(0, sql.lastIndexOf("and"));
		}
		if (order != null && order.toString() != null && !order.toString().isEmpty()) {
			sql += " order by " + order + " ";
		}
		if (limit != null && limit.toString() != null && !limit.toString().isEmpty()) {
			sql += " limit " + limit + " ";
		}
		return rows(conn, sql, params);
	}

	public static LList selectList(Connection conn, Object table, Object order, Object limit, Object splitColumns,
			Object splitConditionColumns, Object... conditionValues) throws Exception {
		Object[] conditionColumns = StringUtils.splitByWholeSeparatorPreserveAllTokens((String) splitConditionColumns,
				",");
		MMap conditionm = new MMap();
		conditionColumns = conditionColumns == null ? new Object[] {} : conditionColumns;
		conditionValues = conditionValues == null ? new Object[] {} : conditionValues;
		for (int i = 0; i < conditionColumns.length; i++) {
			conditionm.put((String) conditionColumns[i], conditionValues[i]);
		}

		Object[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens((String) splitColumns, ",");
		return select(conn, table, order, limit, columns, conditionm);
	}

	public static MMap selectById(Connection conn, String table, Object splitColumns, Object id) throws Exception {
		return selectOne(conn, table, splitColumns, "id", id);
	}

	public static MMap selectOne(Connection conn, String table, Object splitColumns, Object splitConditionColumns,
			Object... conditionValues) throws Exception {
		LList rows = selectList(conn, table, "", "2", splitColumns, splitConditionColumns, conditionValues);
		if (rows.size() > 1)
			return MMap.build();
		return rows.getMap(0);
	}

	public static ResultSet selectSql(Connection conn, PreparedStatement pst, String sql, Object... params)
			throws Exception {
		if (params == null)
			params = new Object[] {};
		sql = sql.trim().replaceAll("\\s+", " ");
		String sqlNo = new SimpleDateFormat("YYYYMMDDHHmmssSSS").format(new Date())
				+ RandomStringUtils.randomNumeric(8);
		logger.info("[" + sql + "]" + " " + Arrays.toString(params) + " " + sqlNo);
		try {
			for (int i = 0; i < params.length; i++) {
				Object param = params[i];
				if (param instanceof Var) {
					param = ((Var) param).value;
				}
				pst.setObject(i + 1, param);
			}
			long s = System.currentTimeMillis();
			logger.info("start preparedStatement.executeQuery()");
			ResultSet rs = pst.executeQuery();
			logger.info("end preparedStatement.executeQuery()");
			long e = System.currentTimeMillis();
			Float duration = (e - s) / 1000f;
			logger.info("duration: " + duration + " " + sqlNo);

			return rs;
		} catch (Exception e) {
			throw new Exception(e.getMessage() + " sqlNo: " + sqlNo, e);
		}
	}

//	public static String toLikePart(String columnValue) {
//		return new StringBuilder("%").append(columnValue).append("%").toString();
//	}

	public static Integer insertSql(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			updateSql(conn, pst, sql, params);
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);
			else
				return null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static Integer insertSql(Connection conn, String sql, LList params) throws Exception {
		return insertSql(conn, sql, params.toArray());
	}

	public static int updateSql(Connection conn, String sql, LList params) throws Exception {
		return updateSql(conn, sql, params.toArray());
	}

	public static int updateSql(Connection conn, String sql, List params) throws Exception {
		return updateSql(conn, sql, LList.build(params));
	}

	public static int updateSql(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return updateSql(conn, pst, sql, params);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pst != null)
				pst.close();

		}
	}

	public static int updateSql(Connection conn, PreparedStatement pst, String sql, Object... params) throws Exception {
		if (params == null)
			params = new Object[] {};
		sql = sql.trim().replaceAll("\\s+", " ");
		String sqlNo = new SimpleDateFormat("YYYYMMDDHHmmssSSS").format(new Date())
				+ RandomStringUtils.randomNumeric(8);
		logger.info("[" + sql + "]" + " " + Arrays.toString(params) + " " + sqlNo);
		int cnt = 0;
		try {
			for (int i = 0; i < params.length; i++) {
				Object param = params[i];
				if (param instanceof Var) {
					param = ((Var) param).value;
					pst.setObject(i + 1, param);
				} else if (param instanceof InputStream) {
					pst.setBinaryStream(i + 1, (InputStream) param);
				} else {
					pst.setObject(i + 1, param);
				}
			}

			long s = System.currentTimeMillis();
			logger.info("start preparedStatement.executeQuery()");
			cnt = pst.executeUpdate();
			logger.info("end preparedStatement.executeQuery()");
			long e = System.currentTimeMillis();
			Float duration = (e - s) / 1000f;
			logger.info("duration: " + duration + " " + sqlNo);

			logger.info("duration: " + duration + " affected: " + cnt + " " + sqlNo);
		} catch (Exception e) {
			throw new Exception(e.getMessage() + " sqlNo: " + sqlNo, e);
		}
		return cnt;
	}

//	public static LList rowsCommonly(Connection conn, String table, LList columns, MMap conditionm, MMap orderm,
//			LList limits) throws Exception {
//		String sql = "";
//		sql += "select ";
//
//		if (columns.isEmpty()) {
//			sql += " * ";
//		} else {
//			for (int i = 0; i < columns.size(); i++) {
//				sql += columns.get(i) + ",";
//			}
//			sql = sql.substring(0, sql.lastIndexOf(","));
//		}
//
//		sql += " from ";
//		sql += table;
//
//		LList params = LList.build();
//		if (conditionm.isExisting()) {
//			sql += " where ";
//			for (Iterator iterator = conditionm.map.keySet().iterator(); iterator.hasNext();) {
//				Object key = (Object) iterator.next();
//				Object value = conditionm.get(key);
//				sql += value == null || value.toString().isEmpty() ? "" : key + "=? and ";
//				params.addIf(value, value != null && !value.toString().isEmpty());
//			}
//			sql = sql.substring(0, sql.lastIndexOf("and"));
//		}
//
//		if (orderm.isExisting()) {
//			sql += " order by ";
//			for (Iterator iterator = orderm.map.keySet().iterator(); iterator.hasNext();) {
//				Object key = (Object) iterator.next();
//				Object value = orderm.get(key);
//				sql += key == null || key.toString().isEmpty() ? ""
//						: key + " " + Stringuu.trimToBlank((String) value) + ",";
//			}
//			sql = sql.substring(0, sql.lastIndexOf("and"));
//		}
//		return rows(conn, sql, params);
//	}

	public static int updateById(Connection conn, String table, Object splitColumns, Object[] values, Object id)
			throws Exception {
		if (id == null || id.toString() == null || id.toString().isEmpty())
			return 0;
		return updateOne(conn, table, splitColumns, values, "id", id);
	}

//	public static int updateById(Connection conn, String table, Object id, Object column, Object value)
//			throws Exception {
//		if (id == null || id.toString() == null || id.toString().isEmpty())
//			return 0;
//		return updateById(conn, table, id, column, value);
//	}

//	public static int updateById(Connection conn, String table, Object[] columns, Object[] values, Object id)
//			throws Exception {
//		if (id == null || id.toString() == null || id.toString().isEmpty())
//			return 0;
//		MMap columnm = new MMap();
//		columns = columns == null ? new Object[] {} : columns;
//		values = values == null ? new Object[] {} : values;
//		for (int i = 0; i < columns.length; i++) {
//			columnm.put((String) columns[i], values[i]);
//		}
//		return updateById(conn, table, columnm, id);
//	}

//	public static int updateById(Connection conn, String table, MMap columnm, Object id) throws Exception {
//		if (id == null || id.toString() == null || id.toString().isEmpty())
//			return 0;
//		MMap conditionm = MMap.build();
//		conditionm.put("id", id);
//		return updateOne(conn, table, columnm, conditionm);
//	}

	public static int updateOne(Connection conn, String table, Object splitColumns, Object[] values,
			Object splitConditions, Object... conditionValues) throws Exception {
		int cnt = update(conn, table, splitColumns, values, splitConditions, conditionValues);
		if (cnt > 1)
			throw new Exception("受影响的数量超过1");
		return cnt;
	}

	public static int update(Connection conn, String table, Object splitColumns, Object[] values,
			Object splitConditions, Object... conditionValues) throws Exception {
		Object[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens((String) splitColumns, ",");
		Object[] conditions = StringUtils.splitByWholeSeparatorPreserveAllTokens((String) splitConditions, ",");

		MMap columnm = new MMap();
		columns = columns == null ? new Object[] {} : columns;
		values = values == null ? new Object[] {} : values;
		for (int i = 0; i < columns.length; i++) {
			columnm.put((String) columns[i], values[i]);
		}

		MMap conditionm = new MMap();
		conditions = conditions == null ? new Object[] {} : conditions;
		conditionValues = conditionValues == null ? new Object[] {} : conditionValues;
		for (int i = 0; i < conditions.length; i++) {
			conditionm.put((String) conditions[i], conditionValues[i]);
		}

		return update(conn, table, columnm, conditionm);
	}

//	public static int updateOne(Connection conn, String table, MMap columnm, MMap conditionm) throws Exception {
//		int cnt = update(conn, table, columnm, conditionm);
//		if (cnt > 1)
//			throw new Exception("受影响的数量超过1");
//		return cnt;
//	}

	public static int update(Connection conn, String table, MMap columnm, MMap conditionm) throws Exception {
		String sql = "";
		sql += "update ";
		sql += " `" + table + "` ";
		sql += " set id=id,";

		LList params = LList.build();

		if (columnm.isExisting()) {
			for (Iterator iterator = columnm.map.keySet().iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				Object value = columnm.get(key);
				sql += value == null || value.toString() == null ? ""
						: value.toString().isEmpty() ? "`" + key + "`" + "=null," : "`" + key + "`" + "=?,";
				params.addIf(value, value != null && value.toString() != null && !value.toString().isEmpty());
			}
		}
		sql = sql.substring(0, sql.lastIndexOf(","));
		if (conditionm.isExisting()) {
			sql += " where 1=1 and ";
			for (Iterator iterator = conditionm.map.keySet().iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				Object value = conditionm.get(key);
				sql += value == null || value.toString() == null || value.toString().isEmpty() ? ""
						: "`" + key + "`" + "=? and ";
				params.addIf(value, value != null && value.toString() != null && !value.toString().isEmpty());
			}
			sql = sql.substring(0, sql.lastIndexOf("and"));
		}

		return updateSql(conn, sql, params);
	}

	public static int deleteById(Connection conn, String table, Object id) throws Exception {
		if (id == null || id.toString() == null || id.toString().isEmpty())
			return 0;
		return deleteOne(conn, table, "id", id);
	}

	public static int deleteOne(Connection conn, String table, Object splitColumns, Object... values) throws Exception {
		int cnt = delete(conn, table, splitColumns, values);
		if (cnt > 1)
			throw new Exception("受影响的数量超过1");
		return cnt;
	}

	public static int delete(Connection conn, String table, Object splitColumns, Object... values) throws Exception {
		Object[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens((String) splitColumns, ",");

		MMap conditionm = new MMap();
		columns = columns == null ? new Object[] {} : columns;
		values = values == null ? new Object[] {} : values;
		for (int i = 0; i < columns.length; i++) {
			conditionm.put((String) columns[i], values[i]);
		}
		return delete(conn, table, conditionm);
	}

//	public static int deleteOne(Connection conn, String table, Object columnm, Object value) throws Exception {
//		int cnt = delete(conn, table, columnm, value);
//		if (cnt > 1)
//			throw new Exception("受影响的数量超过1");
//		return cnt;
//	}
//
//	public static int delete(Connection conn, String table, Object columnm, Object value) throws Exception {
//		if (value == null || value.toString() == null || value.toString().isEmpty())
//			return 0;
//		MMap conditionm = new MMap();
//		conditionm.put((String) columnm, value);
//		return delete(conn, table, conditionm);
//	}
//
//	public static int deleteOne(Connection conn, String table, Object[] columnms, Object[] values) throws Exception {
//		int cnt = delete(conn, table, columnms, values);
//		if (cnt > 1)
//			throw new Exception("受影响的数量超过1");
//		return cnt;
//	}
//
//	public static int delete(Connection conn, String table, Object[] columnms, Object[] values) throws Exception {
//		MMap conditionm = new MMap();
//		columnms = columnms == null ? new Object[] {} : columnms;
//		values = values == null ? new Object[] {} : values;
//		for (int i = 0; i < columnms.length; i++) {
//			conditionm.put((String) columnms[i], values[i]);
//		}
//		return delete(conn, table, conditionm);
//	}

//	public static int deleteOne(Connection conn, String table, MMap conditionm) throws Exception {
//		int cnt = delete(conn, table, conditionm);
//		if (cnt > 1)
//			throw new Exception("受影响的数量超过1");
//		return cnt;
//	}

	public static int delete(Connection conn, String table, MMap conditionm) throws Exception {
		String sql = "";
		sql += "delete from ";
		sql += " `" + table + "` ";

		LList params = LList.build();

		if (conditionm.isExisting()) {
			sql += " where 1=1 and ";
			for (Iterator iterator = conditionm.map.keySet().iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				Object value = conditionm.get(key);
				sql += value == null || value.toString() == null || value.toString().isEmpty() ? ""
						: "`" + key + "`" + "=? and ";
				params.addIf(value, value != null && value.toString() != null && !value.toString().isEmpty());
			}
			sql = sql.substring(0, sql.lastIndexOf("and"));
		}

		return updateSql(conn, sql, params);
	}

//	public static int insertByCustomKeyCommonly(Connection conn, String table, MMap columnm) throws Exception {
//		String sql = "";
//		sql += "insert into ";
//		sql += "`" + table + "`";
//		sql += " ( ";
//
//		LList params = LList.build();
//		if (columnm.isExisting()) {
//			for (Iterator iterator = columnm.map.keySet().iterator(); iterator.hasNext();) {
//				Object key = (Object) iterator.next();
//				Object value = columnm.get(key);
//				sql += value == null || value.toString().isEmpty() ? "" : "`" + key + "`" + ",";
//			}
//		}
//		sql = sql.substring(0, sql.lastIndexOf(","));
//		sql += " ) values ( ";
//		if (columnm.isExisting()) {
//			for (Iterator iterator = columnm.map.keySet().iterator(); iterator.hasNext();) {
//				Object key = (Object) iterator.next();
//				Object value = columnm.get(key);
//				sql += value == null || value.toString().isEmpty() ? "" : "?,";
//				params.addIf(value, value != null && !value.toString().isEmpty());
//			}
//		}
//		sql = sql.substring(0, sql.lastIndexOf(","));
//		sql += " ) ";
//
//		return update(conn, sql, params);
//	}

//	public static int insert(Connection conn, String table, Object column, Object value) throws Exception {
//		MMap columnm = new MMap();
//		columnm.put((String) column, value);
//		return insert(conn, table, columnm);
//	}

	public static int insert(Connection conn, String table, Object splitColumns, Object... values) throws Exception {
		Object[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens((String) splitColumns, ",");
		MMap columnm = new MMap();
		columns = columns == null ? new Object[] {} : columns;
		values = values == null ? new Object[] {} : values;
		for (int i = 0; i < columns.length; i++) {
			columnm.put((String) columns[i], values[i]);
		}

		return insert(conn, table, columnm);
	}

//	public static int insert(Connection conn, String table, Object[] columns, Object[] values) throws Exception {
//		MMap columnm = new MMap();
//		columns = columns == null ? new Object[] {} : columns;
//		values = values == null ? new Object[] {} : values;
//		for (int i = 0; i < columns.length; i++) {
//			columnm.put((String) columns[i], values[i]);
//		}
//		return insert(conn, table, columnm);
//	}

	public static int insert(Connection conn, String table, MMap columnm) throws Exception {
		String sql = "";
		sql += "insert into ";
		sql += "`" + table + "`";
		sql += " ( ";

		LList params = LList.build();
		if (columnm.isExisting()) {
			for (Iterator iterator = columnm.map.keySet().iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				Object value = columnm.get(key);
				sql += value == null || value.toString() == null || value.toString().isEmpty() ? ""
						: "`" + key + "`" + ",";
			}
		}
		sql = sql.substring(0, sql.lastIndexOf(","));
		sql += " ) values ( ";
		if (columnm.isExisting()) {
			for (Iterator iterator = columnm.map.keySet().iterator(); iterator.hasNext();) {
				Object key = (Object) iterator.next();
				Object value = columnm.get(key);
				sql += value == null || value.toString() == null || value.toString().isEmpty() ? "" : "?,";
				params.addIf(value, value != null && value.toString() != null && !value.toString().isEmpty());
			}
		}
		sql = sql.substring(0, sql.lastIndexOf(","));
		sql += " ) ";

		return insertSql(conn, sql, params);
	}

	public static Integer generatedKey(PreparedStatement pst) throws SQLException {
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(1);
			rs.close();
			return id;
		} else
			return null;
	}

	public static List<Integer> generatedKeys(PreparedStatement pst) throws SQLException {
		ResultSet rs = pst.getGeneratedKeys();
		List<Integer> keys = new ArrayList();
		while (rs.next()) {
			int key = rs.getInt(1);
			keys.add(key);
		}
		rs.close();
		return keys;
	}

	public static int[] batch(Connection conn, String sql, Object... paramBatches) throws Exception {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return batch(pst, sql, paramBatches);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pst != null)
				pst.close();
		}
	}

	public static int[] batch(PreparedStatement pst, String sql, Object... paramBatches) throws Exception {
		if (paramBatches == null)
			paramBatches = new Object[] {};
		String sqlNo = new SimpleDateFormat("YYYYMMDDHHmmssSSS").format(new Date())
				+ RandomStringUtils.randomNumeric(8);
		logger.info(sqlNo + " " + sql);
		int[] cnts = new int[] {};
		for (Object param : paramBatches) {
			logger.info(sqlNo + " " + JSON.toJSONString(param));
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
		long s = System.currentTimeMillis();
		cnts = pst.executeBatch();
		long e = System.currentTimeMillis();
		logger.info(sqlNo + " " + "takes: " + Stringuu.commaNum((e - s) + "") + "ms");
		logger.info(sqlNo + " " + "affected : " + Arrays.toString(cnts));
		return cnts;
	}

	public static LList<Map> rows(ResultSet rs) throws SQLException {
		LList<Map> rows = LList.build();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCnt = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> row = new LinkedHashMap();
			for (int i = 1; i <= columnCnt; i++) {
				String name = metaData.getColumnLabel(i);
				Object value = rs.getObject(i);
				row.put(Stringuu.camel(name), value);
			}
			rows.add(row);
			if (rows.size() <= 1) {
				logger.info(JSON.toJSONString(row));
			}
		}
		logger.info("affected: " + rows.size());
		return rows;
	}

//	public static List<Map> rows(ResultSet rs, String[] excludeColumns) throws SQLException {
//		List<String> excludeColumnList = Arrays.asList(excludeColumns);
//		List<Map> rows = new ArrayList();
//		ResultSetMetaData metaData = rs.getMetaData();
//		int columnCnt = metaData.getColumnCount();
//		while (rs.next()) {
//			Map<String, Object> row = new LinkedHashMap();
//			for (int i = 1; i <= columnCnt; i++) {
//				String column = metaData.getColumnLabel(i);
//				if (excludeColumnList.contains(column))
//					continue;
//				Object value = rs.getObject(i);
//				row.put(metaData.getColumnLabel(i), value);
//			}
//			rows.add(row);
//			if (rows.size() <= 1)
//				logger.info(JSON.toJSONString(row));
//		}
//		return rows;
//	}

//	public static List<List<Object>> listRows(ResultSet rs) throws SQLException {
//		ResultSetMetaData metaData = rs.getMetaData();
//		int columnCnt = metaData.getColumnCount();
//
//		List<List<Object>> valueLists = new ArrayList<List<Object>>();
//		while (rs.next()) {
//			List<Object> valueList = new ArrayList<Object>();
//			for (int i = 1; i <= columnCnt; i++) {
//				Object value = rs.getObject(i);
//				valueList.add(value);
//			}
//			valueLists.add(valueList);
//			if (valueLists.size() <= 10)
//				logger.info(JSON.toJSONString(valueList));
//		}
//		logger.info("affected : " + valueLists.size());
//		return valueLists;
//	}

//	public static List<Object> thinRows(ResultSet rs) throws SQLException {
//		List<Object> rows = new ArrayList();
//		ResultSetMetaData metaData = rs.getMetaData();
//		while (rs.next()) {
//			Object value = rs.getObject(1);
//			rows.add(value);
//			if (rows.size() <= 10)
//				logger.info(new StringBuilder("[").append(value).append("]").toString());
//		}
//		logger.info("affected : " + rows.size());
//		return rows;
//	}

	public static MMap row(ResultSet rs) throws SQLException {
		LList<Map> list = rows(rs);
		if (list.size() > 0)
			return list.getMap(0);
		else
			return null;
	}

	public static Object getColumn(ResultSet rs) throws SQLException {
		LList<Map> list = rows(rs);
		if (list.size() > 0)
			return list.getMap(0).get(list.getMap(0).keySet().iterator().next());
		else
			return null;
	}

	public static Integer getInteger(ResultSet rs) throws SQLException {
		Object value = getColumn(rs);
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
//	public static String buildSql_paramsplit(String... params) {
//		if (params == null || params.length == 0)
//			return "";
//		String sqlPart = "  ";
//		for (int i = 0; i < params.length; i++) {
//			if (i == 0)
//				sqlPart += "'" + params[i] + "'";
//			else
//				sqlPart += " , '" + params[i] + "'";
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

//	public static List addparams(List params, String insSplit) {
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

	public static void eeeeeeeeeeeeeeeeeeeeeeee() {
	}

//	@Deprecated
//	public static String buildOrderBy(String[] sorts, String[] orders, String[] sortPool, String[] sortColumnPool,
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
//				throw new RuntimeException("排序字段有误");
//			sqlB.append(" ");
//			if (orders[i].equals("desc"))
//				sqlB.append("desc");
//			else if (orders[i].equals("asc"))
//				sqlB.append("asc");
//			else
//				throw new RuntimeException("排序顺序有误");
//			sqlB.append(sorts.length == 0 ? "" : ",");
//		}
//		sqlB.append(baseSort).append(" ").append(baseOrder);
//		return sqlB.toString();
//	}

}
