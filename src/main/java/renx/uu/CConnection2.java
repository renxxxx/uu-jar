package renx.uu;

import java.sql.Connection;
import java.sql.SQLException;

public class CConnection2 {
	public Connection o;
	public boolean self = true;

	public static CConnection2 build() throws SQLException {
		return new CConnection2();
	}

	public static CConnection2 build(CConnection2 cconn) throws SQLException {
		if (cconn == null)
			return build();
		else
			return cconn;
	}

	public static CConnection2 build(Connection conn) throws SQLException {
		CConnection2 cconn = new CConnection2();
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
}
