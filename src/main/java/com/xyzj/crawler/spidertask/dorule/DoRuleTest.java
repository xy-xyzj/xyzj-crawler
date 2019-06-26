package com.xyzj.crawler.spidertask.dorule;

import com.xyzj.crawler.framework.factory.SpiderRuleFactory;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 单页面抓取
 *
 * @author lyy
 * @since 2018-10-27 14:22
 */
@Slf4j
public class DoRuleTest {

    public static void main(String[] args) {

        //工厂取得默认实例
        ISpiderRule spiderRule = new SpiderRuleFactory().getInstance();

        //封装参数
        HashMap<String, String> params = new HashMap<>();
        String url = "https://api.lianlianlvyou.com/v1/wx/booking?timestamp=1540645284482&Authorization=null";
        params.put("address","123");
        params.put("bookingDay","1546732800000");
        params.put("bookingItemId","1952");
        params.put("codeList","5581031411");
        params.put("memo","123");
        params.put("name","刘洋杨");
        params.put("phone","15178745169");
        params.put("type","0");
        params.put("x","914b7bf7c119e516cce52221c789f41b");
        String json = HttpResponseUtil.getJson(url, "utf-8", new HashMap<String, String>(), params);
        log.info(json);

    }
}
