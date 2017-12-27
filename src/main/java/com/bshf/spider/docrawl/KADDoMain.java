package com.bshf.spider.docrawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.bshf.spider.dorule.SpiderRuleKAD;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.SpiderTaskMultThread;
import com.bshf.util.parser.JsoupHtmlParser;

public class KADDoMain {
	// http://www.360kad.com/Category_45/Index.aspx
	// http://www.360kad.com/Category_46/Index.aspx?page=16
	public static void main(String[] args) {
		// 实例化爬取单条数据的工具类
		SpiderRuleKAD spiderRule = new SpiderRuleKAD();
		// 存放目标连接
		List<String> srcUrls = new ArrayList<>();
		List<String> productUrls = new ArrayList<>();
		String pageUrl = null;
		for (int i = 50; i < 410; i++) {
			pageUrl = "http://www.360kad.com/Category_45/Index.aspx?page=" + i;
			productUrls = getProductUrl(pageUrl);
			for (String string : productUrls) {
				srcUrls.add("http://www.360kad.com/product/" + string + ".shtml");
				// System.out.println("http://www.360kad.com/product/" + string
				// + ".shtml");
			}
			System.out.println(i);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(20);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();
	}

	// 取得商品单个的链接
	private static List<String> getProductUrl(String classUrl) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(classUrl);
		getMethod.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		String htmlSource = null;
		try {
			httpClient.executeMethod(getMethod);
			htmlSource = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(htmlSource);
		// 取得class的链接
		List<String> productUrls = new ArrayList<>();

		List<String> productList = new LinkedList<String>();
		productList.add("#YproductList  a");
		List<String> productTag = JsoupHtmlParser.getNodeContentBySelector(htmlSource, productList,
				DataFormatStatus.TagAllContent, true);
		int beginindex = 0;
		int endindex = 0;
		for (String string : productTag) {
			System.out.println(string);
			beginindex = string.indexOf("href=/product");
			endindex = string.indexOf(".shtml");
			String substring = string.substring(beginindex + 19, endindex);
			productUrls.add(substring);
		}
		return productUrls;
	}

}
