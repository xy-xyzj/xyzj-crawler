package com.bshf.spider.dorule.abs;

import com.bshf.spider.dorule.intfaces.ISpiderRule;
import com.bshf.spider.entity.GoodsPO;

import java.io.UnsupportedEncodingException;

public class SpiderRuleAbstract implements ISpiderRule {

	@Override
	public void runSpider(String srcUrl) throws UnsupportedEncodingException {

	}

	@Override
	public void runSpider(String srcUrl, String loginCookie) {

	}

	@Override
	public void runSpider(GoodsPO goodsPO) throws UnsupportedEncodingException {

	}
}
