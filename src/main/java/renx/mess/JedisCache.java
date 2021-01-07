package renx.mess;

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
	public boolean inited = false;

	public void init() {
		if (!this.inited) {
			if (this.jedisPool != null) {
				this.jedisPool.close();
				this.jedisPool.destroy();
			}
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			this.jedisPool = new JedisPool(config, ip, port, 10000, auth, 0);
		}
		this.inited = true;
	}

	public Jedis connect(Jedis jedis) throws SQLException {
		init();
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
