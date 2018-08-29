package com.xyzj.crawler.spidertask.dorule.imooc;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xyzj.crawler.framework.abstracts.SpiderRuleAbstract;
import com.xyzj.crawler.framework.entity.GoodsPO;
import java.io.File;
import java.net.URL;
import org.apache.commons.io.FileUtils;

/**
 * https://coding.imooc.com/lesson/223.html#mid=14557
 */
public class IMoocSpiderRule extends SpiderRuleAbstract {
    @Override
    public void runSpider(GoodsPO goodsPO) {
        try {
            // 下载bgm到spingboot服务器
            URL url = new URL(goodsPO.getWebUrl());
            File file = new File("/Volumes/d/my-");
            FileUtils.copyURLToFile(url, file);


            // 1-1取得源码
            WebClient wc = new WebClient(BrowserVersion.FIREFOX_45);
            wc.setJavaScriptTimeout(50000);
            wc.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书
            wc.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本
            wc.getOptions().setCssEnabled(false);//禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常
            wc.getOptions().setTimeout(100000);//设置连接超时时间
            wc.getOptions().setDoNotTrackEnabled(false);

            HtmlPage page = wc.getPage(goodsPO.getWebUrl());

            //获取页面
            String htmlSource = page.asXml();
            System.out.println("htmlSource" + htmlSource);

            /*

			String regex = "amp;Id=(.*?)'";
			//截取出id
			List<String> stringList =RegexUtil.getSubUtil(htmlSource, regex);
			//System.out.println("stringList"+stringList.toString());
            */


            //	2-1 提取有效内容
            //  table a


        /*

			for (int i=0;i<stringList.size();i++ ) {
				//  3-1 往数据库中存
				goodsPO.setType(Integer.toString(i+1));
				goodsPO.setWebUrl("http://app1.sfda.gov.cn/datasearch/face3/content.jsp?tableId=30&tableName=TABLE30&tableView=%B9%FA%B2%FA%B1%A3%BD%A1%CA%B3%C6%B7&Id="+stringList.get(i));
				SfdaDetailSpiderRule sfdaDetailSpiderRule = new SfdaDetailSpiderRule();
				sfdaDetailSpiderRule.runSpider(goodsPO);
			}*/
        } catch (Exception e) {
            //不处理异常
        }

    }


    public static void main(String[] args) {
        GoodsPO goodsPO = new GoodsPO();
        String srcUrl = "https://video1.sycdn.imooc.com/shizhan/5b06566fe420e576728b47b9/H/0187d9ee9c003586.ts";
        https://video1.sycdn.imooc.com/shizhan/5b06566fe420e576728b47b9/H/e5fd2bb685e97571.jpg
        goodsPO.setWebUrl(srcUrl);
        goodsPO.setOrderNum("1");
        IMoocSpiderRule spiderUtils = new IMoocSpiderRule();
        spiderUtils.runSpider(goodsPO);
    }
}

