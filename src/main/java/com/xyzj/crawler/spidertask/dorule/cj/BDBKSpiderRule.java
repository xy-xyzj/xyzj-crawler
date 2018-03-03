package com.xyzj.crawler.spidertask.dorule.cj;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.gethtmlstring.HttpUtil;

import java.util.regex.Pattern;

/**
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 10:54
 *
 * 百度百科
 * https://baike.baidu.com/item/阿魏消瘤汤
 *
 *
 */
public class BDBKSpiderRule extends SpiderRuleAbstract {
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
        System.out.println(htmlSource);
        ////第二步 正则匹配出内容
        //Matcher matcherString = patternTarget.matcher(htmlSource);
        //String provide = "未创建";
        //if(matcherString.find()){
        //    List<String> nameList = new LinkedList<String>();
        //    nameList.add("a"); // 商品名称
        //    List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(matcherString.group(), nameList, DataFormatStatus.CleanTxt,
        //            true);
        //    provide = nameTxt.get(0).toString();
        //}
        //
        //
        ////设置提供方
        //goodsPO.setProvide(provide);
        ////第三步 往数据库中存
        //ServiceImpl goodsPOServiceImpl = new ServiceImpl();
        //goodsPOServiceImpl.add("goods", goodsPO);

    }

    public static void main(String[] args) {
        BDBKSpiderRule spiderUtils = new BDBKSpiderRule();
        String srcUrl = "https://baike.baidu.com/item/阿魏消瘤汤";
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setType("汤丸剂10000");
        goodsPO.setName("阿魏消瘤汤");
        goodsPO.setWebUrl("https://db.yaozh.com/instruct?name=%E5%AE%A3%E8%82%BA%E6%AD%A2%E5%97%BD%E5%90%88%E5%89%82");
        spiderUtils.runSpider(goodsPO);
    }
}
