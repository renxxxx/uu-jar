package renx.uu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JJedis {
	public JedisPool jedisPool;
	public Jedis jedis;
	public boolean self = true;
	public String prefix = "";

	public static JJedis build() {
		return build(null, null);
	}

	public static JJedis build(JedisPool jedisPool) {
		return build(jedisPool, null);
	}

	public static JJedis build(JedisPool jedisPool, JJedis jjedis) {
		JJedis jjedis2 = new JJedis();
		jjedis2.jedisPool = jedisPool;
		if (jjedis != null && jjedis.jedis != null) {
			jjedis2.jedis = jjedis.jedis;
			jjedis2.self = false;
		}
		return jjedis2;
	}

	public JJedis prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public String buildKey(String key) {
		return this.prefix == null || this.prefix.isEmpty() ? key : (this.prefix + "-" + key);
	}

	public String get(final String key) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.get(buildKey(key));
	}

	public String set(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.set(buildKey(key), value);
	}

	public Long del(final String key) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.del(buildKey(key));
	}

	public String getSet(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.getSet(buildKey(key), value);
	}

	public Long setnx(final String key, String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.setnx(buildKey(key), value);
	}

	public String setex(final String key, final int seconds, final String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.setex(buildKey(key), seconds, value);
	}

	public String psetex(final String key, final long milliseconds, final String value) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.psetex(buildKey(key), milliseconds, value);
	}

	public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.set(buildKey(key), value, key, value, time);
	}

	public void close() {
		if (self && jedis != null) {
			jedis.close();
		}
	}

}
