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
    String baseUrl = " https://db.yaozh.com";

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
        //第一步 取得源码
        String htmlSource = htmlSourceBuffer.toString();
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
                    //第三步 往数据库中存
                    ServiceImpl goodsPOServiceImpl = new ServiceImpl();
                    goodsPOServiceImpl.add("goods", savePO);
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
