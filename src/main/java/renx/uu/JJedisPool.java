package renx.uu;

import java.sql.SQLException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JJedisPool {
	private static Logger logger = LoggerFactory.getLogger(JJedisPool.class);

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

	public JJedis connect(JJedis jjedis) throws SQLException {
		start();
		JJedis jjedis1 = new JJedis();
		if (jjedis == null) {
			jjedis1.jedis = jjedis1.myJedis = jedisPool.getResource();
		} else {
			jjedis1.jedis = jjedis1.upperJedis = jedisPool.getResource();
		}
		return jjedis1;
	}

	public JJedis connect() throws SQLException {
		return connect(null);
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
		System.out.println(Stringuu.newId());
	}
}
