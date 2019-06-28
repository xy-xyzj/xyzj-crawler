package com.xyzj.crawler.spidertask.zlr.dorule;

import avro.shaded.com.google.common.collect.Lists;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
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
    public void runSpider(Param param) {
        try {
            //第一步 拿到源码
            String htmlSource = HttpResponseUtil.getHtmlSource(param);
            if (htmlSource == null)return;

            //第二步 匹配出内容 并存储到数据库
            getGoods(param, htmlSource);
        }finally {

            //第三步 如果有减1个操作
            if (param.getCountDownLatch() !=null){
                param.getCountDownLatch().countDown();
                log.info("还有{}个任务等待中{}", param.getCountDownLatch().getCount());
            }
        }
    }


    /**
     *
     *========================================
     * @description: 这个方法得 自己研究怎么写
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/6/28 11:59
     *
     *========================================
     */
    private void getGoods(Param param,  String htmlSource) {
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
            runSpider(newParam);
        }
    }

}
