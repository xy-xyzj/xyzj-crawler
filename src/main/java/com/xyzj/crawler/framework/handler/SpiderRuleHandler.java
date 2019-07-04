package com.xyzj.crawler.framework.handler;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lyy
 * @since 2018-10-27 13:08
 */
@Slf4j
public  class SpiderRuleHandler {
    public void handler(Param param, ISpiderRule spiderRule) {
        try {
            //第一步 拿到源码
            String htmlSource = HttpResponseUtil.getHtmlSource(param);
            if (htmlSource == null)return;
            //第二步 匹配出内容 并进行下一步处理
            spiderRule.handlerGoods(param, htmlSource);
        }finally {
            //第三步 如果有减1个操作
            if (param.getCountDownLatch() !=null){
                param.getCountDownLatch().countDown();
                log.info("还有{}个任务等待中{}", param.getCountDownLatch().getCount());
            }
        }
    }
}

