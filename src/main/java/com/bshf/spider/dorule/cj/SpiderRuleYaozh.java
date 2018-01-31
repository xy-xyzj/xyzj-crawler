package com.bshf.spider.dorule.cj;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.IPModel.IPMessage;
import com.bshf.util.database.MyRedis;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.parser.JsoupHtmlParser;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 10:54
 */
public class SpiderRuleYaozh extends SpiderRuleAbstract {
    //定义正则表达式
    //查看全文链接匹配
    private static Pattern patternTargetCKQW = Pattern.compile("<a class=\"cl-blue\" href=\"(.*)\" target=\"_blank\">\\s*查看全文\\s*</a>");
    private static String patternTargetString = "\" target=\"_blank\">\\s*查看全文\\s*</a>";
    //网站地址
    String baseUrl = "https://db.yaozh.com";

    @Override
    public void runSpider(GoodsPO goodsPO) {
        //页面源码

        // 设置代理IP
        MyRedis redis = new MyRedis();
        //从redis数据库中随机拿出一个IP
        IPMessage ipMessage = redis.getIPByList();
        redis.close();
        String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());

        //第一步 取得源码

        //a标签取出链接
        List<String> aListPattern = new LinkedList<String>();
        aListPattern.add("table a.cl-blue");
        List<String> aTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, aListPattern, DataFormatStatus.TagAllContent, true);
        if (aTxt !=null) {
            for (int i = 0; i < aTxt.size(); i++) {
                if (aTxt.get(i).contains("查看全文")) {
                    String url = aTxt.get(i).replace("<a class=\"cl-blue\" href=\"", "").replaceFirst(patternTargetString, "");
                    GoodsPO savePO = new GoodsPO();
                    savePO.setWebUrl(baseUrl + url);
                    savePO.setName(goodsPO.getName());
                    savePO.setOrderNum(goodsPO.getOrderNum());
                    savePO.setType(ipMessage.getIPAddress());
                    savePO.setProvide(ipMessage.getIPPort());
                    //第三步 调用detail
                    SpiderRuleYaozhDetail spiderRuleYaozhDetail = new SpiderRuleYaozhDetail();
                    spiderRuleYaozhDetail.runSpider(savePO);
                }
            }
        }
    }

    public static void main(String[] args) {
        SpiderRuleYaozh spiderUtils = new SpiderRuleYaozh();
        String target = "阿胶当归合剂";
        String srcUrl = "https://db.yaozh.com/instruct?name=%E5%AE%A3%E8%82%BA%E6%AD%A2%E5%97%BD%E5%90%88%E5%89%82";
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setName(target);
        goodsPO.setWebUrl(srcUrl);
        spiderUtils.runSpider(goodsPO);


    }
}
