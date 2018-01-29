package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleZhongYaoCai360Detail;
import com.bshf.util.ImportExcelUtil;
import com.bshf.util.SpiderTaskMultThread;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ZhongYaoCai360DetailDoMain {
	public static void main  (String[] args) throws Exception {

		//1-1.从Excel表中读取对象
		File file = new File("D:\\任务2018年1月8日.xlsx");
		FileInputStream fis = new FileInputStream(file);
		Map<String, String> m = new HashMap<String, String>();
		m.put("姓名", "name");
		List<Map<String, Object>> ls = ImportExcelUtil.parseExcel(fis, file.getName(), m);
		List<String> srcUrlList = new ArrayList<>();
		for (int i = 0; i <ls.size(); i++) {
			srcUrlList.add(ls.get(i).get("name").toString());
		}


		//2-1. 多线程爬取数据
		SpiderRuleZhongYaoCai360Detail spiderRule = new SpiderRuleZhongYaoCai360Detail();
		ExecutorService cachedThreadPool = java.util.concurrent.Executors.newFixedThreadPool(5);
		for (final String srcUrl : srcUrlList) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();
	}

}
