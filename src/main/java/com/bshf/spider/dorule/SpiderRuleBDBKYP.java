package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuyangyang@bshf360.com
 * @since 2017-12-05 10:54
 */
public class SpiderRuleBDBKYP extends SpiderRuleAbstract {

    @Override
    public void runSpider(GoodsPO goodsPO) {
        //第一步 取得源码
        String htmlSource = HttpUtil.getPageCode(goodsPO.getWebUrl(), "utf-8");
        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("抱歉，您所访问的页面不存在...") || htmlSource.contains("药品不存在！")) {
            return;
        }

        //第二步 取得需求内容
        // 取得名称
        List<String> nameList = new LinkedList<String>();
        // 取得内容
        nameList.add(".main-content>*");
        List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt,true);

        //标题
        List<String> levelList= new LinkedList<>();
        levelList.add(".level-2");
        List<String> levelListStr = JsoupHtmlParser.getContentBySelector(htmlSource, levelList, DataFormatStatus.CleanTxt,true);
        levelListStr.add("参考资料");
        levelListStr.add("目录");

        String resultLevelStr = StringUtils.join(levelListStr, "|");
        String nameTxtStr = StringUtils.join(nameTxt, "*");
        for(int i=0;i<levelListStr.size();i++) {
            nameTxtStr = nameTxtStr.replace(levelListStr.get(i),"|"+levelListStr.get(i)+"#");
        }
        //设置提供方
        goodsPO.setProvide(nameTxtStr);
        //第三步 往数据库中存
        ServiceImpl goodsPOServiceImpl = new ServiceImpl();
        goodsPOServiceImpl.add("mygoods", goodsPO);
    }

    public static void main(String[] args) {
        SpiderRuleBDBKYP spiderUtils = new SpiderRuleBDBKYP();
        String srcUrl = "https://baike.baidu.com/item/糠甾醇片";
        GoodsPO goodsPO = new GoodsPO();
        goodsPO.setType("西药制剂");
        goodsPO.setName("糠甾醇片");
        goodsPO.setWebUrl("https://baike.baidu.com/item/糠甾醇片");
        goodsPO.setOrderNum("1");
        spiderUtils.runSpider(goodsPO);
    }
}
