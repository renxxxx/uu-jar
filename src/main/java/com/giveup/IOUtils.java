package com.giveup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class IOUtils {
	public static Logger logger = Logger.getLogger(IOUtils.class);

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

	public static int write(File in, OutputStream os) throws Exception {
		InputStream is = null;
		try {
			is = new FileInputStream(in);
			return org.apache.commons.io.IOUtils.copy(is, os);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null)
				is.close();
		}
	}

	public static int write(InputStream is, File out) throws Exception {
		OutputStream os = null;
		try {
			if (!out.getParentFile().exists())
				out.getParentFile().mkdirs();
			if (!out.exists())
				out.createNewFile();
			os = new FileOutputStream(out);
			return org.apache.commons.io.IOUtils.copy(is, os);
		} catch (Exception e) {
			throw e;
		} finally {
			if (os != null)
				os.close();
		}
	}

	public static void deleteRecursion(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileTmp : files) {
				deleteRecursion(fileTmp);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	public static void deleteFileUpEmpty(File file) {
		file.delete();
		logger.debug(file.getParentFile().list());
		if (file.getParentFile().list().length == 0) {
			deleteFileUpEmpty(file.getParentFile());
		} else {
			return;
		}
	}

	public static void main(String[] args) {
		String s = "C:\\data\\renxinwei\\webroot\\oss\\zaylt\\tmp\\zaylt.log";
		deleteFileUpEmpty(new File(s));
	}
}
