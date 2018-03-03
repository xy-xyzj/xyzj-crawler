package com.xyzj.crawler.framework.abstracts;

import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.entity.GoodsPO;

/**
 *  抽象类
 *
 * */
public class SpiderRuleAbstract implements ISpiderRule {

	@Override
	public void runSpider(String srcUrl){ }

	@Override
	public void runSpider(String srcUrl, String loginCookie){}

	@Override
	public void runSpider(String srcUrl, Integer num) {}

	@Override
	public void runSpider(GoodsPO goodsPO){}
}
