package com.bshf.spider.dorule;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class OschinaBlogPageProcesser implements PageProcessor {

    private Site site = Site.me();

    @Override
    public void process(Page page) {
        //List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        //page.addTargetRequests(links);
        //page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1").toString());
        //page.putField("content", page.getHtml().$("div.content").toString());
        //page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
        String toString = page.getHtml().getDocument().toString();
        System.out.println(toString);
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        Spider.create(new OschinaBlogPageProcesser()).addUrl("https://db.yaozh.com/instruct/147336.html").run();
    }
}