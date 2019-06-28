package com.xyzj.crawler.framework.defaults;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.Goods;
import com.xyzj.crawler.framework.entity.Param;
import com.xyzj.crawler.utils.gethtmlstring.HttpResponseUtil;
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
public  class DefaultSpiderRule extends SpiderRuleAbstract {
    @Override
    public void runSpider(Param param) {
        try {
            //第一步 拿到源码
            String htmlSource = HttpResponseUtil.getHtmlSource(param);
            if (htmlSource == null)return;
            //第二步 匹配出内容 并存储到数据库
            getGoods(param, htmlSource);
        }finally {
            //第三步 如果有减1个操作
            if (param.getCountDownLatch() !=null){
                param.getCountDownLatch().countDown();
                log.info("还有{}个任务等待中{}", param.getCountDownLatch().getCount());
            }
        }
    }


    /**
      *
      *========================================
      * @description: 这个方法得 自己研究怎么写
      * @author: lyy
      * @param:
      * @return:
      * @exception:
      * @create: 2019/6/28 11:59
      *
      *========================================
     */
    private void getGoods(Param param,  String htmlSource) {
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

