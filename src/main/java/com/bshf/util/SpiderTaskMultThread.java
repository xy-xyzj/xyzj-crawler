package com.bshf.util;

import com.bshf.spider.dorule.intfaces.ISpiderRule;
import com.bshf.spider.entity.GoodsPO;

import java.io.UnsupportedEncodingException;

public class SpiderTaskMultThread implements Runnable {
	private String srcUrl;
	private GoodsPO goodsPO;
	private ISpiderRule spiderRule;
	private String loginCookie;

	public SpiderTaskMultThread(String srcUrl, ISpiderRule spiderRule) {
		super();
		this.srcUrl = srcUrl;
		this.spiderRule = spiderRule;
	}

	public SpiderTaskMultThread(GoodsPO goodsPO, ISpiderRule spiderRule) {
		super();
		this.goodsPO = goodsPO;
		this.spiderRule = spiderRule;
	}

	public SpiderTaskMultThread(String srcUrl, String loginCookie, ISpiderRule spiderRule) {
		super();
		this.srcUrl = srcUrl;
		this.loginCookie = loginCookie;
		this.spiderRule = spiderRule;
	}

	@Override
	public void run() {
		try {
			spiderRule.runSpider(goodsPO);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		spiderRule.runSpider(srcUrl, loginCookie);

	}
}