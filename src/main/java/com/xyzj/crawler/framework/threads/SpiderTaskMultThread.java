package com.xyzj.crawler.framework.threads;

import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.entity.GoodsPO;
import lombok.extern.slf4j.Slf4j;


/**
 * 多线程任务
 *
 */
@Slf4j
public class SpiderTaskMultThread implements Runnable {

    private String srcUrl;

    //
    private Integer num;

    private GoodsPO goodsPO;
    private ISpiderRule spiderRule;

    //模拟登陆
    private String loginCookie;


    public SpiderTaskMultThread(String srcUrl, ISpiderRule spiderRule, Integer num) {
        super();
        this.srcUrl = srcUrl;
        this.num = num;
        this.spiderRule = spiderRule;
    }



    public SpiderTaskMultThread(String srcUrl, ISpiderRule spiderRule) {
        super();
        this.srcUrl = srcUrl;
        this.spiderRule = spiderRule;
    }

    public SpiderTaskMultThread(GoodsPO goodsPO, ISpiderRule spiderRule) {
        super();
        this.goodsPO = goodsPO;
        this.spiderRule = spiderRule;
    }

    public SpiderTaskMultThread(String srcUrl, String loginCookie, ISpiderRule spiderRule) {
        super();
        this.srcUrl = srcUrl;
        this.loginCookie = loginCookie;
        this.spiderRule = spiderRule;
    }

    @Override
    public void run() {
        //判断goodsPO不为空
        if (goodsPO != null) {
            try {
                spiderRule.runSpider(goodsPO);
            } catch (Exception e) {
                log.error("Exception:{}",e);
            }
        }
        if (srcUrl != null) {
            try {
                if (num != null) {
                    spiderRule.runSpider(srcUrl, num);
                } else if (loginCookie != null) {
                    spiderRule.runSpider(srcUrl, loginCookie);
                } else {
                    spiderRule.runSpider(srcUrl);
                }
            } catch (Exception e) {
                log.error("Exception:{}",e);
            }
        }

    }
}

