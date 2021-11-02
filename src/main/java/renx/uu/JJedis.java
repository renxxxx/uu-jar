package renx.uu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class JJedis {
	private static Logger logger = LoggerFactory.getLogger(JJedis.class);

	public Jedis upperJedis;
	public Jedis myJedis;
	public Jedis jedis;

	public void close() {
		if (myJedis != null) {
			myJedis.close();
			myJedis = null;
		}
	}
}
