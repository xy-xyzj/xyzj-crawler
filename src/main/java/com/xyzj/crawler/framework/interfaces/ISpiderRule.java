package com.xyzj.crawler.framework.interfaces;

import java.util.Map;


/**
 * 爬虫规则 接口
 *
 * @author liuyangyang
 */
public interface ISpiderRule {

    void runSpider(Map<String, Object> params);
}
