package com.xyzj.crawler.framework.interfaces;

import com.xyzj.crawler.framework.entity.GoodsPO;


/**
 * 爬虫规则 接口
 *
 * @author liuyangyang
 * */
public interface ISpiderRule {


	public void runSpider(String srcUrl) ;

	public void runSpider(String srcUrl, String loginCookie);


	public void runSpider(String srcUrl, Integer num);

	public void runSpider(GoodsPO goodsPO);
}
