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

	public static boolean deleteRecursion(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileTmp : files) {
				if (!deleteRecursion(fileTmp))
					return false;
			}
			logger.info("delete " + file.getAbsolutePath());
			return file.delete();
		} else {
			logger.info("delete " + file.getAbsolutePath());
			return file.delete();
		}
	}

	public static boolean deleteFileUpEmpty(File file) {
		logger.info("delete " + file.getAbsolutePath());
		boolean success = file.delete();
		if (file.getParentFile().list().length == 0) {
			deleteFileUpEmpty(file.getParentFile());
		}
		return success;
	}

	public static String fileExtStrip(String fileName) {
		String[] fileNameParts = fileName.split("\\.");
		StringBuilder prefixSb = new StringBuilder("");
		int toIndex = fileNameParts.length;
		if (fileNameParts.length > 1)
			toIndex--;

		for (int i = 0; i < toIndex; i++) {
			prefixSb = prefixSb.append(".").append(fileNameParts[i]);
		}
		String prefix = prefixSb.toString();
		if (prefix.length() > 1)
			prefix = prefix.substring(1);
		return prefix;
	}

	public static String fileExtGet(String fileName) {
		String[] parts = fileName.split("\\.");
		if (parts.length == 1)
			return null;

		return parts[parts.length - 1];
	}

	public static void main(String[] args) {
		String s = "C:\\data\\renxinwei\\webroot\\oss\\zaylt\\tmp\\zaylt.log";
		deleteFileUpEmpty(new File(s));
	}
}
