package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.DataFormatStatus;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.parser.JsoupHtmlParser;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderRuleJiankeDetail extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {
			// 1-1 取得页面元素
			String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl());

			//	1-2 输出查看效果
				//System.out.println("htmlSource=============="+htmlSource);

			//	2-1 提取有效内容
			//  .bigfont p 取出药品详情
			List<String> targetDetailListPattern = new LinkedList<String>();
			targetDetailListPattern.add(".bigfont p");
			List<String> targetDetailList = JsoupHtmlParser.getNodeContentBySelector(htmlSource, targetDetailListPattern, DataFormatStatus.CleanTxt, true);

			//  2-2输出查看
			//System.out.println(targetDetailList.toString().replace("【","|【").replace("[","|["));

			//  3-1 往数据库中存

			//  做字符替换 好在Excel表中分列
			goodsPO.setProvide(targetDetailList.toString().replace("【","|【").replace("[","|["));
			ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			goodsPOServiceImpl.add("goods_jianke_detail", goodsPO);

		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "https://www.jianke.com/product/50841.html";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		goodsPO.setName("测试");
		SpiderRuleJiankeDetail spiderUtils = new SpiderRuleJiankeDetail();
		spiderUtils.runSpider(goodsPO);
	}
}

