package com.xyzj.crawler.utils.proxyip.spider.docrawler;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.utils.proxyip.spider.doRule.ProxyXcSpiderRule;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ProxyXcDoMain {


    public static void main(String[] args) throws Exception {
        log.info("开始采集有效代理");
        String baseUrl = "https://www.xicidaili.com/nn/";
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i <5; i++) {
            Param param = new Param();
            param.setWebUrl(baseUrl+i);
            param.getExtParamMap().put("targetUrl", "https://www.baidu.com");
            ISpiderRule spiderRule = new SpiderRuleFactory(new ProxyXcSpiderRule()).getInstance();
            //spiderRule 参数
            SpiderRunnable spiderRunnable = new SpiderRunnable(spiderRule, param);
            executorService.execute(spiderRunnable);
        }
        //等到任务执行完毕，关闭线程池。
        executorService.shutdown();
    }

}
