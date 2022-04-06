package renx.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CConnection {
	public Connection o;
	public boolean self = true;

	public static CConnection build() throws SQLException {
		return new CConnection();
	}

	public static CConnection build(CConnection cconn) throws SQLException {
		if (cconn == null)
			return build();
		else
			return cconn;
	}

	public static CConnection build(Connection conn) throws SQLException {
		CConnection cconn = new CConnection();
		cconn.o = conn;
		return cconn;
	}

	public void commit() throws SQLException {
		if (self && o != null)
			o.commit();
	}

	public void rollback() throws SQLException {
		if (self && o != null)
			o.rollback();
	}

	public void close() throws SQLException {
		if (self && o != null)
			o.close();
	}

	public boolean isClosed() throws SQLException {
		if (o != null)
			return o.isClosed();
		else
			return true;
	}

	public boolean isOpen() throws SQLException {
		return !isClosed();
	}

	public void isReadOnly() throws SQLException {
		if (o != null)
			o.isReadOnly();
	}

	public InputStream getStream(String sql, Object... params) throws Exception {
		return Jdbcuu.getStream(o, sql, params);
	}

	public Integer getInteger(String sql, Object... params) throws Exception {
		return Jdbcuu.getInteger(o, sql, params);
	}

	public String getString(String sql, Object... params) throws Exception {
		return Jdbcuu.getString(o, sql, params);
	}

	public LList getJsonArray(String sql, Object... params) throws Exception {
		return Jdbcuu.getJsonArray(o, sql, params);
	}

	public MMap getJson(String sql, Object... params) throws Exception {
		return Jdbcuu.getJson(o, sql, params);
	}

	public BigDecimal getDecimal(String sql, Object... params) throws Exception {
		return Jdbcuu.getDecimal(o, sql, params);
	}

	public Long getLong(String sql, Object... params) throws Exception {
		return Jdbcuu.getLong(o, sql, params);
	}

	public Float getFloat(String sql, Object... params) throws Exception {
		return Jdbcuu.getFloat(o, sql, params);
	}

	public Date getDate(String sql, Object... params) throws Exception {
		return Jdbcuu.getDate(o, sql, params);
	}

	public Object getColumn(String sql, Object... params) throws Exception {
		return Jdbcuu.getColumn(o, sql, params);
	}
}
