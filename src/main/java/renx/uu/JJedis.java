package renx.uu;

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
		open();
		return jedis.ttl(buildKey(key));
	}

	public String get(final String key) {
		open();
		return jedis.get(buildKey(key));
	}

	public String set(final String key, String value) {
		open();
		return jedis.set(buildKey(key), value);
	}

	public Long del(final String key) {
		open();
		return jedis.del(buildKey(key));
	}

	public String getSet(final String key, String value) {
		open();
		return jedis.getSet(buildKey(key), value);
	}

	public Long setnx(final String key, String value) {
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
		open();
		return jedis.set(buildKey(key), value, key, value, time);
	}

	public void close() {
		if (self && jedis != null) {
			logger.info("close " + runId);
			jedis.close();
		}
	}

	public void open() {
		if (jedis == null) {
			logger.info("open " + runId);
			jedis = jedisPool.getResource();
		}
	}

	public boolean lock(String lockName) {
		logger.info("start lock " + runId);
		open();
		long acquireTimeout = 0;
		long lockTimeout = 0;

		String realLockName = prefix + lockName;
		logger.info("lock realLockName " + realLockName);
		String theadId = String.valueOf(Thread.currentThread().getId());
		if (acquireTimeout == 0) {
			// 如未设置锁获取超时时间默认为10秒
			acquireTimeout = 10000;
		}
		if (lockTimeout == 0) {
			// 如未设置锁超时时间默认未60秒
			lockTimeout = 60000;
		}
		int lockTimeoutMin = (int) (lockTimeout / 1000);
		long acquireEndTime = System.currentTimeMillis() + acquireTimeout;
		// 防止未设置时间产生死锁
		if (jedis.ttl(realLockName) == -1) {
			jedis.expire(realLockName, lockTimeoutMin);
		}
		while (System.currentTimeMillis() < acquireEndTime) {
			// 加锁成功
			if (theadId.equals(jedis.get(realLockName)) || jedis.setnx(realLockName, theadId) == 1) {
				logger.info("lock realLockName " + realLockName + " true");
				jedis.expire(realLockName, lockTimeoutMin);
				return true;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("lock realLockName " + realLockName + " false");
		return false;
	}

	public boolean unlock(String lockName) {
		logger.info("start unlock " + runId);
		open();
		String realLockName = prefix + lockName;
		logger.info("unlock realLockName " + realLockName);
		String theadId = String.valueOf(Thread.currentThread().getId());
		boolean isRelease = false;
		while (true) {
			// jedis事务操作 监控realLockName
			// 如果其中值发生变化tx.exec事务不会提交(注jedis中的事务不具备原子性,其中一个事务有错误，另外的仍然执行)
			jedis.watch(realLockName);
			if (theadId.equals(jedis.get(realLockName))) {
				Transaction tx = jedis.multi();
				tx.del(realLockName);
				if (tx.exec().isEmpty()) {
					continue;
				}
				isRelease = true;
			}
			// q取消监控
			jedis.unwatch();
			break;
		}
		logger.info("unlock realLockName " + realLockName + " " + isRelease);
		return isRelease;
	}
}
