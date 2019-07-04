package com.xyzj.crawler.framework.defaults;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.framework.handler.SpiderRuleHandler;
import com.xyzj.crawler.framework.interfaces.ISpiderRule;
import com.xyzj.crawler.utils.parsehtmlstring.RegexUtil;
import com.xyzj.crawler.utils.savetomysql.SaveToMysql;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * @author lyy
 * @since 2018-10-27 13:08
 */
@Slf4j
public class DefaultSpiderRule extends SpiderRuleAbstract {

    public void runSpider(Param param,ISpiderRule spiderRule) {
        SpiderRuleHandler spiderRuleHandler = new SpiderRuleHandler();
        spiderRuleHandler.handler(param, spiderRule);
    }

    public void handlerGoods(Param param, String htmlSource) {
        String regexPattern = "<html>([\\s\\S]*)</html>";
        List<String> stringList = RegexUtil.getSubUtil(htmlSource, regexPattern);
        if (CollectionUtils.isEmpty(stringList)) {
            log.info("没有匹配需要都内容......");
        }
        Goods saveGoods = new Goods();
        saveGoods.setWebUrl(param.getWebUrl());
        SaveToMysql saveToMysql = new SaveToMysql();
        for (int i = 0; i < stringList.size(); i++) {
            saveGoods.setType(Integer.toString(i + 1));
            saveGoods.setName(stringList.get(i));
            saveToMysql.saveToMasql("goods", saveGoods);
        }
    }
}

