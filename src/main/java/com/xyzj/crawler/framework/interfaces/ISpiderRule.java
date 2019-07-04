package com.xyzj.crawler.framework.interfaces;

import com.xyzj.crawler.framework.entity.Param;


/**
 * 爬虫规则 接口
 *
 * @author liuyangyang
 */
public interface ISpiderRule {

    void runSpider(Param param,ISpiderRule spiderRule);

    void handlerGoods(Param param, String htmlSource);

}
