package com.xyzj.crawler.framework.factory;

import com.xyzj.crawler.framework.impl.DefaultSpiderRule;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;

/**
 * @author lyy
 * @since 2018-10-27 13:57
 */
public class SpiderRuleFactory {


    //提供默认实现
    private ISpiderRule spiderRule = new DefaultSpiderRule();


    //无参构造
    public SpiderRuleFactory() {

    }

    //有参数构造
    public SpiderRuleFactory(ISpiderRule spiderRule) {
        this.spiderRule = spiderRule;
    }


    //取得实例
    public ISpiderRule getInstance() {
        return spiderRule;
    }



}
