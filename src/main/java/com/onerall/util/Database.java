package com.onerall.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class Database {

	public static DataSource dataSource;

	public static void m2() throws Exception {
		System.out.println(StrUtil.newId());
	}

	public static void m1() throws Exception {
		Database database = new Database("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://47.110.157.60:54987/zaylt_prod?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
				"prod", "wqArF1wH#qJ@erT33Y3");
		Connection connection = database.on();
		for (int j = 0; j < 4; j++) {
			List<Map> rows = JdbcUtil.queryList(connection, "select * from t_patient ");
			for (int i = 0; i < rows.size(); i++) {
				Map row = rows.get(i);
				row.put("id", StrUtil.newId());
				Map cR = JdbcUtil.queryOne(connection,
						"select id clinicId,hospitalId from t_hospital_clinic where id != ? order by rand()",
						row.get("clinicId"));
				row.put("clinicId", cR.get("clinicId"));
				row.put("hospitalId", cR.get("hospitalId"));
				row.put("virtual", 1);
				List values = new ArrayList(row.values());
				System.out.println(values);

				JdbcUtil.update(connection,
						"insert into t_patient values(" + JdbcUtil.buildSql_placeholderList(values.size()) + ")",
						values);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public Database(String driver, String connectURI, String username, String password)
			throws IOException, SQLException {
		org.apache.tomcat.jdbc.pool.DataSource tomcatJdbcPoolDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		tomcatJdbcPoolDataSource.setDriverClassName(driver);
		tomcatJdbcPoolDataSource.setUrl(connectURI);
		tomcatJdbcPoolDataSource.setUsername(username);
		tomcatJdbcPoolDataSource.setPassword(password);
		tomcatJdbcPoolDataSource.setTestOnBorrow(true);
		tomcatJdbcPoolDataSource.setDefaultAutoCommit(true);
		tomcatJdbcPoolDataSource.setRollbackOnReturn(true);
		tomcatJdbcPoolDataSource.setValidationQuery("SELECT 1");
		dataSource = tomcatJdbcPoolDataSource;
	}

	public Database(DataSource dataSource) throws IOException, SQLException {
		this.dataSource = dataSource;
	}

	public Connection on(Connection connection) throws SQLException {
		if (connection == null)
			connection = dataSource.getConnection();
		return connection;
	}

	public Connection on() throws SQLException {
		return on(null);
	}

	public boolean subBeginCommitable(Connection connection) throws SQLException {
		if (connection == null || connection.getAutoCommit())
			return true;
		return false;
	}

	public Connection begin(Connection connection) throws SQLException {
		connection = on(connection);
		if (connection != null && connection.getAutoCommit())
			connection.setAutoCommit(false);
		return connection;
	}

	public Connection begin() throws SQLException {
		return begin(null);
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
		if (connection != null)
			connection.close();
	}

}
