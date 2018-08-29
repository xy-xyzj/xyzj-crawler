package com.xyzj.crawler.spidertask.dorule.bdbk;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * 百度百科
 * https://baike.baidu.com/tashuo/browse/content?id=4a78326fd36ff54f2e1086af
 *
 *
 */
@Slf4j
public class BdbkSpiderRule extends SpiderRuleAbstract {

    @Override
    public void runSpider(GoodsPO goodsPO) {


        //第一步 取得源码

        Map<String, String> headerInfos = new HashMap<>();
        headerInfos.put("Host", "baike.baidu.com");
        headerInfos.put("Referer", "https://baike.abc.com/");
        headerInfos.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");

        String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(),"utf-8",headerInfos);

        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
            return;
        }

        log.info(htmlSource);
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
        BdbkSpiderRule spiderUtils = new BdbkSpiderRule();
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setType("汤丸剂10000");
        goodsPO.setName("阿魏消瘤汤");
        goodsPO.setWebUrl("https://baike.baidu.com/tashuo/browse/content?id=4a78326fd36ff54f2e1086af");
        spiderUtils.runSpider(goodsPO);
    }
}
