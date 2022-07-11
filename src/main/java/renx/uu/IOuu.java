package renx.uu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOuu {
	private static Logger logger = LoggerFactory.getLogger(IOuu.class);

	public static void downloadAsync(String url, String to, Map headers) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					download(url, to, headers);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

	public static void downloadSilent(String url, String to, Map headers) {
		try {
			download(url, to, headers);
		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
		}
	}

	public static void download(String url, String to, Map headers) throws Exception {
		logger.debug("download " + url + " to " + to + " " + headers);
		if (url == null || url.isEmpty() || to == null || to.isEmpty())
			return;
		url = Urluu.beautify(url);
		int bytesum = 0;
		int byteread = 0;
		URL urll = new URL(url);
		HttpURLConnection conn = null;
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			conn = (HttpURLConnection) urll.openConnection();
			if (headers != null) {
				Set keySet = headers.keySet();
				Iterator itr = keySet.iterator();
				while (itr.hasNext()) {
					Object key = itr.next();
					Object value = headers.get(key);
					if (key != null && value != null)
						conn.addRequestProperty(key.toString(), value.toString());
				}
			}

			if (conn.getResponseCode() != 200)
				return;

			inStream = conn.getInputStream();
			File file = new File(to);
			boolean ff = file.getParentFile().mkdirs();
			fs = new FileOutputStream(to);

			byte[] buffer = new byte[1204];
			int length;
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (inStream != null)
				inStream.close();
			if (fs != null)
				fs.close();
		}
	}

	public static String md5(File file) throws Exception {
		FileInputStream fileInputStream = null;
		try {
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (Exception e) {
			throw e;
		} finally {
			if (fileInputStream != null)
				fileInputStream.close();

		}
	}

	public static Integer copy(File in, File out) throws Exception {
		if (in == null || out == null)
			return null;
		InputStream is = null;
		OutputStream os = null;
		try {
			out.getParentFile().mkdirs();
			is = new FileInputStream(in);
			os = new FileOutputStream(out);
			return org.apache.commons.io.IOUtils.copy(is, os);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}

	}

	public static Integer copy(String in, String out) throws Exception {
		if (in == null || in.isEmpty() || out == null || out.isEmpty())
			return null;
		return copy(new File(in), new File(out));
	}

	public static String toString(File file) throws Exception {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return org.apache.commons.io.IOUtils.toString(is);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null)
				is.close();
		}

	}

	public static void write(String string, File file) throws Exception {
		write(string, file, "utf-8");
	}

	public static void write(String string, File file, String charset) throws Exception {
		if (string == null || file == null)
			return;
		write(string.getBytes(charset), file);
	}

	public static void write(byte[] data, File file) throws Exception {
		if (data == null || file == null)
			return;
		OutputStream os = null;
		try {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (!file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			org.apache.commons.io.IOUtils.write(data, os);
		} catch (Exception e) {
			throw e;
		} finally {
			if (os != null)
				os.close();
		}
	}

	public static int write(File file, OutputStream os) throws Exception {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return org.apache.commons.io.IOUtils.copy(is, os);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null)
				is.close();
		}
	}

	public static int write(InputStream is, File file) throws Exception {
		OutputStream os = null;
		try {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (!file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			return org.apache.commons.io.IOUtils.copy(is, os);
		} catch (Exception e) {
			throw e;
		} finally {
			if (os != null)
				os.close();
		}
	}

	public static boolean replace(File file, String regex, String newStr) throws IOException {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			String line = null;
			long lastPoint = 0; // 记住上一次的偏移量
			while ((line = raf.readLine()) != null) {
				final long ponit = raf.getFilePointer();
				String str = line.replaceAll(regex, newStr);
				raf.seek(lastPoint);
				raf.writeBytes(str);
				lastPoint = ponit;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			raf.close();
		}
		return true;
	}

	public static boolean delete(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileTmp : files) {
				if (!delete(fileTmp))
					return false;
			}
			logger.debug("delete " + file.getAbsolutePath());
			return file.delete();
		} else {
			logger.debug("delete " + file.getAbsolutePath());
			return file.delete();
		}
	}

	public static boolean delete(String path) {
		return delete(new File(path));
	}

	public static boolean deleteUptoEmpty(File file, File limit) {
		logger.debug("delete " + file.getAbsolutePath());
		boolean success = file.delete();
		if (file.getParentFile().list().length == 0
				&& !file.getParentFile().getAbsolutePath().equals(limit.getAbsolutePath())) {
			deleteUptoEmpty(file.getParentFile(), limit);
		}
		return success;
	}

}
