package com.bshf.spider.dorule.cj;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 10:54
 *
 *
 * 药智网
 * https://db.yaozh.com/
 * 根据关键字 取得链接 并取得其内容
 *
 */
public class SpiderRuleYaozhDetail extends SpiderRuleAbstract {
    @Override
    public void runSpider(GoodsPO goodsPO) {
        //页面源码

        //  第一步 取得源码
        String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), goodsPO.getType(), goodsPO.getProvide());

        //System.out.println(htmlSource);

        //  .manual .title 取出药品名称
        List<String> targetListPattern = new LinkedList<String>();
        targetListPattern.add(".manual .title");
        List<String> targetList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetListPattern, DataFormatStatus.CleanTxt, true);
        //  .manual .content 取出药品详情
        List<String> targetDetailListPattern = new LinkedList<String>();
        targetDetailListPattern.add(".manual .content");
        List<String> targetDetailList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetDetailListPattern, DataFormatStatus.CleanTxt, true);

        //  输出查看
        //System.out.println(targetList.toString());
        //System.out.println(targetDetailList.toString().replace("【","|【").replace("[","|["));

        //  第三步 往数据库中存
        goodsPO.setName(targetList.toString());
        //  做字符替换 好在Excel表中分列
        goodsPO.setProvide(targetDetailList.toString().replace("【","|【").replace("[","|["));
        ServiceImpl goodsPOServiceImpl = new ServiceImpl();
        goodsPOServiceImpl.add("goods_yaozh_detail", goodsPO);


    }

    public static void main(String[] args) {
        SpiderRuleYaozhDetail spiderUtils = new SpiderRuleYaozhDetail();
        String srcUrl = " https://db.yaozh.com/instruct/149441.html";
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setWebUrl(srcUrl);
        goodsPO.setOrderNum("1");
        spiderUtils.runSpider(goodsPO);
    }
}
