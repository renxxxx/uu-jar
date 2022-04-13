package renx.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class CConnection {
	public DataSource dataSource;
	public Connection conn;
	public boolean self = true;

	public static CConnection build() {
		return build(null, null);
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

	public List<Map> rows(String sql, LList params) throws Exception {
		if (params == null || params.isEmpty())
			return rows(sql);
		else
			return rows(sql, params.toArray());
	}

	public List<Map> rows(String sql, List params) throws Exception {
		if (params == null)
			return rows(sql);
		else
			return rows(sql, params.toArray());
	}

	public List<Map> rows(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}

		List<Map> rows = Jdbcuu.rows(conn, sql, params);
		return rows;
	}

	public List<Object> thinRows(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}

		List<Object> rows = Jdbcuu.thinRows(conn, sql, params);
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
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}

		MMap row = Jdbcuu.row(conn, sql, params);
		return row;
	}

	public int update(String sql, LList params) throws Exception {
		if (params == null || params.isEmpty())
			return update(sql);
		else
			return update(sql, params.toArray());
	}

	public int update(String sql, List params) throws Exception {
		if (params == null)
			return update(sql);
		else
			return update(sql, params.toArray());
	}

	public int update(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.update(conn, sql, params);
	}

	public Integer insert(String sql, LList params) throws Exception {
		if (params == null || params.isEmpty())
			return insert(sql);
		else
			return insert(sql, params.toArray());
	}

	public Integer insert(String sql, List params) throws Exception {
		if (params == null)
			return insert(sql);
		else
			return insert(sql, params.toArray());
	}

	public Integer insert(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.insert(conn, sql, params);
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
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getStream(conn, sql, params);
	}

	public Integer getInteger(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getInteger(conn, sql, params);
	}

	public String getString(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getString(conn, sql, params);
	}

	public LList getJsonArray(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getJsonArray(conn, sql, params);
	}

	public MMap getJson(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getJson(conn, sql, params);
	}

	public BigDecimal getDecimal(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getDecimal(conn, sql, params);
	}

	public Long getLong(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getLong(conn, sql, params);
	}

	public Float getFloat(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getFloat(conn, sql, params);
	}

	public Date getDate(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getDate(conn, sql, params);
	}

	public Object getColumn(String sql, Object... params) throws Exception {
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return Jdbcuu.getColumn(conn, sql, params);
	}
}
