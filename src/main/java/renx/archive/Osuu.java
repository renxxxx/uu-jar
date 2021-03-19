package renx.archive;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Osuu {
	private static Logger logger = LoggerFactory.getLogger(Osuu.class);

	public static void exec(String commandLinux, String commandWindows) throws IOException, InterruptedException {
		String command = Uu.isLinux() ? commandLinux : Uu.isWindows() ? commandWindows : null;
		logger.debug(command);
		if (command == null) {
			logger.debug("unknown os,can`t run the command specified");
			return;
		}
		Process ps = Runtime.getRuntime().exec(command);
		ps.waitFor();
	}
}
