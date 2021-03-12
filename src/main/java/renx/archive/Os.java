package renx.archive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

public class Os {
	public static Logger logger = Logger.getLogger(Os.class);

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
