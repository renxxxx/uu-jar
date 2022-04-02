package renx.uu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class CConnection2 {
	public DataSource ds;
	public Connection conn;
	public boolean self = true;

	public static CConnection2 build() throws SQLException {
		return build(null, null);
	}

	public static CConnection2 build(DataSource ds) throws SQLException {
		return build(ds, null);
	}

	public static CConnection2 build(DataSource ds, CConnection2 cconn) throws SQLException {
		CConnection2 cconn2 = new CConnection2();
		cconn2.ds = ds;
		if (cconn != null && cconn.conn != null && !cconn.conn.isClosed()) {
			cconn2.conn = cconn.conn;
			cconn2.self = false;
		}
		return cconn2;
	}

	public List<Map> rows(String sql, List params) throws Exception {
		if (conn == null || conn.isClosed()) {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		}

		List<Map> rows = Jdbcuu.rows(conn, sql, params);
		return rows;
	}

	public MMap row(String sql, List params) throws Exception {
		if (conn == null || conn.isClosed()) {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		}

		MMap row = Jdbcuu.row(conn, sql, params);
		return row;
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
}
