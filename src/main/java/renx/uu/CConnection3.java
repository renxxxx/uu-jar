package renx.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CConnection3 {
	private static Logger logger = LoggerFactory.getLogger(CConnection3.class);

	public DataSource dataSource;
	public Connection conn;
	public boolean self = true;

	public static CConnection3 build() {
		return build(null, null);
	}

	public static CConnection3 build(Connection conn) {
		CConnection3 cconn = build();
		cconn.conn = conn;
		return cconn;
	}

	public static CConnection3 build(DataSource ds) {
		return build(ds, null);
	}

	public static CConnection3 build(DataSource dataSource, CConnection3 cconn) {
		CConnection3 cconn2 = new CConnection3();
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
		LList rows = Jdbcuu3.rows(conn, sql, params);
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
		MMap row = Jdbcuu3.row(conn, sql, params);
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
		return Jdbcuu3.updateSql(conn, sql, params);
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
		return Jdbcuu3.insertSql(conn, sql, params);
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
		return Jdbcuu3.getStream(conn, sql, params);
	}

	public Integer getInteger(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getInteger(conn, sql, params);
	}

	public Var get(String sql, Object... params) throws Exception {
		return Jdbcuu3.get(conn, sql, params);
	}

	public String getString(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getString(conn, sql, params);
	}

	public LList getList(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getList(conn, sql, params);
	}

	public MMap getMap(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getMap(conn, sql, params);
	}

	public BigDecimal getDecimal(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getDecimal(conn, sql, params);
	}

	public Long getLong(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getLong(conn, sql, params);
	}

	public Float getFloat(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getFloat(conn, sql, params);
	}

	public Date getDate(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getDate(conn, sql, params);
	}

	public Object getObject(String sql, Object... params) throws Exception {
		setConnection();
		return Jdbcuu3.getObject(conn, sql, params);
	}

//	public int updateById(String table, Object column, Object value, Object id) throws Exception {
//		setConnection();
//		return Jdbcuu3.updateById(conn, table, column, value, id);
//	}

//	public int updateById(String table, Object[] columns, Object[] values, Object id) throws Exception {
//		setConnection();
//		return Jdbcuu3.updateById(conn, table, columns, values, id);
//	}
//
//	public int updateById(String table, MMap columnm, Object id) throws Exception {
//		setConnection();
//		return Jdbcuu3.updateById(conn, table, columnm, id);
//	}

	public int updateById(String table, Object splitColumns, Object[] values, Object id) throws Exception {
		setConnection();
		return Jdbcuu3.updateById(conn, table, splitColumns, values, id);
	}

	public int updateOne(String table, Object splitColumns, Object[] values, Object splitConditions,
			Object... conditionValues) throws Exception {
		setConnection();
		return Jdbcuu3.updateOne(conn, table, splitColumns, values, splitConditions, conditionValues);
	}

	public int update(String table, Object splitColumns, Object[] values, Object splitConditions,
			Object... conditionValues) throws Exception {
		setConnection();
		return Jdbcuu3.update(conn, table, splitColumns, values, splitConditions, conditionValues);
	}

//	public int updateOne(String table, MMap columnm, MMap conditionm) throws Exception {
//		setConnection();
//		return Jdbcuu3.updateOne(conn, table, columnm, conditionm);
//	}
//
//	public int update(String table, MMap columnm, MMap conditionm) throws Exception {
//		setConnection();
//		return Jdbcuu3.update(conn, table, columnm, conditionm);
//	}

	public int deleteById(String table, Object id) throws Exception {
		setConnection();
		return Jdbcuu3.deleteById(conn, table, id);
	}

//	public int deleteOne(String table, Object columnm, Object value) throws Exception {
//		setConnection();
//		return Jdbcuu3.deleteOne(conn, table, columnm, value);
//	}
//
//	public int delete(String table, Object columnm, Object value) throws Exception {
//		setConnection();
//		return Jdbcuu3.delete(conn, table, columnm, value);
//	}
//
//	public int deleteOne(String table, Object[] columnms, Object[] values) throws Exception {
//		setConnection();
//		return Jdbcuu3.deleteOne(conn, table, columnms, values);
//	}
//
//	public int delete(String table, Object[] columnms, Object[] values) throws Exception {
//		setConnection();
//		return Jdbcuu3.delete(conn, table, columnms, values);
//	}
//
//	public int deleteOne(String table, MMap conditionm) throws Exception {
//		setConnection();
//		return Jdbcuu3.deleteOne(conn, table, conditionm);
//	}
//
//	public int delete(String table, MMap conditionm) throws Exception {
//		setConnection();
//		return Jdbcuu3.delete(conn, table, conditionm);
//	}

	public int deleteOne(String table, Object splitColumns, Object... values) throws Exception {
		setConnection();
		return Jdbcuu3.deleteOne(conn, table, splitColumns, values);
	}

	public int delete(String table, Object splitColumns, Object... values) throws Exception {
		setConnection();
		return Jdbcuu3.delete(conn, table, splitColumns, values);
	}

//	public int insertByCustomKeyCommonly(String table, MMap columnm) throws Exception {
//		return Jdbcuu3.insertByCustomKeyCommonly(conn, table, columnm);
//
//	}

//	public int insert(String table, MMap columnm) throws Exception {
//		setConnection();
//		return Jdbcuu3.insert(conn, table, columnm);
//	}

//	public int insert(String table, Object column, Object value) throws Exception {
//		return Jdbcuu3.insert(conn, table, column, value);
//	}

	public int insert(String table, Object splitColumns, Object... values) throws Exception {
		setConnection();
		return Jdbcuu3.insert(conn, table, splitColumns, values);
	}

//	public int insert(String table, Object[] columnms, Object[] values) throws Exception {
//		setConnection();
//		return Jdbcuu3.insert(conn, table, columnms, values);
//	}

	public MMap selectById(String table, Object splitColumns, Object id) throws Exception {
		setConnection();
		return Jdbcuu3.selectById(conn, table, splitColumns, id);
	}

	public LList selectList(String table, Object order, Object limit, Object splitColumns, Object splitConditionColumns,
			Object... conditionValues) throws Exception {
		setConnection();
		return Jdbcuu3.selectList(conn, table, order, limit, splitColumns, splitConditionColumns, conditionValues);
	}

	public MMap selectOne(String table, Object splitColumns, Object splitConditionColumns, Object... conditionValues)
			throws Exception {
		setConnection();
		return Jdbcuu3.selectOne(conn, table, splitColumns, splitConditionColumns, conditionValues);
	}
}
