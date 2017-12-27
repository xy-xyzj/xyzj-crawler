package com.bshf.spider.docrawl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bshf.spider.dorule.SpiderRulepsms;
import com.bshf.util.LoginUtil;
import com.bshf.util.SpiderTaskMultThread;

public class SMSDoMain {
	public static void main(String[] args) {
		// 实例化爬取单条数据的工具类
		SpiderRulepsms spiderRule = new SpiderRulepsms();

		// 存放目标连接
		List<String> srcUrls = new ArrayList<String>();
		// 登陆 Url
		String loginUrl = "https://www.yaozh.com/login";
		String loginCookie = LoginUtil.login(loginUrl);

		for (int i = 47161; i < 50057; i++) {
			String currentPage = "https://db.yaozh.com/instruct/" + i + ".html";
			srcUrls.add(currentPage);
		}
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);
		try {

			for (final String srcUrl : srcUrls) {
				cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, loginCookie, spiderRule));
			}
		} catch (Exception e) {
		} finally {
			cachedThreadPool.shutdown();
		}

	}

}
