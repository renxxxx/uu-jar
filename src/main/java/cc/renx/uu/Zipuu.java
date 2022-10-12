package cc.renx.uu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zipuu {
	private static Logger logger = LoggerFactory.getLogger(Zipuu.class);

	public String zip;
	public Map<String, String> map = new LinkedHashMap();
	public ZipOutputStream out = null;

	public static void main(String[] args) throws Exception {
		main3();
	}

	public static void main2() throws Exception {
		Zipuu zipuu = Zipuu.build("C:/temp/132440421132.zip");

		// 先添加, 最后一起写入
		zipuu.add("catalina.log", "C:/temp/catalina.log");
		zipuu.add("132440421132/headimg.jpg", "C:/temp/132440421132/headimg.jpg");
		zipuu.add("132440421132/info.json", "C:/temp/132440421132/info.json");
		zipuu.add("132440421132/video.mp4", "C:/temp/132440421132/video.mp4");
		zipuu.write();

		// 边添加边写入
//		zipuu.addwrite("catalina.log", "C:/temp/catalina.log");
//		zipuu.addwrite("132440421132/headimg.jpg", "C:/temp/132440421132/headimg.jpg");
//		zipuu.addwrite("132440421132/info.json", "C:/temp/132440421132/info.json");
//		zipuu.addwrite("132440421132/video.mp4", "C:/temp/132440421132/video.mp4");
//		zipuu.out.close();
	}

	public static void main3() throws Exception {
		Zipuu.unzip("C:\\Users\\Administrator\\Desktop\\temp\\share-demo.zip",
				"C:\\Users\\Administrator\\Desktop\\temp\\share-demo");
	}

	public static Zipuu build(String zip) throws FileNotFoundException {
		Zipuu zipuu = new Zipuu();
		zipuu.zip = zip;
		return zipuu;
	}

	public static Zipuu build() throws FileNotFoundException {
		Zipuu zipuu = new Zipuu();
		return zipuu;
	}

	public void add(String zippath, String path) throws Exception {
		System.out.println("add " + zippath + "   " + path);
		logger.debug("addwrite " + zippath + "   " + path);
		map.put(zippath, path);
	}

	public void addwrite(String zippath, String path) throws Exception {
		System.out.println("addwrite " + zippath + "   " + path);
		logger.debug("addwrite " + zippath + "   " + path);
		if (out == null)
			out = new ZipOutputStream(new FileOutputStream(zip));
		map.put(zippath, path);
		out.putNextEntry(new ZipEntry(zippath));
		FileInputStream fos = new FileInputStream(path);
		int tag;
		// 将源文件写入到zip文件中
		while ((tag = fos.read()) != -1) {
			out.write(tag);
		}
		fos.close();
	}

	public void write() throws Exception {
		if (out == null)
			out = new ZipOutputStream(new FileOutputStream(zip));
		Iterator<String> irt = map.keySet().iterator();
		while (irt.hasNext()) {
			String zippath = irt.next();
			String path = map.get(zippath);
			System.out.println("write " + zippath + "   " + path);
			logger.debug("write " + zippath + "   " + path);
			out.putNextEntry(new ZipEntry(zippath));
			FileInputStream fos = new FileInputStream(path);
			int tag;
			// 将源文件写入到zip文件中
			while ((tag = fos.read()) != -1) {
				out.write(tag);
			}
			fos.close();
		}
		out.close();
		System.out.println("完成");
		logger.debug("完成");

	}

	/**
	 * 解压有zipFilePath所指定的Zip文件到destDirectory所指定的目录（如果目标目录不存在将会重新创建）
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		unzip(new FileInputStream(zipFilePath), destDirectory);
	}

	public static void unzip(InputStream fileIn, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		ZipInputStream zipIn = new ZipInputStream(fileIn, Charset.forName("utf-8"));

		ZipEntry entry = zipIn.getNextEntry();

		// 遍历Zip文件中的条目
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// 如果条目是文件直接解压
				extractFile(zipIn, filePath);
			} else {
				// 如果条目是目录, 创建对应的目录
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * 解压Zip包的条目 (文件条目)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}
