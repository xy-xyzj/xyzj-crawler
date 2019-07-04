package com.xyzj.crawler.utils.proxyip.spider.docrawler;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.proxyip.spider.doRule.ProxyXcSpiderRule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyXcDoMain {


    public static void main(String[] args) throws Exception {
        log.info("开始采集有效代理");
        String baseUrl = "https://www.xicidaili.com/nn/";

        
        for (int i = 0; i <100; i++) {
            Param param = new Param();
            param.setWebUrl(baseUrl+i);
            param.getExtParamMap().put("targetUrl", "https://www.baidu.com");
            ISpiderRule spiderRule = new SpiderRuleFactory(new ProxyXcSpiderRule()).getInstance();
            spiderRule.runSpider(param,spiderRule);
        }

    }

}
