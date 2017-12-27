package com.bshf.spider.dorule;

import java.util.LinkedList;
import java.util.List;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinedrugPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

public class SpiderRuleDrug extends SpiderRuleAbstract {
	@Override
	public void runSpider(String srcUrl) {
		String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
			return;
		}
		// 取得名称
		List<String> nameList = new LinkedList<String>();
		nameList.add(".commend"); // 商品名称
		List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt,
				true);
		System.out.println(nameTxt.toString());
		// 取得含有的字段名
		List<String> zdmList = new LinkedList<String>();
		zdmList.add(".common_bd span");
		List<String> zdmTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, zdmList, DataFormatStatus.CleanTxt,
				true);

		List<String> contextList = new LinkedList<String>();
		contextList.add(".common_bd dd");
		List<String> contTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, contextList,
				DataFormatStatus.CleanTxt, true);

		List<String> myzdmTxt = new LinkedList<String>();
		for (String zdt : zdmTxt) {
			myzdmTxt.add("|" + zdt);
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < myzdmTxt.size(); i++) {
			stringBuffer.append(myzdmTxt.get(i) + contTxt.get(i)); // 内容描述
		}

		MedicinedrugPO medicinedrugPO = new MedicinedrugPO();
		medicinedrugPO.setWebsite(srcUrl);
		medicinedrugPO.setName(nameTxt.toString());
		medicinedrugPO.setContent(stringBuffer.toString());

		ServiceImpl medicinepbwServiceImpl = new ServiceImpl();
		medicinepbwServiceImpl.add("medicinedrug", medicinedrugPO);

	}

	public static void main(String[] args) {
		SpiderRuleDrug spiderUtils = new SpiderRuleDrug();
		String srcUrl = "http://drugs.dxy.cn/drug/94700/detail.htm";
		spiderUtils.runSpider(srcUrl);
	}
}