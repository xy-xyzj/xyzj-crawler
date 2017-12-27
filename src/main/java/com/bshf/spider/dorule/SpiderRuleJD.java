package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.JdGoods;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.HttpUtil;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderRuleJD extends SpiderRuleAbstract {
	private static 	Pattern  patternLink = Pattern.compile("href=\"(.*?)\">");
	private static  Pattern patternName = Pattern.compile("<em>(.*?)</em>");
	private static  Pattern patternNameId = Pattern.compile("item.jd.com/(.*?).html");
	private static  Pattern patternPrice = Pattern.compile("\"p\":\"(.*?)\"");

	@Override
	public void runSpider(String srcUrl) {
		//取得网页源码
		String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		//页面不存在
		if (null == htmlSource || htmlSource.contains("Not Found") || htmlSource.contains("无法访问此网站")
				|| htmlSource.contains("你所访问的页面就如那些遇害的同道") || htmlSource.contains("药品不存在！")) {
			return;
		}
		//打出来看看
		//System.out.println(htmlSource);
		//本页面可以取得商品链接和商品名称

		// 取得商品名称规则
		List<String> nameList = new LinkedList<String>();
		nameList.add(".p-name a");
		//取得商品名称
		List<String> nameTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.CleanTxt, true);
		//System.out.println(nameTxt.toString());

		//取得链接名称
		List<String> linkTxt = JsoupHtmlParser.getNodeContentBySelector(htmlSource, nameList, DataFormatStatus.TagAllContent, true);
		/*System.out.println(linkTxt.toString());*/
		//正则取出链接
		/* <atarget="_blank" title="" href="//item.jd.com/10365172840.html"> <em> 【东阿阿胶官方直营】东阿阿胶 OTC阿胶块250g可制作阿胶糕 补血滋阴 润燥止血 </em> <i class="promo-words"></i> </a>*/



		for (int i = 0; i <linkTxt.size(); i++) {
			Matcher matcherLink = patternLink.matcher(linkTxt.get(i));
			Matcher matcherName = patternName.matcher(linkTxt.get(i));
			Matcher matcherNameId = patternNameId.matcher(linkTxt.get(i));

			if(matcherLink.find() && matcherName.find() && matcherNameId.find()){
				/*System.out.println("https:"+matcherLink.group(1));
				System.out.println(matcherName.group(1).trim());
				System.out.println(matcherNameId.group(1));*/

				JdGoods jdGoods = new JdGoods();
				jdGoods.setName(matcherName.group(1).trim());

				//设置价格
				String url = "https://p.3.cn/prices/mgets?callback=jQuery1191838&type=1&area=1_2800_2851_0.172457579&pdtk=&pduid=1241177241&pdpin=%25E8%2581%259A%25E5%25A5%258E%25E4%25BD%2599%25E6%2581%25A8&pin=%E8%81%9A%E5%A5%8E%E4%BD%99%E6%81%A8&pdbp=0&skuIds=J_"+matcherNameId.group(1)+"&ext=11000000&source=item-pc";
				jdGoods.setPrice(getPrice(url));

				jdGoods.setWebsite("https:"+matcherLink.group(1));

				//存储到数据库
				ServiceImpl jdGoodsServiceImpl = new ServiceImpl();
				jdGoodsServiceImpl.add("jd_goods", jdGoods);
			}
		}
	}


	private BigDecimal getPrice(String srcUrl) {
		//取得网页源码
		String htmlSource = HttpUtil.getPageCode(srcUrl, "utf-8");
		System.out.println(htmlSource);

		Matcher matcherPrice = patternPrice.matcher(htmlSource);

		if(matcherPrice.find() ){
			return  new BigDecimal(matcherPrice.group(1));
		}
		return null;
	}




	public static void main(String[] args) {
		SpiderRuleJD spiderUtils = new SpiderRuleJD();
		String srcUrl = "https://list.jd.com/list.html?cat=9192,12632&page=2&sort=sort_totalsales15_desc&trans=1&JL=6_0_0#J_main";
		spiderUtils.runSpider(srcUrl);
		/*String priceUrl = "https://p.3.cn/prices/mgets?callback=jQuery1191838&type=1&area=1_2800_2851_0.172457579&pdtk=&pduid=1241177241&pdpin=%25E8%2581%259A%25E5%25A5%258E%25E4%25BD%2599%25E6%2581%25A8&pin=%E8%81%9A%E5%A5%8E%E4%BD%99%E6%81%A8&pdbp=0&skuIds=J_10347343263&ext=11000000&source=item-pc";
		spiderUtils.getPrice(priceUrl);*/
	}
}