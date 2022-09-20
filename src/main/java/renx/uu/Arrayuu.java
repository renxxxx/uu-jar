package renx.uu;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arrayuu {
	private static Logger logger = LoggerFactory.getLogger(Arrayuu.class);

	public static <T> T[] array(final T... items) {
		return ArrayUtils.toArray(items);
	}

}
