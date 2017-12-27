package com.bshf.spider.docrawl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bshf.spider.dorule.SpiderRuleDrug;
import com.bshf.util.SpiderTaskMultThread;

public class DrugDoMain {
	public static void main(String[] args) {
		// 实例化爬取单条数据的工具类
		SpiderRuleDrug spiderRule = new SpiderRuleDrug();
		// 存放目标连接
		List<String> srcUrls = new ArrayList<>();
		for (int i = 94138; i > 90586; i--) {
			String currentPage = "http://drugs.dxy.cn/drug/" + i + "/detail.htm";
			srcUrls.add(currentPage);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(1);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();

	}

}
