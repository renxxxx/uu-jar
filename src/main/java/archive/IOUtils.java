package archive;

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

public class IOUtils {
	public static Logger logger = Logger.getLogger(IOUtils.class);

	public static String getMD5(File file) throws Exception {
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

	public static int copy(File in, File out) throws Exception {
		InputStream is = null;
		OutputStream os = null;
		try {
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
		OutputStream os = null;
		try {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (!file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			org.apache.commons.io.IOUtils.write(string, os);
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

	public static boolean replaceFileContent(File file, String regex, String newStr) throws IOException {
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

	public static boolean deleteRecursion(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileTmp : files) {
				if (!deleteRecursion(fileTmp))
					return false;
			}
			logger.debug("delete " + file.getAbsolutePath());
			return file.delete();
		} else {
			logger.debug("delete " + file.getAbsolutePath());
			return file.delete();
		}
	}

	public static boolean deleteFileUpEmpty(File file, File endFolder) {
		logger.debug("delete " + file.getAbsolutePath());
		boolean success = file.delete();
		if (file.getParentFile().list().length == 0
				&& !file.getParentFile().getAbsolutePath().equals(endFolder.getAbsolutePath())) {
			deleteFileUpEmpty(file.getParentFile(), endFolder);
		}
		return success;
	}

}
