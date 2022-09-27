package cc.renx.uu;

import java.sql.SQLException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class Lockuu {

	private static Logger logger = LoggerFactory.getLogger(Lockuu.class);

	public static boolean lock(Jedis jedis, String lock, String owner, long period)
			throws SQLException, InterruptedException {
		int i = 0;
		while (true) {
			String result = jedis.set(lock, owner, "NX", "PX", period);
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

	public static boolean lock(Jedis jedis, String lock, String owner) throws SQLException, InterruptedException {
		return lock(jedis, lock, owner, 1 * 60 * 1000);
	}

	public static boolean unlock(Jedis jedis, String lock, String owner) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lock), Collections.singletonList(owner));

		Long RELEASE_SUCCESS = 1L;
		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}
}
