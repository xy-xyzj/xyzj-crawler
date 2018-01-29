package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleDrugsDetail;
import com.bshf.spider.entity.GoodsPO;
import com.bshf.util.ImportExcelUtil;
import com.bshf.util.SpiderTaskMultThread;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrugsDetailDoMain {
	//0.1 基础准备
	/** 链接头部*/
	private static final String BASE_URL ="http://drugs.medlive.cn/drugref/drug_info_search.do?q=";


	public static void main(String[] args) throws Exception {


		//	1-1 从Excel表中读取目标数据
		File file = new File("D:\\java\\workspace\\learn\\crawler\\record\\中成药二次爬网名单20180110.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);

		//	1-2 将Excel表中的记录保存在map对象中
		Map<String, String> excel2Map = new HashMap<String, String>();
		excel2Map.put("序号", "orderNum");
		excel2Map.put("通用名称", "webUrl");
		List<Map<String, Object>> excel2List = ImportExcelUtil.parseExcel(fileInputStream, file.getName(), excel2Map);

		// 1-3 将excel2List中的map封装到对象中
		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 0; i < excel2List.size(); i++) {
			GoodsPO goodsPO = new GoodsPO();
			goodsPO.setOrderNum(excel2List.get(i).get("orderNum").toString());
			goodsPO.setWebUrl(BASE_URL+ URLEncoder.encode(excel2List.get(i).get("webUrl").toString()));
			goodsPOList.add(goodsPO);
		}

		//2-1启动线程进行数据爬取
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);
		for (final GoodsPO goodsPO : goodsPOList) {
			SpiderRuleDrugsDetail spiderRuleDrugsDetail = new SpiderRuleDrugsDetail();
			cachedThreadPool.execute(new SpiderTaskMultThread(goodsPO, spiderRuleDrugsDetail));
		}
		cachedThreadPool.shutdown();

		/*//2-2 因为有验证码 所以我决定3秒钟爬一个
		for (final GoodsPO goodsPO : goodsPOList) {
			SpiderRuleDrugsDetail spiderRuleDrugsDetail = new SpiderRuleDrugsDetail();
			spiderRuleDrugsDetail.runSpider(goodsPO);
			Thread.sleep(3000);
		}*/
	}

}
