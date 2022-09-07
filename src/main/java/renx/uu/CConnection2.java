package renx.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CConnection2 {
	private static Logger logger = LoggerFactory.getLogger(CConnection2.class);

	public DataSource dataSource;
	public Connection conn;
	public boolean self = true;

	public static CConnection2 build() {
		return build(null, null);
	}

	public static CConnection2 build(Connection conn) {
		CConnection2 cconn = build();
		cconn.conn = conn;
		return cconn;
	}

	public static CConnection2 build(DataSource ds) {
		return build(ds, null);
	}

	public static CConnection2 build(DataSource dataSource, CConnection2 cconn) {
		CConnection2 cconn2 = new CConnection2();
		cconn2.dataSource = dataSource;
		if (cconn != null && cconn.conn != null) {
			cconn2.conn = cconn.conn;
			cconn2.self = false;
		}
		return cconn2;
	}

	public JSONArray rows(String sql, JSONArray params) throws Exception {
		if (params == null || params.isEmpty())
			return rows(sql);
		else
			return rows(sql, params.toArray());
	}

	public JSONArray rows(String sql, List params) throws Exception {
		if (params == null)
			return rows(sql);
		else
			return rows(sql, params.toArray());
	}

	private void setConnection() throws SQLException {
		int mark = 0;
		logger.info("mark" + ++mark);
		if (conn == null) {
			logger.info("mark" + ++mark);
			conn = dataSource.getConnection();

			logger.info("mark" + ++mark);
			conn.setAutoCommit(false);
		}
		logger.info("mark" + ++mark);
	}

	public JSONArray rows(String sql, Object... params) throws Exception {
		setConnection();
		JSONArray rows = Jdbcuu2.rows(conn, sql, params);
		return rows;
	}

	public JSONObject row(String sql, JSONArray params) throws Exception {
		if (params == null || params.isEmpty())
			return row(sql);
		else
			return row(sql, params.toArray());
	}

	public JSONObject row(String sql, List params) throws Exception {
		if (params == null)
			return row(sql);
		else
			return row(sql, params.toArray());
	}

	public JSONObject row(String sql, Object... params) throws Exception {
		setConnection();
		JSONObject row = Jdbcuu2.row(conn, sql, params);
		return row;
	}

	public int updateSql(String sql, JSONArray params) throws Exception {
		if (params == null || params.isEmpty())
			return updateSql(sql);
		else
			return updateSql(sql, params.toArray());
	}

	public int updateSql(String sql, List params) throws Exception {
		if (params == null)
			return updateSql(sql);
		else
			return updateSql(sql, params.toArray());
	}

	public int updateSql(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.updateSql(conn, sql, params);
	}

	public Integer insertSql(String sql, JSONArray params) throws Exception {
		if (params == null || params.isEmpty())
			return insertSql(sql);
		else
			return insertSql(sql, params.toArray());
	}

	public Integer insertSql(String sql, List params) throws Exception {
		if (params == null)
			return insertSql(sql);
		else
			return insertSql(sql, params.toArray());
	}

	public Integer insertSql(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.insertSql(conn, sql, params);
	}

	public void rollback() throws Exception {
		if (self && conn != null) {
			conn.rollback();
		}
	}

	public void commit() throws Exception {
		if (self && conn != null) {
			conn.commit();
		}
	}

	public void close() throws Exception {
		if (self && conn != null) {
			conn.close();
		}
	}

	public InputStream getStream(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getStream(conn, sql, params);
	}

	public Integer getInteger(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getInteger(conn, sql, params);
	}

	public Var getVar(String sql, Object... params) throws Exception {
		return Var.build(getString(sql, params));
	}

	public String getString(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getString(conn, sql, params);
	}

	public JSONArray getList(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getList(conn, sql, params);
	}

	public JSONObject getMap(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getMap(conn, sql, params);
	}

	public BigDecimal getDecimal(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getDecimal(conn, sql, params);
	}

	public Long getLong(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getLong(conn, sql, params);
	}

	public Float getFloat(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getFloat(conn, sql, params);
	}

	public Date getDate(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getDate(conn, sql, params);
	}

	public Object getObject(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu2.getObject(conn, sql, params);
	}

	public int updateById(String table, Object column, Object value, String id) throws Exception {
		JSONObject columnm = new JSONObject();
		columnm.put((String) column, value);
		return Jdbcuu2.updateById(conn, table, columnm, id);
	}

	public int updateById(String table, Object[] columns, Object[] values, String id) throws Exception {
		JSONObject columnm = new JSONObject();
		columns = columns == null ? new Object[] {} : columns;
		values = values == null ? new Object[] {} : values;
		for (int i = 0; i < columns.length; i++) {
			columnm.put((String) columns[i], values[i]);
		}
		return Jdbcuu2.updateById(conn, table, columnm, id);
	}

	public int updateById(String table, JSONObject columnm, String id) throws Exception {
		JSONObject conditionm = new JSONObject();
		conditionm.put("id", id);
		return Jdbcuu2.update(conn, table, columnm, conditionm);
	}

	public int update(String table, JSONObject columnm, JSONObject conditionm) throws Exception {
		return Jdbcuu2.update(conn, table, columnm, conditionm);
	}

	public int deleteById(Connection conn, String table, Object id) throws Exception {
		JSONObject conditionm = new JSONObject();
		conditionm.put("id", id);
		return Jdbcuu2.delete(conn, table, conditionm);
	}

	public int delete(String table, Object columnm, Object value) throws Exception {
		JSONObject conditionm = new JSONObject();
		conditionm.put((String) columnm, value);
		return Jdbcuu2.delete(conn, table, conditionm);
	}

	public int delete(String table, Object[] columnms, Object[] values) throws Exception {
		JSONObject conditionm = new JSONObject();
		columnms = columnms == null ? new Object[] {} : columnms;
		values = values == null ? new Object[] {} : values;
		for (int i = 0; i < columnms.length; i++) {
			conditionm.put((String) columnms[i], values[i]);
		}
		return Jdbcuu2.delete(conn, table, conditionm);
	}

	public int delete(String table, JSONObject conditionm) throws Exception {
		return Jdbcuu2.delete(conn, table, conditionm);

	}

//	public int insertByCustomKeyCommonly(String table, JSONObject columnm) throws Exception {
//		return Jdbcuu2.insertByCustomKeyCommonly(conn, table, columnm);
//
//	}

	public int insert(String table, JSONObject columnm) throws Exception {
		return Jdbcuu2.insert(conn, table, columnm);
	}

	public int insert(String table, Object column, Object value) throws Exception {
		JSONObject columnm = new JSONObject();
		columnm.put((String) column, value);
		return Jdbcuu2.insert(conn, table, columnm);
	}

	public int insert(String table, Object[] columnms, Object[] values) throws Exception {
		JSONObject columnm = new JSONObject();
		columnms = columnms == null ? new Object[] {} : columnms;
		values = values == null ? new Object[] {} : values;
		for (int i = 0; i < columnms.length; i++) {
			columnm.put((String) columnms[i], values[i]);
		}
		return Jdbcuu2.insert(conn, table, columnm);
	}
}
