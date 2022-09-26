package renx.cc.uu;

import java.sql.SQLException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class JJedis {
	private static Logger logger = LoggerFactory.getLogger(JJedis.class);

	public JedisPool jedisPool;
	public Jedis jedis;
	public String runId = Stringuu.timeId();
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
			jjedis2.prefix = jjedis.prefix;
			jjedis2.runId = jjedis.runId;
			jjedis2.self = false;
		}
		return jjedis2;
	}

	public JJedis prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public JJedis runId(String runId) {
		this.runId = runId;
		return this;
	}

	public String buildKey(String key) {
		return this.prefix == null || this.prefix.isEmpty() ? key : (this.prefix + "-" + key);
	}

	public Long ttl(final String key) {
		logger.info("ttl " + runId);
		open();
		return jedis.ttl(buildKey(key));
	}

	public String get(final String key) {
		logger.info("get " + runId);
		open();
		return jedis.get(buildKey(key));
	}

	public String set(final String key, String value) {
		open();
		return jedis.set(buildKey(key), value);
	}

	public Long del(final String key) {
		logger.info("del " + runId);
		open();
		return jedis.del(buildKey(key));
	}

	public String getSet(final String key, String value) {
		open();
		return jedis.getSet(buildKey(key), value);
	}

	public Long setnx(final String key, String value) {
		logger.info("setnx " + runId);
		open();
		return jedis.setnx(buildKey(key), value);
	}

	public String setex(final String key, final int seconds, final String value) {
		open();
		return jedis.setex(buildKey(key), seconds, value);
	}

	public String psetex(final String key, final long milliseconds, final String value) {
		open();
		return jedis.psetex(buildKey(key), milliseconds, value);
	}

	public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
		logger.info("set " + runId);
		open();
		return jedis.set(buildKey(key), value, nxxx, expx, time);
	}

	public void close() {
		if (self && jedis != null) {
			logger.info("close " + runId);
			jedis.close();
		}
	}

	public synchronized void open() {
		if (jedis == null) {
			logger.info("open " + runId);
			jedis = jedisPool.getResource();
		}
	}

	public boolean lock(String lock, String owner, long acquireTime, long period)
			throws SQLException, InterruptedException {
		logger.info("lock " + runId);
		open();
		long ccc = 0;
		while (true) {
			String result = set(lock, owner, "NX", "PX", period);
			logger.info("lock result " + result);
			if ("OK".equals(result)) {
				logger.info("lock success " + "lock: " + lock + " owner: " + owner);
				return true;
			}
			if (ccc > acquireTime)
				break;

			Thread.sleep(100);
			ccc += 100;
		}
		logger.info("lock fail " + "lock: " + lock + " owner: " + owner);
		return false;
	}

	public boolean lock(String lock, String owner) throws SQLException, InterruptedException {
		return lock(lock, owner, 10 * 1000, 1 * 60 * 1000);
	}

	public boolean unlock(String lock, String owner) {
		logger.info("unlock " + runId);
		open();
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(buildKey(lock)), Collections.singletonList(owner));

		Long RELEASE_SUCCESS = 1L;
		if (RELEASE_SUCCESS.equals(result)) {
			logger.info("unlock success " + "lock: " + lock + " owner: " + owner);
			return true;
		}
		logger.info("unlock fail " + "lock: " + lock + " owner: " + owner);
		return false;

	}
}
