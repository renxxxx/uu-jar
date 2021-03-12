package renx.archive;

import java.sql.SQLException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {
	private static Logger logger = Logger.getLogger(Redis.class);

	public JedisPool jedisPool;
	public String ip;
	public int port;
	public String auth;
	public boolean started = false;

	public void start() {
		if (this.started)
			return;
		try {
			if (this.jedisPool != null) {
				this.jedisPool.close();
				this.jedisPool.destroy();
				this.jedisPool = null;
			}
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			this.jedisPool = new JedisPool(config, ip, port, 10000, auth, 0);
			started = true;
		} catch (Exception e) {
			started = false;
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		if (this.jedisPool != null) {
			this.jedisPool.close();
			this.jedisPool.destroy();
			this.jedisPool = null;
		}
	}

	public Jedis connect(Jedis jedis) throws SQLException {
		start();
		if (jedis == null) {
			jedis = jedisPool.getResource();
		}
		return jedis;
	}

	public Jedis connect() throws SQLException {
		return connect(null);
	}

	public void close(Jedis jedis) throws SQLException {
		if (jedis != null) {
			jedis.close();
		}
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
		System.out.println(Stringuu.newId());
	}
}
