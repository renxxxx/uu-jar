package rxw.others;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Database {
	private static Logger logger = Logger.getLogger(Database.class);

	public DataSource dataSource;
	public String driver;
	public String url;
	public String username;
	public String password;

	public void setDataSource() {
		if (this.dataSource == null) {
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
	}

	public Connection connect(Connection conn) throws SQLException {
		setDataSource();
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
		System.out.println(StringUtils.newId());
	}
}
