package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 10:54
 */
public class SpiderRuleBDBK extends SpiderRuleAbstract {
    //定义正则表达式
    private static Pattern patternTarget = Pattern.compile("本词条由<a href=\"(.*?)\">(.*?)</a>");

    @Override
    public void runSpider(GoodsPO goodsPO) {
        //第一步 取得源码
        String htmlSource = HttpUtil.getPageCode(goodsPO.getWebUrl(), "utf-8");
        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
            return;
        }

        //第二步 正则匹配出内容
        Matcher matcherString = patternTarget.matcher(htmlSource);
        String provide = "未创建";
        if(matcherString.find()){
            List<String> nameList = new LinkedList<String>();
            nameList.add("a"); // 商品名称
            List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(matcherString.group(), nameList, DataFormatStatus.CleanTxt,
                    true);
            provide = nameTxt.get(0).toString();
        }


        //设置提供方
        goodsPO.setProvide(provide);
        //第三步 往数据库中存
        ServiceImpl goodsPOServiceImpl = new ServiceImpl();
        goodsPOServiceImpl.add("goods", goodsPO);

    }

    public static void main(String[] args) {
        SpiderRuleBDBK spiderUtils = new SpiderRuleBDBK();
        String srcUrl = "https://baike.baidu.com/item/阿魏消瘤汤";
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setType("汤丸剂10000");
        goodsPO.setName("阿魏消瘤汤");
        goodsPO.setWebUrl("https://baike.baidu.com/item/阿魏消瘤汤");
        spiderUtils.runSpider(goodsPO);
    }
}
