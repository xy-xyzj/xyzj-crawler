package com.xyzj.crawler.framework.factory;

import com.xyzj.crawler.framework.abstracts.AbstractSpiderRule;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lyy
 * @since 2018-10-27 13:08
 */
@Slf4j
public class DefaultSpiderRule extends AbstractSpiderRule implements ISpiderRule{

    @Override
    public void runSpider(Map<String, Object> params) {
        //约定优于配置
        //params中取出webUrl
        String webUrl = "http://www.baidu.com";

        //params中取出headerInfos
        Map<String, String> headerInfos = new HashMap<>();
        headerInfos.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");

        //params中取出charset
        String charset = "utf-8";

        //params中取出pattern 匹配规则
        String regexPattern = "京公网安备(.*?)号";




        if (params.containsKey("webUrl")) {
            webUrl = (String) params.get("webUrl");
        }
        if (params.containsKey("webUrl")) {
            headerInfos = (Map<String, String>) params.get("headerInfos");
        }
        if (params.containsKey("charset")) {
            charset = (String) params.get("charset");
        }
        if (params.containsKey("regexPattern")) {
            regexPattern = (String) params.get("regexPattern");
        }


        String htmlSource = MyHttpResponse.getHtml(webUrl, charset, headerInfos);

        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
            log.info("网页不存在 webUrl={}", webUrl);
            return;
        }

        log.info(htmlSource);

        //第二步 正则匹配出内容
        //截取出目标数据
        List<String> stringList = RegexUtil.getSubUtil(htmlSource, regexPattern);

        //第三步 往数据库中存
        Goods goods = new Goods();
        goods.setWebUrl(webUrl);
        SaveToMysql saveToMysql = new SaveToMysql();


        for (int i = 0; i < stringList.size(); i++) {
            //  3-1 往数据库中存
            goods.setType(Integer.toString(i + 1));
            goods.setName(stringList.get(i));
            saveToMysql.saveToMasql("goods", goods);
        }
    }


}

