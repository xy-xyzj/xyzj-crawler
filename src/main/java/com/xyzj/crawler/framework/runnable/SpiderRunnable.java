package com.xyzj.crawler.framework.runnable;

import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


/**
 * 多线程任务定义
 * @since liuyangyang
 */
@Slf4j
public class SpiderRunnable implements Runnable {

    //封装参数
    private Map<String,Object> params;

    //规则
    private ISpiderRule spiderRule;


    //构造方法
    public SpiderRunnable(ISpiderRule spiderRule,Map<String,Object> params) {
        super();
        this.spiderRule = spiderRule;
        this.params = params;
    }

    @Override
    public void run() {
        spiderRule.runSpider(params);
    }
}

