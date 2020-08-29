package inininininin.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Database {
	private static Logger logger = Logger.getLogger(Database.class);

	private static DataSource dataSource;

	public static void m2() throws Exception {
		System.out.println(StringUtil.newId());
	}

	public static void m1() throws Exception {
		Database database = new Database("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://47.110.157.60:54987/zaylt_prod?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
				"prod", "wqArF1wH#qJ@erT33Y3");
		Connection connection = database.connect();
		for (int j = 0; j < 4; j++) {
			List<Map> rows = JdbcUtil.queryList(connection, "select * from t_patient ");
			for (int i = 0; i < rows.size(); i++) {
				Map row = rows.get(i);
				row.put("id", StringUtil.newId());
				Map cR = JdbcUtil.query(connection,
						"select id clinicId,hospitalId from t_hospital_clinic where id != ? order by rand()",
						row.get("clinicId"));
				row.put("clinicId", cR.get("clinicId"));
				row.put("hospitalId", cR.get("hospitalId"));
				row.put("virtual", 1);
				List values = new ArrayList(row.values());
				System.out.println(values);

//				JdbcUtils.update(connection,
//						"insert into t_patient values(" + JdbcUtils.buildSql_placeholderList(values.size()) + ")",
//						values);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public Database(String driver, String connectURI, String username, String password)
			throws IOException, SQLException {
		logger.info("into");
		org.apache.tomcat.jdbc.pool.DataSource tomcatJdbcPoolDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		tomcatJdbcPoolDataSource.setDriverClassName(driver);
		tomcatJdbcPoolDataSource.setUrl(connectURI);
		tomcatJdbcPoolDataSource.setUsername(username);
		tomcatJdbcPoolDataSource.setPassword(password);
		tomcatJdbcPoolDataSource.setTestOnBorrow(true);
		tomcatJdbcPoolDataSource.setDefaultAutoCommit(false);
		tomcatJdbcPoolDataSource.setRollbackOnReturn(true);
		tomcatJdbcPoolDataSource.setValidationQuery("SELECT 1");
		dataSource = tomcatJdbcPoolDataSource;
		logger.info("out");
	}

	public Database(DataSource dataSource) throws IOException, SQLException {
		this.dataSource = dataSource;
	}

	public Connection connect(Connection connection) throws SQLException {
		logger.info("into");
		if (connection == null) {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
		}
		logger.info("out");
		return connection;
	}

	public Connection connect() throws SQLException {
		return connect(null);
	}

	public void commit(Connection connection) throws SQLException {
		if (connection != null && !connection.getAutoCommit())
			connection.commit();
	}

	public void rollback(Connection connection) throws SQLException {
		if (connection != null && !connection.getAutoCommit())
			connection.rollback();
	}

	public void close(Connection connection) throws SQLException {
		if (connection != null) {
			rollback(connection);
			connection.close();
		}
	}

}
