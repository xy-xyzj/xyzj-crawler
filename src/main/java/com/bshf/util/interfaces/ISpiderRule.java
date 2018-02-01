package com.bshf.util.interfaces;

import com.bshf.util.entity.GoodsPO;

import java.io.UnsupportedEncodingException;

public interface ISpiderRule {
	public void runSpider(String srcUrl) throws UnsupportedEncodingException;

	public void runSpider(String srcUrl, String loginCookie);

	public void runSpider(String srcUrl, Integer num);

	public void runSpider(GoodsPO goodsPO) throws UnsupportedEncodingException;
}
