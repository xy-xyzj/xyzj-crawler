package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinedrugPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;

public class SpiderRuleZYW extends SpiderRuleAbstract {
	@Override
	public void runSpider(String srcUrl) {
		String htmlSource = HttpUtil.getPageCode(srcUrl, "GB2312");
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
			return;
		}
		// 取得名称
		List<String> nameList = new LinkedList<String>();
		nameList.add(".title h1"); // 商品名称
		List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt,
				true);
		System.out.println(nameTxt.toString());
		// 取得含有的字段名
		List<String> contextList = new LinkedList<String>();
		contextList.add(".text p");
		List<String> contTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, contextList,
				DataFormatStatus.CleanTxt, true);
		System.out.println(contTxt.toString());

		MedicinedrugPO medicinedrugPO = new MedicinedrugPO();
		medicinedrugPO.setWebsite(srcUrl);
		medicinedrugPO.setName(nameTxt.toString());
		medicinedrugPO.setContent(contTxt.toString());

		ServiceImpl medicinepbwServiceImpl = new ServiceImpl();
		medicinepbwServiceImpl.add("medicinezyw", medicinedrugPO);

	}

	public static void main(String[] args) {
		SpiderRuleZYW spiderUtils = new SpiderRuleZYW();
		String srcUrl = "http://www.zhongyoo.com/name/qiyeyizhihua_10355.html";
		spiderUtils.runSpider(srcUrl);
	}
}