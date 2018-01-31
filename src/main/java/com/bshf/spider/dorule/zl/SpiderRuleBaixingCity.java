package com.bshf.spider.dorule.zl;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.RegexUtil;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.savetomysql.ServiceImpl;

import java.util.List;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderRuleBaixingCity extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl());

			//	1-2 输出查看效果
			System.out.println("htmlSource=============="+htmlSource);

			//	2-1 提取有效内容

			String rgex = "<a href='//(.*?)'>";
			List<String> stringList = RegexUtil.getSubUtil(htmlSource, rgex);
			System.out.println(stringList.toString());
			for (String mystr : stringList) {
				goodsPO.setName(mystr);
				ServiceImpl goodsPOServiceImpl = new ServiceImpl();
				goodsPOServiceImpl.add("goods_bx_city", goodsPO);

			}
		}catch (Exception e){
			//不处理异常
		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://wuhan.baixing.com/gongzuo/?page=100";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SpiderRuleBaixingCity spiderUtils = new SpiderRuleBaixingCity();
		spiderUtils.runSpider(goodsPO);
	}
}

