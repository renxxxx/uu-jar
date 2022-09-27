package cc.renx.uu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Htmluu {
	private static Logger logger = LoggerFactory.getLogger(Htmluu.class);

	public static List<String> extractOffUrls(String oldHtml, String newHtml) throws InterruptedException {
		if (oldHtml == null)
			return null;
		if (newHtml == null)
			return null;
		if (oldHtml.equals(newHtml))
			return new ArrayList<String>();
		List<String> oldUrls = Htmluu.extractUrls(oldHtml);
		List<String> newUrls = Htmluu.extractUrls(newHtml);
		return Listuu.extractOffEles(oldUrls, newUrls);
	}

	public static List<String> extractOffUrls(File oldFile, File newFile) throws Exception {
		if (oldFile == null)
			return null;
		if (newFile == null)
			return null;
		return extractOffUrls(new FileInputStream(oldFile), new FileInputStream(newFile));
	}

	public static List<String> extractOffUrls(InputStream oldis, InputStream newis) throws Exception {
		if (oldis == null)
			return null;
		if (newis == null)
			return null;
		List<String> oldUrls = extractUrls(oldis);
		List<String> newUrls = extractUrls(newis);
		List<String> oUrls = new ArrayList<String>();
		return Listuu.extractOffEles(oldUrls, newUrls);
	}

	public static void main(String[] args) {
		String html = "";
		Document doc = Jsoup.parse("asdfsadf<img src='asdfsdafasdf' /><img src='11asdfsdafasdf'/>/n<iframe ");
		System.out.println(extractUrls(doc));

	}

	public static List<String> extractUrls(Document doc) {
		if (doc == null)
			return null;
		Elements elements = doc.getElementsByAttribute("src");
		List<String> srcs = new ArrayList();
		for (int i = 0; i < elements.size(); i++) {
			Element media = elements.get(i);
			String src = media.attr("src");
			srcs.add(src);
		}
		return srcs;
	}

	public static List<String> extractUrls(String htmlStr) {
		if (htmlStr == null)
			return null;
		Document doc = Jsoup.parse(htmlStr);
		return extractUrls(doc);
	}

	public static List<String> extractUrls(InputStream in) throws IOException {
		if (in == null)
			return null;
		Document doc = Jsoup.parse(in, "utf-8", null);
		return extractUrls(doc);
	}

	public static List<String> extractUrls(File file) throws IOException {
		if (file == null)
			return null;
		Document doc = Jsoup.parse(file, "utf-8");
		return extractUrls(doc);
	}

	public static String extractAllText(String htmlStr) {
		if (htmlStr == null)
			return null;
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
		String regEx_html = "<[^>]+>"; // HTML tag
		String regEx_space = "\\s+|\t|\r|\n";// other characters

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll("");
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll("");
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll("");
		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(" ");
		return htmlStr;
	}
}
