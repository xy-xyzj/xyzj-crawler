package com.xyzj.crawler.spidertask.zlr.docrawler;

import avro.shaded.com.google.common.collect.Lists;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.framework.runnable.SpiderRunnable;
import com.xyzj.crawler.spidertask.zlr.dorule.SsqDetailSpiderRule;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SsqDoMain {


    public static void main(String[] args) throws Exception {
        log.info("开始抓取区域数据");
        //第一步 取得源码
        Param param = new Param();
        String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
        param.setWebUrl(baseUrl);
        param.setCharset("gb2312");
        String htmlSource = HttpResponseUtil.getHtmlSource(param);
        if (htmlSource == null) return;

        //第二步 提取url
        String numberRegexString = "<a href='(.*?).html'>";
        String nameRegexString = ".html'>(.*?)<br/>";
        List<String> numUtil = RegexUtil.getSubUtil(htmlSource, numberRegexString);
        List<String> nameUtil = RegexUtil.getSubUtil(htmlSource, nameRegexString);

        ArrayList<Goods> goodsList = Lists.newArrayList();
        for (int i = 0; i < numUtil.size(); i++) {
            Goods goods = new Goods();
            goods.setName(nameUtil.get(i));
            goods.setWebUrl(baseUrl + numUtil.get(i) + ".html");
            goods.setOrderNum(numUtil.get(i));
            //第三步 往数据库中存
            SaveToMysql saveToMysql = new SaveToMysql();
            saveToMysql.saveToMasql("goods", goods);
            goodsList.add(goods);
        }

        //第四步 爬取明细记录
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (Goods goods : goodsList) {
            Param detailParam = new Param();
            detailParam.setWebUrl(goods.getWebUrl());
            detailParam.setCharset("gb2312");
            //抓取器
            SsqDetailSpiderRule ssqDetailSpiderRule = new SsqDetailSpiderRule();
            ISpiderRule spiderRule = new SpiderRuleFactory(ssqDetailSpiderRule).getInstance();
            //spiderRule 参数
            SpiderRunnable spiderRunnable = new SpiderRunnable(spiderRule, detailParam);
            executorService.execute(spiderRunnable);
        }
        //等到任务执行完毕，关闭线程池。
        executorService.shutdown();


    }

}
