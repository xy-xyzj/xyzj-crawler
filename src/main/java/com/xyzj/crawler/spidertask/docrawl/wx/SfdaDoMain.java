package com.xyzj.crawler.spidertask.docrawl.wx;

import com.xyzj.crawler.framework.entity.GoodsPO;
import com.xyzj.crawler.framework.threads.SpiderTaskMultThread;
import com.xyzj.crawler.spidertask.dorule.wx.SfdaListSpiderRule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SfdaDoMain {


	public static void main  (String[] args) throws Exception {
		// 1-1 定义baseUrl
		String baseUrlQZ = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=30&State=1&bcId=118103385532690845640177699192&State=1&curstart=";

		String baseUrlHZ = "&State=1&tableName=TABLE30&State=1&viewtitleName=COLUMN233&State=1&viewsubTitleName=COLUMN235,COLUMN232&State=1&keyword=%25E5%2585%258D%25E7%2596%25AB%25E5%258A%259B&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E4%25BF%259D%25E5%2581%25A5%25E9%25A3%259F%25E5%2593%2581&State=1&cid=0&State=1&ytableId=0&State=1&searchType=search&State=1";


		List<GoodsPO> goodsPOList = new ArrayList<>();
		for (int i = 1; i<200; i++) {
			GoodsPO  goodsPO = new GoodsPO();
			goodsPO.setWebUrl(baseUrlQZ+i+baseUrlHZ);
			goodsPO.setOrderNum(Integer.toString(i));
			goodsPOList.add(goodsPO);
		}

		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(1);
		for (final GoodsPO goodsPO : goodsPOList) {
			SfdaListSpiderRule  sfdaListSpiderRule= new SfdaListSpiderRule();
			cachedThreadPool.execute(new SpiderTaskMultThread( goodsPO, sfdaListSpiderRule));
		}
		cachedThreadPool.shutdown();
	}

}
