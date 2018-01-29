package com.bshf.spider.dorule;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.IPModel.IPMessage;
import com.bshf.util.RegexUtil;
import com.bshf.util.database.MyRedis;
import com.bshf.util.httpbrowser.MyHttpResponse;
import com.bshf.util.savetomysql.ServiceImpl;
import org.apache.commons.lang.StringUtils;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderRuleBaixingZhaopingDetail extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {

			// 设置代理IP
			MyRedis redis = new MyRedis();
			//从redis数据库中随机拿出一个IP
			IPMessage ipMessage = redis.getIPByList();
			redis.close();
			String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());


			//	1-2 输出查看效果
			//System.out.println("htmlSource=============="+htmlSource);

			String regexTitle = "<div class='viewad-title'><h1>(.*?)</h1>";
			String regexPerson = "<label class=''>联系人：</label><label title='(.*?)'>";
			String regexPhonePre = "<a href='javaScript:;' title='点击查看完整号码' class='contact-no'>(.*?)\\*\\*\\*\\*</a>";
			String regexPhoneEnd = "<a data-contact='(.*?)'";
			String title = "空";
			String person = "空";
			String phonePre = "空";
			String phoneEnd = "空";
			if(StringUtils.isNotEmpty(RegexUtil.getSubUtilSimple(htmlSource, regexTitle))){
				title = RegexUtil.getSubUtilSimple(htmlSource, regexTitle);
			}
			if(StringUtils.isNotEmpty(RegexUtil.getSubUtilSimple(htmlSource, regexPerson))){
				person = RegexUtil.getSubUtilSimple(htmlSource, regexPerson);
			}
			if(StringUtils.isNotEmpty(RegexUtil.getSubUtilSimple(htmlSource, regexPhonePre))){
				phonePre = RegexUtil.getSubUtilSimple(htmlSource, regexPhonePre);
			}
			if(StringUtils.isNotEmpty(RegexUtil.getSubUtilSimple(htmlSource, regexPhoneEnd))){
				phoneEnd = RegexUtil.getSubUtilSimple(htmlSource, regexPhoneEnd);
			}



			goodsPO.setProvide(title + "|" + person + "|" + phonePre + phoneEnd);
			goodsPO.setType(ipMessage.getIPAddress()+":"+ipMessage.getIPPort());
			ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			goodsPOServiceImpl.add("goods_bx_zhaoping_deatil", goodsPO);

			//	2-1 提取有效内容
		//<div class='viewad-title'><h1>(.?*)</h1>
		//<label class=''>联系人：</label><label title='宫经理'>
		// <a href='javaScript:;' title='点击查看完整号码' class='contact-no'>1515404****</a>
		// <a data-contact='0753' href='javascript:void(0);' class='show-contact'>查看完整号码</a>


/*
			String rgex = "<div class='preview-hover'><a href='(.*?)' target='_blank' class='ad-title'>";
			List<String> stringList = RegexUtil.getSubUtil(htmlSource, rgex);
			//System.out.println(stringList.toString());
			for (String mystr : stringList) {
				goodsPO.setName(mystr);
				goodsPO.setType(ipMessage.getIPAddress()+":"+ipMessage.getIPPort());


			}*/
		}catch (Exception e){
			//不处理异常

		}

	}



	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://jiuzhaigou.baixing.com/gongren/a1143798947.html?from=vip";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		SpiderRuleBaixingZhaopingDetail spiderUtils = new SpiderRuleBaixingZhaopingDetail();
		spiderUtils.runSpider(goodsPO);
	}
}

