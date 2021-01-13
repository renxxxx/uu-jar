package archive;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Db {
	private static Logger logger = Logger.getLogger(Db.class);

	public DataSource dataSource;
	public String driver;
	public String url;
	public String username;
	public String password;
	public boolean inited = false;

	public void init() {
		if (!this.inited) {
			org.apache.tomcat.jdbc.pool.DataSource tomcatJdbcPoolDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
			tomcatJdbcPoolDataSource.setDriverClassName(driver);
			tomcatJdbcPoolDataSource.setUrl(url);
			tomcatJdbcPoolDataSource.setUsername(username);
			tomcatJdbcPoolDataSource.setPassword(password);
			tomcatJdbcPoolDataSource.setTestOnBorrow(true);
			tomcatJdbcPoolDataSource.setDefaultAutoCommit(false);
			tomcatJdbcPoolDataSource.setRollbackOnReturn(true);
			tomcatJdbcPoolDataSource.setValidationQuery("SELECT 1");
			dataSource = tomcatJdbcPoolDataSource;
		}
		this.inited = true;
	}

	public Connection connect(Connection conn) throws SQLException {
		init();
		if (conn == null) {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		}
		return conn;
	}

	public Connection connect() throws SQLException {
		return connect(null);
	}

	public void commit(Connection conn) throws SQLException {
		if (conn != null && !conn.getAutoCommit())
			conn.commit();
	}

	public void rollback(Connection conn) throws SQLException {
		if (conn != null && !conn.getAutoCommit())
			conn.rollback();
	}

	public void close(Connection conn) throws SQLException {
		if (conn != null) {
			rollback(conn);
			conn.close();
		}
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
		System.out.println(StringUtils.newId());
	}
}
