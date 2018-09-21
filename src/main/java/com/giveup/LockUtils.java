package com.giveup;

import java.sql.SQLException;
import java.util.Collections;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class LockUtils {
	
	public static Logger logger = Logger.getLogger(LockUtils.class);

	public static boolean distributedLock(String lock, String locker) throws Exception {
		Jedis jedis = null;
		try {
			return distributedLock(jedis, lock, locker);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	public static boolean distributedLock(Jedis jedis, String lock, String locker) throws SQLException, InterruptedException {
		int i = 0;
		while (true) {
			String result = jedis.set(lock, locker, "NX", "PX", 1 * 60 * 1000);
			if ("OK".equals(result)) {
				return true;
			}
			i++;
			if (i > 5)
				break;
			Thread.sleep(1000);
		}
		return false;
	}

	public static boolean distributedUnlock(String lock, String locker) throws Exception {
		Jedis jedis = null;
		try {
			return distributedUnlock(jedis, lock, locker);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	public static boolean distributedUnlock(Jedis jedis, String lock, String locker) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lock), Collections.singletonList(locker));

		Long RELEASE_SUCCESS = 1L;
		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}
}
