package renx.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CConnection {
	private static Logger logger = LoggerFactory.getLogger(CConnection.class);

	public DataSource dataSource;
	public Connection conn;
	public boolean self = true;

	public static CConnection build() {
		return build(null, null);
	}

	public static CConnection build(Connection conn) {
		CConnection cconn = build();
		cconn.conn = conn;
		return cconn;
	}

	public static CConnection build(DataSource ds) {
		return build(ds, null);
	}

	public static CConnection build(DataSource dataSource, CConnection cconn) {
		CConnection cconn2 = new CConnection();
		cconn2.dataSource = dataSource;
		if (cconn != null && cconn.conn != null) {
			cconn2.conn = cconn.conn;
			cconn2.self = false;
		}
		return cconn2;
	}

	public LList rows(String sql, LList params) throws Exception {
		if (params == null || params.isEmpty())
			return rows(sql);
		else
			return rows(sql, params.toArray());
	}

	public LList rows(String sql, List params) throws Exception {
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

	public LList rows(String sql, Object... params) throws Exception {
		setConnection();
		LList rows = Jdbcuu.rows(conn, sql, params);
		return rows;
	}

	public MMap row(String sql, LList params) throws Exception {
		if (params == null || params.isEmpty())
			return row(sql);
		else
			return row(sql, params.toArray());
	}

	public MMap row(String sql, List params) throws Exception {
		if (params == null)
			return row(sql);
		else
			return row(sql, params.toArray());
	}

	public MMap row(String sql, Object... params) throws Exception {
		setConnection();
		MMap row = Jdbcuu.row(conn, sql, params);
		return row;
	}

	public int updateSql(String sql, LList params) throws Exception {
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
		return Jdbcuu.updateSql(conn, sql, params);
	}

	public Integer insertSql(String sql, LList params) throws Exception {
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
		return Jdbcuu.insertSql(conn, sql, params);
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
		return Jdbcuu.getStream(conn, sql, params);
	}

	public Integer getInteger(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getInteger(conn, sql, params);
	}

	public Var get(String sql, Object... params) throws Exception {
		return Jdbcuu.get(conn, sql, params);
	}

	public String getString(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getString(conn, sql, params);
	}

	public LList getList(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getList(conn, sql, params);
	}

	public MMap getMap(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getMap(conn, sql, params);
	}

	public BigDecimal getDecimal(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getDecimal(conn, sql, params);
	}

	public Long getLong(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getLong(conn, sql, params);
	}

	public Float getFloat(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getFloat(conn, sql, params);
	}

	public Date getDate(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getDate(conn, sql, params);
	}

	public Object getObject(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu.getObject(conn, sql, params);
	}

	public int updateById(String table, Object column, Object value, Object id) throws Exception {
		setConnection();
		return Jdbcuu.updateById(conn, table, column, value, id);
	}

	public int updateById(String table, Object[] columns, Object[] values, Object id) throws Exception {
		setConnection();
		return Jdbcuu.updateById(conn, table, columns, values, id);
	}

	public int updateById(String table, MMap columnm, Object id) throws Exception {
		setConnection();
		return Jdbcuu.updateById(conn, table, columnm, id);
	}

	public int updateById(String table, Object splitColumns, Object[] values, Object id) throws Exception {
		setConnection();
		return Jdbcuu.updateById(conn, table, splitColumns, values, id);
	}

	public int update(String table, Object splitColumns, Object[] values, Object splitConditions,
			Object[] conditionValues) throws Exception {
		setConnection();
		return Jdbcuu.update(conn, table, splitColumns, values, splitConditions, conditionValues);
	}

	public int update(String table, MMap columnm, MMap conditionm) throws Exception {
		setConnection();
		return Jdbcuu.update(conn, table, columnm, conditionm);
	}

	public int deleteById(String table, Object id) throws Exception {
		setConnection();
		return Jdbcuu.deleteById(conn, table, id);
	}

	public int delete(String table, Object columnm, Object value) throws Exception {
		setConnection();
		return Jdbcuu.delete(conn, table, columnm, value);
	}

	public int delete(String table, Object[] columnms, Object[] values) throws Exception {
		setConnection();
		return Jdbcuu.delete(conn, table, columnms, values);
	}

	public int delete(String table, MMap conditionm) throws Exception {
		setConnection();
		return Jdbcuu.delete(conn, table, conditionm);

	}

	public int delete(String table, Object splitColumns, Object... values) throws Exception {
		setConnection();
		return Jdbcuu.delete(conn, table, splitColumns, values);
	}
//	public int insertByCustomKeyCommonly(String table, MMap columnm) throws Exception {
//		return Jdbcuu.insertByCustomKeyCommonly(conn, table, columnm);
//
//	}

	public int insert(String table, MMap columnm) throws Exception {
		setConnection();
		return Jdbcuu.insert(conn, table, columnm);
	}

//	public int insert(String table, Object column, Object value) throws Exception {
//		return Jdbcuu.insert(conn, table, column, value);
//	}

	public int insert(String table, Object splitColumns, Object... values) throws Exception {
		setConnection();
		return Jdbcuu.insert(conn, table, splitColumns, values);
	}

	public int insert(String table, Object[] columnms, Object[] values) throws Exception {
		setConnection();
		return Jdbcuu.insert(conn, table, columnms, values);
	}

	public MMap selectById(String table, Object splitColumns, Object id) throws Exception {
		setConnection();
		return Jdbcuu.selectOne(conn, table, splitColumns, "id", id);
	}

	public LList selectList(String table, String order, String limit, Object splitColumns, Object splitConditionColumns,
			Object... conditionValues) throws Exception {
		setConnection();
		return Jdbcuu.selectList(conn, table, order, limit, splitConditionColumns, conditionValues);
	}

	public MMap selectOne(String table, Object splitColumns, Object splitConditionColumns, Object... conditionValues)
			throws Exception {
		setConnection();
		return Jdbcuu.selectOne(conn, table, splitColumns, splitConditionColumns, conditionValues);
	}
}
