package com.bshf.util.httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {

	private static final String LINE = "\n";

	private static final String HTTP = "http://";

	/**
	 * 获得网址的源码
	 * 
	 * @param str
	 *            传入的需要获得的网页地址
	 * @return 网页的源码
	 */
	public static String getPageCode(String str, String encoding) {
		try {
			URL u = new URL(str);
			InputStream is = u.openStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, encoding));
			StringBuffer sb = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str);
				sb.append(LINE);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return "连接失败";
		return "null";
	}

	/**
	 * 从网址里面抽取链接
	 * 
	 * @return 链接的集合
	 */
	public static List<String> getUrlsByPage(String str) {
		List<String> urls = new ArrayList<String>();
		try {
			URL url = new URL(str);
			int end = 0;
			String host = url.getHost();
			Document doc = Jsoup.parse(url, 30000);
			Elements links = doc.select("a");
			String href = null;
			for (Element link : links) {
				href = link.attr("href");
				if (href.startsWith(HTTP)) {
					urls.add(href);
				} else if (href.startsWith("/")) {
					urls.add(HTTP + host + href);
				} else {
					if (end > 0) {
						urls.add(str + href);
					} else {
						urls.add(str + href);
					}

				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return urls;
	}
}
