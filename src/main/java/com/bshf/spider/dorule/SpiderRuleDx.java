package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinePO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;

public class SpiderRuleDx extends SpiderRuleAbstract {
	@Override
	public void runSpider(String srcUrl) {
		String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		List<String> selList = new LinkedList<String>();
		// 规则
		selList.add(".more-infomation");
		List<String> cleanTxtAll = JsoupHtmlParser.getNodeContentBySelector(htmlSource, selList,
				DataFormatStatus.CleanTxt, true);
		System.out.println(cleanTxtAll);
		// 封装数据
		MedicinePO medicinePO = new MedicinePO();
		medicinePO.setCommname(cleanTxtAll.get(0));// 通用名称
		medicinePO.setElement(cleanTxtAll.get(1));// 成分
		medicinePO.setDisease(cleanTxtAll.get(2));// 性状
		medicinePO.setCharacter(cleanTxtAll.get(3));// 适应症
		medicinePO.setStanderd(cleanTxtAll.get(4));// 规格
		medicinePO.setUseage(cleanTxtAll.get(5));// 用法用量
		medicinePO.setWrong(cleanTxtAll.get(6));// 不良反应
		medicinePO.setTaboo(cleanTxtAll.get(7));// 禁忌
		medicinePO.setNotice(cleanTxtAll.get(8));// 注意事项
		medicinePO.setGravida(cleanTxtAll.get(9));// 孕妇 妇女用药
		medicinePO.setChildren(cleanTxtAll.get(10));// 儿童
		medicinePO.setOldman(cleanTxtAll.get(11));// 老年
		medicinePO.setTogether(cleanTxtAll.get(12));// 药物相互作用
		medicinePO.setPoisonstudy(cleanTxtAll.get(13));// 毒物研究
		medicinePO.setStoragestyle(cleanTxtAll.get(14));// 存储方式
		medicinePO.setApprovalnumber(cleanTxtAll.get(15));// 批准文号
		medicinePO.setCompany(cleanTxtAll.get(16));// 生产企业
		medicinePO.setLactationlevel(cleanTxtAll.get(17));// 哺乳期分级
		// 保存到数据库
		ServiceImpl medicineServiceImpl = new ServiceImpl();
		medicineServiceImpl.add("medicine", medicinePO);
	}

	public static void main(String[] args) {
		SpiderRuleDx spiderRuleDrug = new SpiderRuleDx();
		String srcUrl = "http://drugs.medlive.cn/drugref/html/19328.shtml";
		spiderRuleDrug.runSpider(srcUrl);

	}
}
