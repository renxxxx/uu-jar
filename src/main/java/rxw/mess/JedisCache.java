package rxw.mess;

import java.sql.SQLException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisCache {
	private static Logger logger = Logger.getLogger(JedisCache.class);

	public JedisPool jedisPool;
	public String ip;
	public int port;
	public String auth;

	public void setJedisPool() {
		if (this.jedisPool == null) {
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			this.jedisPool = new JedisPool(config, ip, port, 10000, auth, 0);
		}
	}

	public Jedis connect(Jedis jedis) throws SQLException {
		setJedisPool();
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
		System.out.println(StringUtils.newId());
	}
}
