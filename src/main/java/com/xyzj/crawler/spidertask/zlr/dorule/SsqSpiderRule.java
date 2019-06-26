package com.xyzj.crawler.spidertask.zlr.dorule;

import avro.shaded.com.google.common.collect.Maps;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SsqSpiderRule extends SpiderRuleAbstract {

    @Override
    public void runSpider(Map<String, Object> params) {


        //第一步 取得源码
        Map<String, String> headerInfos = new HashMap<>();
        headerInfos.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        String htmlSource = HttpResponseUtil.getHtml(String.valueOf(params.get("webUrl")), "gb2312", headerInfos);
        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
            return;
        }
        log.info(htmlSource);

        //第二步
        //提取url
        String numberRegexString = "<a href='(.*?).html'>";
        String nameRegexString = ".html'>(.*?)<br/>";
        List<String> numUtil = RegexUtil.getSubUtil(htmlSource, numberRegexString);
        List<String> nameUtil = RegexUtil.getSubUtil(htmlSource, nameRegexString);
        System.out.println("subUtil========"+numUtil);
        System.out.println("nameUtil========"+nameUtil);
        String qzUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/";

        for (int i = 0; i < numUtil.size(); i++) {
            //设置提供方
            Goods goods = new Goods();
            goods.setName(nameUtil.get(i));
            goods.setWebUrl(qzUrl+numUtil.get(i)+".html");
            goods.setOrderNum(numUtil.get(i));

            //第三步 往数据库中存
            SaveToMysql saveToMysql = new SaveToMysql();
            saveToMysql.saveToMasql("goods", goods);
        }

    }

    public static void main(String[] args) {
        SsqSpiderRule spiderUtils = new SsqSpiderRule();
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("webUrl", "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html");
        spiderUtils.runSpider(param);
    }
}
