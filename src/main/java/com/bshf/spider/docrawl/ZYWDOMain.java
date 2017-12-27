package com.bshf.spider.docrawl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bshf.spider.dorule.SpiderRuleZYW;
import com.bshf.util.HttpUtil;
import com.bshf.util.SpiderTaskMultThread;

public class ZYWDOMain {
	public static void main(String[] args) {
		// 实例化爬取单条数据的工具类
		SpiderRuleZYW spiderRule = new SpiderRuleZYW();
		// 存放目标连接
		List<String> srcUrls = new ArrayList<String>();
		String pageUrl = null;
		for (int i = 1; i < 43; i++) {
			pageUrl = "http://www.zhongyoo.com/name/page_" + i + ".html";
			List<String> urls = HttpUtil.getUrlsByPage(pageUrl);

			for (String url : urls) {
				if (url.contains("http://www.zhongyoo.com/name/")
						&& url.length() > "http://www.zhongyoo.com/name/".length()
						&& !url.contains("http://www.zhongyoo.com/name/page"))
					srcUrls.add(url);
			}
		}
		Set<String> srcUrlss = new HashSet<String>();
		for (String src : srcUrls) {
			srcUrlss.add(src);
			System.out.println(src);
		}
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);
		for (final String srcUrl : srcUrlss) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();

	}

}
