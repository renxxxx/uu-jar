package renx.uu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JJedis {
	public JedisPool jedisPool;
	public Jedis jedis;
	public boolean self = true;

	public static JJedis build() {
		return build(null, null);
	}

	public static JJedis build(JedisPool jedisPool) {
		return build(jedisPool, null);
	}

	public static JJedis build(JedisPool jedisPool, JJedis jjedis) {
		JJedis jjedis2 = new JJedis();
		jjedis2.jedisPool = jedisPool;
		if (jjedis != null && jjedis.jedis != null && !jjedis.jedis.isConnected()) {
			jjedis2.jedis = jjedis.jedis;
			jjedis2.self = false;
		}
		return jjedis2;
	}

	public String get(final String key) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.get(key);
	}

	public String set(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.set(key, value);
	}

	public Long del(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.del(key);
	}

	public String getSet(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.getSet(key, value);
	}

	public Long setnx(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.setnx(key, value);
	}

	public String setex(final String key, final int seconds, final String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.setex(key, seconds, value);
	}

	public String psetex(final String key, final long milliseconds, final String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.psetex(key, milliseconds, value);
	}

	public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.set(key, value, key, value, time);
	}

	public void close() {
		if (self && jedis != null) {
			jedis.close();
		}
	}

}
