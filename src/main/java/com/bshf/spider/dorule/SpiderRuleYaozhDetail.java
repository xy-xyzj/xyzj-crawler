package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 10:54
 */
public class SpiderRuleYaozhDetail extends SpiderRuleAbstract {
    @Override
    public void runSpider(GoodsPO goodsPO) {
        //页面源码
        StringBuffer htmlSourceBuffer = new StringBuffer();
        try {
            //HtmlUnit请求web页面
            WebClient wc = new WebClient();
            //启用JS解释器，默认为true
            wc.getOptions().setJavaScriptEnabled(true);
            //禁用css支持
            wc.getOptions().setCssEnabled(false);
            //js运行错误时，是否抛出异常
            wc.getOptions().setThrowExceptionOnScriptError(false);
            //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            wc.getOptions().setTimeout(10000);
            HtmlPage page = wc.getPage(goodsPO.getWebUrl());
            //以xml的形式获取响应文本
            htmlSourceBuffer.append(page.asXml());
            wc.close();
        } catch (Exception e) {
            //异常不做处理
        }
        //  第一步 取得源码
        String htmlSource = htmlSourceBuffer.toString();
        System.out.println(htmlSource);

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
        goodsPOServiceImpl.add("goods", goodsPO);


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
