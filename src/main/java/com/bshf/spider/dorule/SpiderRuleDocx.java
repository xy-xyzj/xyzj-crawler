/******************************************************************
 *
 *    Powered By Baishi Huifang.
 *
 *    TODO(用一句话描述该文件做什么)
 *
 *    @author:     mn11235813@163.com
 *
 *    Create at:   2017-08-01 14:51:31
 *
 *    Revision:
 *
 *    2017-08-01 14:51:31
 *        - first revision
 *
 *****************************************************************/
package com.bshf.spider.dorule;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.bshf.spider.dorule.abs.SpiderRuleAbstract;
import com.bshf.spider.entity.MedicinekadPO;
import com.bshf.util.savetomysql.ServiceImpl;

/**
 * 利用poi读取docx文档信息
 * @author mn11235813@163.com
 * @date 2017-08-01 14:51:31
 */
public class SpiderRuleDocx extends SpiderRuleAbstract{
	public void runSpider() throws Exception {
		InputStream is = new FileInputStream("D:\\111.docx");
		XWPFDocument doc = new XWPFDocument(is);
		XWPFWordExtractor extractor = new XWPFWordExtractor(doc);		
		String[] split = extractor.getText().split("\n\n");
		
		MedicinekadPO medicinekadPO = new MedicinekadPO();
		ServiceImpl medicinepbwServiceImpl = new ServiceImpl();
		for (String string : split) {
			//System.out.println(string);
			medicinekadPO.setProducts(string);
			medicinepbwServiceImpl.add("medicinekad", medicinekadPO);
		}
	}
	public static void main(String[] args) {
		SpiderRuleDocx spiderUtils = new SpiderRuleDocx();
		try {
			spiderUtils.runSpider();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
