package renx.uu;

import java.sql.Connection;
import java.sql.SQLException;

public class CConnection {
	public Connection o;
	public boolean self = true;

	public CConnection build(Connection conn) throws SQLException {
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

	public void isClosed() throws SQLException {
		if (o != null)
			o.isClosed();
	}

	public void isReadOnly() throws SQLException {
		if (o != null)
			o.isReadOnly();
	}
}
