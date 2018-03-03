package com.xyzj.crawler.spidertask.dorule.zl;

import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class Qd8ZhaopingDetailSpiderRule extends SpiderRuleAbstract {
	@Override
	public void runSpider(GoodsPO goodsPO)  {
		try {


			//String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), goodsPO.getType(), goodsPO.getName());

			String htmlSource = MyHttpResponse.getMyHtml(goodsPO.getWebUrl());

			//	1-2 输出查看效果
			System.out.println("htmlSource=============="+htmlSource);

			//<h1 class="f24 fc4b">北京销售精英</h1>
			//<span id="ck2">联 系 人：王女士 (联系我时请说明是在快点8分类信息网上看到的)<br />

		/*	String regexTitle = "<div class='viewad-title'><h1>(.*?)</h1>";
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
			goodsPO.setType(goodsPO.getType()+":"+goodsPO.getName());
			ServiceImpl goodsPOServiceImpl = new ServiceImpl();
			goodsPOServiceImpl.add("goods_bx_zhaoping_deatil_2", goodsPO);*/


		}catch (Exception e){
			//不处理异常
		}
	}

	public static void main(String[] args) {
		GoodsPO goodsPO = new GoodsPO();
		String srcUrl = "http://beijing.qd8.com.cn/yewu/xinxi12_2804280.html";
		goodsPO.setWebUrl(srcUrl);
		goodsPO.setOrderNum("1");
		Qd8ZhaopingDetailSpiderRule spiderUtils = new Qd8ZhaopingDetailSpiderRule();
		spiderUtils.runSpider(goodsPO);
	}
}

