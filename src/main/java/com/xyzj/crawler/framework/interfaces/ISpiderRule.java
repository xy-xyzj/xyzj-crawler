package com.xyzj.crawler.framework.interfaces;

import com.xyzj.crawler.framework.entity.GoodsPO;


/**
 * 爬虫规则 接口
 *
 * @author liuyangyang
 * */
public interface ISpiderRule {

	 void runSpider(String srcUrl) ;

	 void runSpider(String srcUrl, String loginCookie);


	 void runSpider(String srcUrl, Integer num);

	 void runSpider(GoodsPO goodsPO);
}
