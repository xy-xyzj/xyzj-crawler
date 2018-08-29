package com.xyzj.crawler.utils.proxyip.htmlparse;


import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.gethtmlstring.MyHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by paranoid on 17-4-10.
 */

public class URLFecter {
    //使用代理进行爬取
    public static boolean urlParse(String url, String ip, String port,
                                           List<IPMessage> ipMessages1) {
        //调用一个类使其返回html源码
        String html = MyHttpResponse.getHtmlWithProxyIp(url, ip, port,"utf-8",null);

        if(html != null) {
            //将html解析成DOM结构
            Document document = Jsoup.parse(html);

            //提取所需要的数据
            Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");

            for (int i = 1; i < trs.size(); i++) {
                IPMessage ipMessage = new IPMessage();
                String ipAddress = trs.get(i).select("td").get(1).text();
                String ipPort = trs.get(i).select("td").get(2).text();
                String ipType = trs.get(i).select("td").get(5).text();
                String ipSpeed = trs.get(i).select("td").get(6).select("div[class=bar]").
                        attr("title");

                ipMessage.setIPAddress(ipAddress);
                ipMessage.setIPPort(ipPort);
                ipMessage.setIPType(ipType);
                ipMessage.setIPSpeed(ipSpeed);


                ipMessages1.add(ipMessage);
            }

            return true;
        } else {
            out.println(ip+ ": " + port + " 代理不可用");

            return false;
        }
    }

    //使用本机IP爬取xici代理网站的第一页
    public static List<IPMessage> urlParse(List<IPMessage> ipMessages,Integer size) {

        for (int j = 1; j <size+1; j++) {
            String url = "http://www.xicidaili.com/nn/"+j;
            String html = MyHttpResponse.getHtml(url,"utf-8",null);

            //将html解析成DOM结构
            Document document = Jsoup.parse(html);

            //提取所需要的数据
            Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");

            for (int i = 1; i < trs.size(); i++) {
                IPMessage ipMessage = new IPMessage();
                String ipAddress = trs.get(i).select("td").get(1).text();
                String ipPort = trs.get(i).select("td").get(2).text();
                String ipType = trs.get(i).select("td").get(5).text();
                String ipSpeed = trs.get(i).select("td").get(6).select("div[class=bar]").
                        attr("title");

                ipMessage.setIPAddress(ipAddress);
                ipMessage.setIPPort(ipPort);
                ipMessage.setIPType(ipType);
                ipMessage.setIPSpeed(ipSpeed);

                ipMessages.add(ipMessage);
            }
        }
        return ipMessages;
    }



}
