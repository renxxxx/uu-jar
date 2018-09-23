package com.giveup;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class MainClass {
	public static void main(String[] args) throws IOException, SAXException {
		a8();
	}

	public static void a8() {
		String s = "001367e101b440bc9a260a54bf376dfb";
		String s1 = s.substring(0, 8);
		String s2 = s.substring(8, 12);
		String s3 = s.substring(12, 16);
		String s4 = s.substring(16, 20);
		String s5 = s.substring(20, 32);
		String ss = s1 + "-" + s2 + "-" + s3 + "-" + s4 + "-" + s5;
		System.out.println(ss);
	}

	public static int a7(String time1, String time2) {
		String[] time1Parts = time1.split(":");
		String[] time2Parts = time2.split(":");
		if (Integer.parseInt(time1Parts[0]) > Integer.parseInt(time2Parts[0]))
			return 1;
		else if (Integer.parseInt(time1Parts[0]) == Integer.parseInt(time2Parts[0])) {
			if (Integer.parseInt(time1Parts[1]) > Integer.parseInt(time2Parts[1]))
				return 1;
			else if (Integer.parseInt(time1Parts[1]) == Integer.parseInt(time2Parts[1])) {
				return 0;
			} else
				return -1;
		} else {
			return -1;
		}
	}

	public static void a6() {
		String alipayTradeno = "78190688";
		String alipayDateStr = alipayTradeno.substring(0, 8);
		try {
			Date alipayDate = new SimpleDateFormat("yyyyMMdd").parse(alipayDateStr);
			if (new Date().getTime() - alipayDate.getTime() > 3 * 30 * 24 * 60 * 60 * 1000l)
				throw new RuntimeException("请输入3个月内支付宝订单号");
		} catch (Exception e) {
			throw new RuntimeException("支付宝订单号异常");
		}

	}

	public static void a5() throws MalformedURLException, IOException {
		String root = "http://www.81.cn/wjsm";
		String url = "http://www.81.cn/wjsm/index.htm";
		// 获取新闻主页
		Document indexDoc = Jsoup.parse(new URL(url), 30000);
		// 获取主页新闻列表元素
		Elements xinWenLieBiaoEle = indexDoc.select(".list_ns_f14_dot a");
		for (int i = 0; i < xinWenLieBiaoEle.size(); i++) {
			Element xinwenEle = xinWenLieBiaoEle.get(i);
			// 新闻标题写入数据库
			System.out.println(xinwenEle.text());
			// 新闻内容url写入数据库
			System.out.println(xinwenEle.attr("href"));
			// 获取新闻内容页面
			Document contentDoc = Jsoup.parse(new URL(root + "/" + xinwenEle.attr("href")), 30000);// 利用Jsoup实现document树
			// 获取新闻内容元素
			Element contentEle = contentDoc.getElementById("article-content");
			// 新闻内容写入数据库 。新闻内容字符太多，就不打印在控制台了。
			// System.out.println(contentEle.html());
		}
	}

	public static void a4() {
		String orderId = "1234";
		System.out.println(orderId.substring(orderId.length() - 5));
	}

	public static void a1() {
		List<String> detailPics = new ArrayList<String>();
		String cover = null;
		try {
			Document mydoc = Jsoup.parse(new URL("https://item.taobao.com/item.htm?id=559054357139"), 30000);
			List<String> pics = mydoc.getElementById("J_UlThumb").getElementsByTag("img").eachAttr("data-src");
			if (!pics.isEmpty()) {
				cover = pics.get(0).replaceAll("50x50", "400x400");
				pics.remove(0);
				if (pics.size() > 1) {
					for (int i = 0; i < pics.size(); i++) {
						detailPics.add(pics.get(i).replaceAll("50x50", "400x400"));
					}

				}
			}
			System.out.println(cover);
			System.out.println(detailPics);
		} catch (Exception e) {

		}
	}

	public static void a2() throws IOException, SAXException {
		WebConversation wc = new WebConversation();
		WebResponse wr = wc
				.getResponse("http://passion.njshangka.com/rrightway/p/m/safetysetent/getbankinfo?token=400266258702");
		System.out.println(wr.getText());
	}

	public static void a3() {
		Pattern pattern = Pattern.compile("[^0-9a-zA-Z!@#$%^&*]");
		Pattern pattern1 = Pattern.compile("(?=[0-9])(?=[a-z])");
		Pattern pattern2 = Pattern.compile("");

		System.out.println(pattern1.matcher("a1").find());
	}
}
