package com.xyzj.crawler.spidertask.dorule.cj;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.framework.savetomysql.ServiceImpl;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import com.xyzj.crawler.utils.parsehtmlstring.DataFormatStatus;
import com.xyzj.crawler.utils.parsehtmlstring.JsoupHtmlParser;

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
public class YaozhDetailSpiderRule extends SpiderRuleAbstract {
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
        YaozhDetailSpiderRule spiderUtils = new YaozhDetailSpiderRule();
        String srcUrl = " https://db.yaozh.com/instruct/149441.html";
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setWebUrl(srcUrl);
        goodsPO.setOrderNum("1");
        spiderUtils.runSpider(goodsPO);
    }
}
