package com.xyzj.crawler.utils.proxyip.spider.doRule;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.handler.SpiderRuleHandler;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.proxyip.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lyy
 * @since 2018-10-27 13:08
 */
@Slf4j
public  class ProxyFilterSpiderRule extends SpiderRuleAbstract {

    @Override
    public void runSpider(Param param, ISpiderRule spiderRule) {
        SpiderRuleHandler spiderRuleHandler = new SpiderRuleHandler();
        spiderRuleHandler.handler(param, spiderRule);
    }

    @Override
    public void handlerGoods(Param param, String htmlSource) {
        IPMessage ipMessage = (IPMessage) param.getExtParamMap().get("ipMessage");
        String ipType = ipMessage.getType();
        String ipSpeed = ipMessage.getSpeed();
        ipSpeed = ipSpeed.substring(0, ipSpeed.indexOf('秒'));
        double speed = Double.parseDouble(ipSpeed);
        if (ipType.equals("HTTPS") && speed <= 2.0) {
            RedisUtil.setOneIp(ipMessage);
        }

    }
}

