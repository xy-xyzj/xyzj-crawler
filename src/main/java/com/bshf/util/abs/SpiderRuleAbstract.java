package com.bshf.util.abs;

import com.bshf.util.interfaces.ISpiderRule;
import com.bshf.util.entity.GoodsPO;

import java.io.UnsupportedEncodingException;

public class SpiderRuleAbstract implements ISpiderRule {

	@Override
	public void runSpider(String srcUrl) throws UnsupportedEncodingException {

	}

	@Override
	public void runSpider(String srcUrl, String loginCookie) {

	}

	@Override
	public void runSpider(String srcUrl, Integer num) {

	}

	@Override
	public void runSpider(GoodsPO goodsPO) throws UnsupportedEncodingException {

	}
}
