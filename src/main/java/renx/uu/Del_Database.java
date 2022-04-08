package renx.uu;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Del_Database {
	private Logger logger = LoggerFactory.getLogger(Del_Database.class);

	public DataSource dataSource;
	public String driver;
	public String url;
	public String username;
	public String password;

	public void start() throws SQLException {
		start(null);
	}

	public void start(DataSource dataSource) throws SQLException {
		if (dataSource == null) {
			if (this.dataSource == null) {
				Connection connection = null;
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
					connection = tomcatJdbcPoolDataSource.getConnection();
					connection.createStatement().execute("select 1");
					this.dataSource = tomcatJdbcPoolDataSource;
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					if (connection != null)
						connection.close();
				}
			}
		} else {
			this.dataSource = dataSource;
		}
	}

	public Connection getConnection() throws SQLException {
		start();
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		return conn;
	}

	public Connection getConnection(Connection conn) throws SQLException {
		if (conn != null)
			return conn;
		return getConnection();
	}

	public void commitConnection(Connection conn) throws SQLException {
		if (conn != null && !conn.getAutoCommit())
			conn.commit();
	}

	public void rollbackConnection(Connection conn) throws SQLException {
		if (conn != null && !conn.getAutoCommit())
			conn.rollback();
	}

	public void closeConnection(Connection conn) throws SQLException {
		if (conn != null) {
			rollbackConnection(conn);
			conn.close();
		}
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
	}
}
