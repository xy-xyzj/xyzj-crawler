package com.xyzj.crawler.spidertask.zlr.dorule;

import avro.shaded.com.google.common.collect.Lists;
import avro.shaded.com.google.common.collect.Maps;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
import com.xyzj.crawler.utils.parsehtmlstring.JsoupHtmlParser;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SsqDetailSpiderRule extends SpiderRuleAbstract {

    @Override
    public void runSpider(Map<String, Object> params) {
        System.out.println("url======"+params.get("webUrl"));
        //第一步 取得源码
        Map<String, String> headerInfos = new HashMap<>();
        headerInfos.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        String htmlSource = HttpResponseUtil.getHtml(String.valueOf(params.get("webUrl")), "gb2312", headerInfos);
        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {

            Goods unableGoods = new Goods();
            unableGoods.setWebUrl(String.valueOf(params.get("webUrl")));
            //第三步 往数据库中存
            SaveToMysql saveToMysql = new SaveToMysql();
            saveToMysql.saveToMasql("ungoods", unableGoods);
            return;
        }

        log.info(htmlSource);
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
        System.out.println("allList========"+allList);
        if (allList!=null) {
            //判断是否包含城乡分类代码
            if (htmlSource.contains("<td width=80>城乡分类代码</td>")) {
                for (int i = 0; i < allList.size() ; i = i + 3) {
                    Goods goods = new Goods();
                    goods.setWebUrl(String.valueOf(params.get("webUrl")));
                    goods.setOrderNum(allList.get(i));
                    goods.setName(allList.get(i + 2));
                    //第三步 往数据库中存
                    SaveToMysql saveToMysql = new SaveToMysql();
                    saveToMysql.saveToMasql("goods", goods);
                }
            } else {
                for (int i = 0; i < allList.size() ; i = i + 2) {
                    Goods goods = new Goods();
                    goods.setWebUrl(String.valueOf(params.get("webUrl")));
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
        System.out.println("urlUtil========"+urlUtil);

        //http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/14/01/140108.html
        //http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/14/01/08/140108001.html
        for (int i = 0; i < urlUtil.size(); i++) {
            //设置提供方
            String oldWebUrl = String.valueOf(params.get("webUrl"));
            String newWebUrl = oldWebUrl.substring(0, oldWebUrl.lastIndexOf("/")+1);
            HashMap<String, Object> param = Maps.newHashMap();
            param.put("webUrl", newWebUrl+urlUtil.get(i)+".html");
            runSpider(param);
        }


    }


    public static void main(String[] args) {
        SsqDetailSpiderRule spiderUtils = new SsqDetailSpiderRule();
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("webUrl", "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html");
        spiderUtils.runSpider(param);
    }
}
