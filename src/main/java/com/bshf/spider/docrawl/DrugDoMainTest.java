package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleDrug;
import com.bshf.spider.dorule.SpiderRuleDrugTest;
import com.bshf.util.SpiderTaskMultThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrugDoMainTest {
	public static void main(String[] args) {
		// 实例化爬取单条数据的工具类
		SpiderRuleDrugTest spiderRule = new SpiderRuleDrugTest();
		// 存放目标连接
		List<String> srcUrls = new ArrayList<>();
		for (int i = 1; i <1000000; i++) {
			String currentPage = "https://somd5.com/tijiao.php?hash=" + i ;
			srcUrls.add(currentPage);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(100);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();

	}

}
