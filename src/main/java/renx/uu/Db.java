package renx.uu;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Db {
	private Logger logger = LoggerFactory.getLogger(Db.class);

	public DataSource dataSource;
	public String driver;
	public String url;
	public String username;
	public String password;

	public void start() {
		start(null);
	}

	public void start(DataSource dataSource) {
		if (dataSource == null) {
			if (this.dataSource == null) {
				try {
					org.apache.tomcat.jdbc.pool.DataSource tomcatJdbcPoolDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
					tomcatJdbcPoolDataSource.setDriverClassName(driver);
					tomcatJdbcPoolDataSource.setUrl(url);
					tomcatJdbcPoolDataSource.setUsername(username);
					tomcatJdbcPoolDataSource.setPassword(password);
					tomcatJdbcPoolDataSource.setTestOnBorrow(true);
					tomcatJdbcPoolDataSource.setDefaultAutoCommit(false);
					tomcatJdbcPoolDataSource.setRollbackOnReturn(true);
					tomcatJdbcPoolDataSource.setValidationQuery("SELECT 1");
					this.dataSource = tomcatJdbcPoolDataSource;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			this.dataSource = dataSource;
		}
	}

	public Connection connect(Connection conn) throws SQLException {
		start();
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
	}
}
