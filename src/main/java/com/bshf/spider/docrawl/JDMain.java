package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleJD;
import com.bshf.util.SpiderTaskMultThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JDMain {

	public static void main(String[] args) {
		//京东单个分页数据爬取
		SpiderRuleJD spiderRule = new SpiderRuleJD();

		// 存放目标连接
		List<String> srcUrls = new ArrayList<>();
		for (int i = 1; i < 204; i++) {
			String pageUrl = "https://list.jd.com/list.html?cat=9192,12632,12633&page="+i+"&sort=sort_totalsales15_desc&trans=1&JL=6_0_0#J_main";
			srcUrls.add(pageUrl);
		}

		//创建线程池 爬取数据
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();
	}
}
