package com.bshf.spider.docrawl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bshf.spider.dorule.SpiderRulepbw;
import com.bshf.util.SpiderTaskMultThread;

public class PBWDoMain {
	public static void main(String[] args) {
		// 实例化爬取单条数据的工具类
		SpiderRulepbw spiderRule = new SpiderRulepbw();
		// 存放目标连接
		List<String> srcUrls = new ArrayList<>();
		for (int i = 669; i < 5349; i++) {
			String currentPage = "http://db.ouryao.com/yd2015/view.php?v=txt&id=" + i;
			srcUrls.add(currentPage);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();
	}

}
