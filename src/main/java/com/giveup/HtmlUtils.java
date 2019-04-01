package com.giveup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtils {
	public static Logger logger = Logger.getLogger(HtmlUtils.class);

	// public static List<String> extractOffUrls(String oldcontent, String
	// newcontent) throws InterruptedException {
	// oldcontent = oldcontent == null ? "" : oldcontent;
	// newcontent = newcontent == null ? "" : newcontent;
	// List<String> offUrls = new ArrayList<String>();
	// List<String> oldUrls = HtmlUtils.extractUrls(oldcontent);
	// List<String> newUrls = HtmlUtils.extractUrls(newcontent);
	// for (String oldUrl : oldUrls) {
	// if (!newUrls.contains(oldUrl))
	// offUrls.add(oldUrl);
	// }
	// return offUrls;
	// }

	public static List<String> extractOffUrls(String oldcontent, String newcontent) throws InterruptedException {
		oldcontent = oldcontent == null ? "" : oldcontent;
		newcontent = newcontent == null ? "" : newcontent;
		if (oldcontent.equals(newcontent))
			return new ArrayList<String>();
		List<String> oldUrls = HtmlUtils.extractUrls(oldcontent);
		List<String> newUrls = HtmlUtils.extractUrls(newcontent);
		return OtherUtils.extractOffStrs(oldUrls, newUrls, true);
	}

	public static List<String> extractOffUrls(File oldFile, File newFile) throws Exception {
		return extractOffUrls(new FileInputStream(oldFile), new FileInputStream(newFile));
	}

	public static List<String> extractOffUrls(InputStream oldis, InputStream newis) throws Exception {
		List<String> oldUrls = extractUrls(oldis);
		List<String> newUrls = extractUrls(newis);
		List<String> oUrls = new ArrayList<String>();
		for (String oldUrl : oldUrls) {
			if (!newUrls.contains(oldUrl))
				oUrls.add(oldUrl);
		}
		return oUrls;
	}

	public static void main(String[] args) {
		Document doc = Jsoup.parse("asdfsadf<img src='asdfsdafasdf' /><img src='11asdfsdafasdf'/>/n<iframe ");
		System.out.println(extractUrls(doc));

	}

	public static List<String> extractUrls(Document doc) {
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
		Document doc = Jsoup.parse(htmlStr);
		return extractUrls(doc);
	}

	public static List<String> extractUrls(InputStream in) throws IOException {
		Document doc = Jsoup.parse(in, "utf-8", null);
		return extractUrls(doc);
	}

	public static List<String> extractUrls(File file) throws IOException {
		Document doc = Jsoup.parse(file, "utf-8");
		return extractUrls(doc);
	}

	public static String removeTag(String htmlStr) {
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
