package com.xyzj.crawler.framework.runnable;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import lombok.extern.slf4j.Slf4j;


/**
 * 多线程任务定义
 *
 * @since liuyangyang
 */
@Slf4j
public class SpiderRunnable implements Runnable {

    //封装参数
    private Param param;

    //规则
    private ISpiderRule spiderRule;

    //构造方法
    public SpiderRunnable(ISpiderRule spiderRule, Param param) {
        super();
        this.spiderRule = spiderRule;
        this.param = param;
    }

    @Override
    public void run() {
        spiderRule.runSpider(param,spiderRule);

    }
}

