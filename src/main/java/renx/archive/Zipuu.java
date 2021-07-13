package renx.archive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zipuu {
	private static Logger logger = LoggerFactory.getLogger(Zipuu.class);

	public String zip;
	public Map<String, String> map = new LinkedHashMap();
	public ZipOutputStream out = null;

	public static void main(String[] args) throws Exception {
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

}
