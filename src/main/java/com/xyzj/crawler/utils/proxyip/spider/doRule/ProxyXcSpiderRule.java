package com.xyzj.crawler.utils.proxyip.spider.doRule;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.handler.SpiderRuleHandler;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author lyy
 * @since 2018-10-27 13:08
 */
@Slf4j
public  class ProxyXcSpiderRule extends SpiderRuleAbstract {
    @Override
    public void runSpider(Param param, ISpiderRule spiderRule) {
        SpiderRuleHandler spiderRuleHandler = new SpiderRuleHandler();
        spiderRuleHandler.handler(param, spiderRule);
    }

    @Override
    public void handlerGoods(Param param, String htmlSource) {
        Document document = Jsoup.parse(htmlSource);
        Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");
        for (int i = 1; i < trs.size(); i++) {
            String newIp = trs.get(i).select("td").get(1).text();
            String newPort = trs.get(i).select("td").get(2).text();
            String newType = trs.get(i).select("td").get(5).text();
            String newSpeed = trs.get(i).select("td").get(6).select("div[class=bar]").attr("title");
            //取得单个ip
            IPMessage ipMessage = new IPMessage();
            ipMessage.setIp(newIp);
            ipMessage.setPort(newPort);
            ipMessage.setType(newType);
            ipMessage.setSpeed(newSpeed);

            ISpiderRule spiderRule = new SpiderRuleFactory(new ProxyFilterSpiderRule()).getInstance();
            Param newParam = new Param();
            newParam.setProxyIp(newIp);
            newParam.setProxyPort(newPort);
            newParam.setWebUrl(String.valueOf(param.getExtParamMap().get("targetUrl")));
            newParam.getExtParamMap().put("ipMessage",ipMessage);
            spiderRule.runSpider(newParam,spiderRule);
        }
    }
}

