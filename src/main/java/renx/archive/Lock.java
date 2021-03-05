package renx.archive;

import java.sql.SQLException;
import java.util.Collections;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class Lock {

	public static Logger logger = Logger.getLogger(Lock.class);

	public static boolean distributedLock(Jedis jedis, String lock, String locker, long period)
			throws SQLException, InterruptedException {
		int i = 0;
		while (true) {
			String result = jedis.set(lock, locker, "NX", "PX", period);
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

	public static boolean distributedLock(Jedis jedis, String lock, String locker)
			throws SQLException, InterruptedException {
		return distributedLock(jedis, lock, locker, 1 * 60 * 1000);
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
