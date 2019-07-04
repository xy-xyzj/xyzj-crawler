package com.xyzj.crawler.spidertask.example.dorule;

import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;

/**
 * 单页面抓取
 *
 * @author lyy
 * @since 2018-10-27 14:22
 */
public class DoRule58 {

    public static void main(String[] args) {

        //工厂取得默认实例
        ISpiderRule spiderRule = new SpiderRuleFactory().getInstance();
        //封装参数
        Param param = new Param();
        param.setWebUrl("https://cq.58.com/shouji/?PGTID=0d100000-0002-5d3a-b0c9-e83a870d03be&ClickID=3");

        //走你
        spiderRule.runSpider(param,spiderRule);



    }
}
