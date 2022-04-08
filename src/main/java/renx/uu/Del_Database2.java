package renx.uu;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Del_Database2 {
	private Logger logger = LoggerFactory.getLogger(Del_Database2.class);

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

	public Del_CConnection getCConnection() throws SQLException {
		start();
		Del_CConnection cconn = new Del_CConnection();
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);

		cconn.o = conn;
		return cconn;
	}

	public Del_CConnection getCConnection(Del_CConnection cconn) throws SQLException {
		if (cconn != null && cconn.isOpen()) {
			Del_CConnection cconn2 = new Del_CConnection();
			cconn2.o = cconn.o;
			cconn2.self = false;
			return cconn2;
		}
		return getCConnection();
	}

	public Del_CConnection getCConnection(Connection conn) throws SQLException {
		if (conn != null && !conn.isClosed()) {
			Del_CConnection cconn2 = new Del_CConnection();
			cconn2.o = conn;
			cconn2.self = false;
			return cconn2;
		}
		return getCConnection();
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
	}
}
