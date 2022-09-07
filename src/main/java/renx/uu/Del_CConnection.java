package renx.uu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class Del_CConnection {
	public Connection o;
	public boolean self = true;

	public static Del_CConnection build() throws SQLException {
		return new Del_CConnection();
	}

	public static Del_CConnection build(Del_CConnection cconn) throws SQLException {
		if (cconn == null)
			return build();
		else
			return cconn;
	}

	public static Del_CConnection build(Connection conn) throws SQLException {
		Del_CConnection cconn = new Del_CConnection();
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
		return Jdbcuu2.getStream(o, sql, params);
	}

	public Integer getInteger(String sql, Object... params) throws Exception {
		return Jdbcuu2.getInteger(o, sql, params);
	}

	public String getString(String sql, Object... params) throws Exception {
		return Jdbcuu2.getString(o, sql, params);
	}

//	public LList getList(String sql, Object... params) throws Exception {
//		return Jdbcuuu.getList(o, sql, params);
//	}

	public JSONObject getMap(String sql, Object... params) throws Exception {
		return Jdbcuu2.getMap(o, sql, params);
	}

	public BigDecimal getDecimal(String sql, Object... params) throws Exception {
		return Jdbcuu2.getDecimal(o, sql, params);
	}

	public Long getLong(String sql, Object... params) throws Exception {
		return Jdbcuu2.getLong(o, sql, params);
	}

	public Float getFloat(String sql, Object... params) throws Exception {
		return Jdbcuu2.getFloat(o, sql, params);
	}

	public Date getDate(String sql, Object... params) throws Exception {
		return Jdbcuu2.getDate(o, sql, params);
	}

	public Object getObject(String sql, Object... params) throws Exception {
		return Jdbcuu2.getObject(o, sql, params);
	}
}
