package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinekadPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class SpiderRuleEntry extends SpiderRuleAbstract {
    @Override
    public void runSpider(String srcUrl) {
        String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
        //System.out.println(htmlSource);
        if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
                || htmlSource.contains("抱歉，您所访问的页面不存在...") || htmlSource.contains("药品不存在！")) {
            return;
        }
        // 取得名称
        List<String> nameList = new LinkedList<String>();
        // 取得内容
        nameList.add(".main-content>*");
        List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt,true);

        /*List<String> nameTxt = JsoupHtmlParser.getContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt,true);
        String nameTxtStr = StringUtils.join(nameTxt, "|");
        System.out.println("name: "+nameTxtStr);*/
        //标题
        List<String> levelList= new LinkedList<>();
        levelList.add(".level-2");
        List<String> levelListStr = JsoupHtmlParser.getContentBySelector(htmlSource, levelList, DataFormatStatus.CleanTxt,true);
        levelListStr.add("参考资料");
        levelListStr.add("目录");

        String resultLevelStr = StringUtils.join(levelListStr, "|");
        String nameTxtStr = StringUtils.join(nameTxt, "*");
        for(int i=0;i<levelListStr.size();i++) {
            //System.out.println(levelListStr.get(i));
            nameTxtStr = nameTxtStr.replace(levelListStr.get(i),"|"+levelListStr.get(i)+"#");
        }
        //System.out.println("name: "+nameTxtStr);
        MedicinekadPO medicinekadPO = new MedicinekadPO();
		medicinekadPO.setWebsite(srcUrl);

		medicinekadPO.setProducts(nameTxtStr);
		//medicinekadPO.setInstructions(null);
		ServiceImpl medicinepbwServiceImpl = new ServiceImpl();
		medicinepbwServiceImpl.add("medicinekad", medicinekadPO);
    }

    public static void main(String[] args) {
        SpiderRuleEntry spiderUtils = new SpiderRuleEntry();
        String srcUrl = "https://baike.baidu.com/item/%E6%9C%88%E6%A1%82%E5%AD%90/8381528";
        spiderUtils.runSpider(srcUrl);
    }
}