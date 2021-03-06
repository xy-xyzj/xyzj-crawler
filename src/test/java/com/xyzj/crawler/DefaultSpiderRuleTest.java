package com.xyzj.crawler;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import org.junit.Test;

/**
 * @author lyy
 * @since 2018-10-27 14:06
 */

public class DefaultSpiderRuleTest {

    @Test
    public  void runSpider(){
        Param param = new Param();
        param.setWebUrl("https://www.baidu.com");
        ISpiderRule spiderRule = new SpiderRuleFactory().getInstance();
        spiderRule.runSpider(param,spiderRule);
    }
}
