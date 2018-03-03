package com.xyzj.crawler.framework.threads;

import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.entity.GoodsPO;

public class SpiderTaskMultThread implements Runnable {
    private String srcUrl;
    private GoodsPO goodsPO;
    private ISpiderRule spiderRule;
    private Integer num;
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

        if (goodsPO != null) {
            try {
                spiderRule.runSpider(goodsPO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (srcUrl != null) {
            if (num != null) {
                spiderRule.runSpider(srcUrl, num);
                System.out.println(111);
            } else if (loginCookie != null) {
                spiderRule.runSpider(srcUrl, loginCookie);
                System.out.println(222);
            } else {
                try {
                    spiderRule.runSpider(srcUrl);
                    System.out.println(333);
                } catch (Exception e) {
                    System.out.println("异常了");
                }

            }
        }

    }
}

