package renx.uu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

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

	public Long ttl(final String key) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		return jedis.ttl(buildKey(key));
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

	public boolean lock(String lockName) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		long acquireTimeout = 0;
		long lockTimeout = 0;

		String realLockName = prefix + lockName;
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
				jedis.expire(realLockName, lockTimeoutMin);
				return true;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean unlock(String lockName) {
		if (jedis == null)
			jedis = jedisPool.getResource();
		String realLockName = prefix + lockName;
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
		return isRelease;
	}
}
