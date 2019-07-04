package com.xyzj.crawler.spidertask.zlr.dorule;

import avro.shaded.com.google.common.collect.Lists;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.handler.SpiderRuleHandler;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.parsehtmlstring.JsoupHtmlParser;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;


@Slf4j
public class SsqDetailSpiderRule extends SpiderRuleAbstract {

    @Override
    public void runSpider(Param param, ISpiderRule spiderRule) {
        SpiderRuleHandler spiderRuleHandler = new SpiderRuleHandler();
        spiderRuleHandler.handler(param, spiderRule);
    }

    @Override
    public void handlerGoods(Param param, String htmlSource) {
        List<String> citytr = JsoupHtmlParser.getNodeContentBySelector(htmlSource, Arrays.asList("tr.citytr > td"), false);
        List<String> countytr = JsoupHtmlParser.getNodeContentBySelector(htmlSource, Arrays.asList("tr.countytr > td"), false);
        List<String> towntr  = JsoupHtmlParser.getNodeContentBySelector(htmlSource, Arrays.asList("tr.towntr > td"), false);
        List<String> villagetr = JsoupHtmlParser.getNodeContentBySelector(htmlSource, Arrays.asList("tr.villagetr > td"), false);
        ArrayList<String> allList = Lists.newArrayList();
        if (citytr != null) {
            allList.addAll(citytr);
        }
        if (countytr != null) {
            allList.addAll(countytr);
        }
        if (towntr != null) {
            allList.addAll(towntr);
        }
        if (villagetr != null) {
            allList.addAll(villagetr);
        }
        log.info("allList========"+allList);
        if (!CollectionUtils.isEmpty(allList)) {
            //判断是否包含城乡分类代码
            if (htmlSource.contains("<td width=80>城乡分类代码</td>")) {
                for (int i = 0; i < allList.size() ; i = i + 3) {
                    Goods goods = new Goods();
                    goods.setWebUrl(String.valueOf(param.getWebUrl()));
                    goods.setOrderNum(allList.get(i));
                    goods.setName(allList.get(i + 2));
                    //第三步 往数据库中存
                    SaveToMysql saveToMysql = new SaveToMysql();
                    saveToMysql.saveToMasql("goods", goods);
                }
            } else {
                for (int i = 0; i < allList.size() ; i = i + 2) {
                    Goods goods = new Goods();
                    goods.setWebUrl(String.valueOf(param.getWebUrl()));
                    goods.setOrderNum(allList.get(i));
                    goods.setName(allList.get(i + 1));
                    //第三步 往数据库中存
                    SaveToMysql saveToMysql = new SaveToMysql();
                    saveToMysql.saveToMasql("goods", goods);
                }
            }


        }

        //保存url
        String urlRegexString = "tr'><td><a href='(.*?).html'>";
        List<String> urlUtil = RegexUtil.getSubUtil(htmlSource, urlRegexString);
        log.info("urlUtil========"+urlUtil);

        //http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/14/01/140108.html
        //http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/14/01/08/140108001.html
        for (int i = 0; i < urlUtil.size(); i++) {
            //设置提供方
            String oldWebUrl = String.valueOf(param.getWebUrl());
            String newWebUrl = oldWebUrl.substring(0, oldWebUrl.lastIndexOf("/")+1);
            Param newParam = new Param();
            newParam.setWebUrl(newWebUrl+urlUtil.get(i)+".html");
            ISpiderRule spiderRule = new SpiderRuleFactory(new SsqDetailSpiderRule()).getInstance();
            runSpider(newParam,spiderRule);
        }
    }
}
