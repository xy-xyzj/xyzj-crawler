package com.bshf.spider.dorule;

import java.util.LinkedList;
import java.util.List;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicineSfdaPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

public class SpiderRuleSfda extends SpiderRuleAbstract {
	@Override
	public void runSpider(String srcUrl) {
		String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
			return;
		}
		// 取得名称
		List<String> nameList = new LinkedList<String>();
		nameList.add("tr td a"); // 取得内容
		List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt,
				true);
		

		MedicineSfdaPO medicineSfdaPO = null;
		ServiceImpl medicineServiceImpl = null;
		for (String content : nameTxt) {
			medicineSfdaPO = new MedicineSfdaPO();
			medicineSfdaPO.setWebsite(srcUrl);
			medicineSfdaPO.setContent(content);

			medicineServiceImpl = new ServiceImpl();
			medicineServiceImpl.add("sfda", medicineSfdaPO);
		}
		System.out.println(srcUrl);
	}

	public static void main(String[] args) {
		SpiderRuleSfda spiderUtils = new SpiderRuleSfda();
		// 国产保健食品
		//	String srcUrl = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=30&State=1&bcId=118103385532690845640177699192&State=1&curstart=1&State=1&tableName=TABLE30&State=1&viewtitleName=COLUMN233&State=1&viewsubTitleName=COLUMN232,COLUMN235&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E4%25BF%259D%25E5%2581%25A5%25E9%25A3%259F%25E5%2593%2581&State=1";
		// 进口保健食品
		//String srcUrl = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=31&State=1&bcId=118103387241329685908587941736&State=1&curstart=1&State=1&tableName=TABLE31&State=1&viewtitleName=COLUMN263&State=1&viewsubTitleName=COLUMN262,COLUMN266&State=1&tableView=%25E8%25BF%259B%25E5%258F%25A3%25E4%25BF%259D%25E5%2581%25A5%25E9%25A3%259F%25E5%2593%2581&State=1";
		//国产器械
		//String srcUrl = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=26&State=1&bcId=118103058617027083838706701567&State=1&tableName=TABLE26&State=1&viewtitleName=COLUMN184&State=1&viewsubTitleName=COLUMN180,COLUMN181&State=1&curstart=1&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E5%2599%25A8%25E6%25A2%25B0&State=1";
		//进口器械
		String srcUrl ="http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=27&State=1&bcId=118103063506935484150101953610&State=1&curstart=1&State=1&tableName=TABLE27&State=1&viewtitleName=COLUMN200&State=1&viewsubTitleName=COLUMN199,COLUMN192&State=1&tableView=%25E8%25BF%259B%25E5%258F%25A3%25E5%2599%25A8%25E6%25A2%25B0&State=1";
		spiderUtils.runSpider(srcUrl);
	}
}