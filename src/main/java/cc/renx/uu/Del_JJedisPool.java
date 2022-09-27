package cc.renx.uu;

import java.sql.SQLException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Del_JJedisPool {
	private static Logger logger = LoggerFactory.getLogger(Del_JJedisPool.class);

	public JedisPool jedisPool;
	public String ip = "127.0.0.1";
	public int port = 6379;
	public String auth;
	public boolean started = false;

	public void start() {
		if (this.jedisPool != null && !this.jedisPool.isClosed())
			return;
		if (this.jedisPool != null) {
			this.jedisPool.close();
			this.jedisPool.destroy();
			this.jedisPool = null;
		}
		JedisPoolConfig config = new JedisPoolConfig();
		this.jedisPool = new JedisPool(config, ip, port, 2000, auth, 0);
		started = true;
	}

	public void stop() {
		if (this.jedisPool != null) {
			this.jedisPool.close();
			this.jedisPool.destroy();
			this.jedisPool = null;
		}
	}

	public Jedis getConnection() throws SQLException {
		start();
		Jedis jedis = this.jedisPool.getResource();
		return jedis;
	}

	public void closeConnection(Jedis jedis) throws SQLException {
		if (jedis != null)
			jedis.close();
	}

	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
	}
}
