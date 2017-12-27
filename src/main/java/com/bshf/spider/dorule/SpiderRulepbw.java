package com.bshf.spider.dorule;

import java.util.LinkedList;
import java.util.List;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinepbwPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

public class SpiderRulepbw extends SpiderRuleAbstract{
	@Override
	public void runSpider(String srcUrl) {
		String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")) {
			return;
		}
		// 取得含有的字段名
		List<String> zdmList = new LinkedList<String>();
		zdmList.add(".cms_list pre center"); // 中文名称 汉语拼音 英文名称
		List<String> zdmTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, zdmList, DataFormatStatus.CleanTxt,
				true);
		List<String> contextList = new LinkedList<String>();
		contextList.add(".cms_list pre div"); // 内容描述
		List<String> contTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, contextList,
				DataFormatStatus.CleanTxt, true);
		MedicinepbwPO medicinepbwPO = new MedicinepbwPO();
		medicinepbwPO.setWebsit(srcUrl);
		medicinepbwPO.setName(zdmTxt.get(0).toString());
		medicinepbwPO.setHypy(zdmTxt.get(1).toString());
		if (zdmTxt.size() == 3) {
			medicinepbwPO.setEnglishname(zdmTxt.get(2).toString());
		}
		medicinepbwPO.setContent(contTxt.get(0).toString());
		ServiceImpl medicinepbwServiceImpl = new ServiceImpl();
		medicinepbwServiceImpl.add("medicinepbw", medicinepbwPO);

	}

	public static void main(String[] args) {
		SpiderRulepbw spiderUtils = new SpiderRulepbw();
		String srcUrl = "http://db.ouryao.com/yd2015/view.php?v=txt&id=1";
		spiderUtils.runSpider(srcUrl);

	}
}