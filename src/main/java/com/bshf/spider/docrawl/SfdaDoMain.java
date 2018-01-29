package com.bshf.spider.docrawl;

import com.bshf.spider.dorule.SpiderRuleSfda;
import com.bshf.util.SpiderTaskMultThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SfdaDoMain {
	public static void main(String[] args) {
		System.out.println("开始");
		// 实例化爬取单条数据的工具类
		SpiderRuleSfda spiderRule = new SpiderRuleSfda();
		// 存放目标连接
		List<String> srcUrls = new ArrayList<>();
		//List<Integer> myTarget = Arrays.asList(18,19,22,23,76,77,86,87,111,112,113,151,152,153,154,155,156,158,159,206,207,228,229,230,271);

		for (int i = 1; i <11053; i++) {
			//国产保健食品
			//String currentPage = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=30&State=1&bcId=118103385532690845640177699192&State=1&curstart="+ i +"&State=1&tableName=TABLE30&State=1&viewtitleName=COLUMN233&State=1&viewsubTitleName=COLUMN232,COLUMN235&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E4%25BF%259D%25E5%2581%25A5%25E9%25A3%259F%25E5%2593%2581&State=1";
			//进口保健食品
			//String currentPage =  "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=31&State=1&bcId=118103387241329685908587941736&State=1&curstart="+ i+"&State=1&tableName=TABLE31&State=1&viewtitleName=COLUMN263&State=1&viewsubTitleName=COLUMN262,COLUMN266&State=1&tableView=%25E8%25BF%259B%25E5%258F%25A3%25E4%25BF%259D%25E5%2581%25A5%25E9%25A3%259F%25E5%2593%2581&State=1";
			//国产器械
			//String currentPage= "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=26&State=1&bcId=118103058617027083838706701567&State=1&tableName=TABLE26&State=1&viewtitleName=COLUMN184&State=1&viewsubTitleName=COLUMN180,COLUMN181&State=1&curstart="+ i+"&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E5%2599%25A8%25E6%25A2%25B0&State=1";
			//进口器械
			//String currentPage="http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=27&State=1&bcId=118103063506935484150101953610&State=1&curstart="+ i+"&State=1&tableName=TABLE27&State=1&viewtitleName=COLUMN200&State=1&viewsubTitleName=COLUMN199,COLUMN192&State=1&tableView=%25E8%25BF%259B%25E5%258F%25A3%25E5%2599%25A8%25E6%25A2%25B0&State=1";
			//进口药品
			//String currentPage ="http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=36&State=1&bcId=124356651564146415214424405468&State=1&curstart="+ myTarget.get(i)+"&State=1&tableName=TABLE36&State=1&viewtitleName=COLUMN361&State=1&viewsubTitleName=COLUMN354,COLUMN823,COLUMN356,COLUMN355&State=1&tableView=%25E8%25BF%259B%25E5%258F%25A3%25E8%258D%25AF%25E5%2593%2581&State=1&cid=0&State=1&ytableId=0&State=1&searchType=search&State=1";

			// 国产药品
			String currentPage ="http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=25&State=1&bcId=124356560303886909015737447882&State=1&curstart="+i+"&State=1&tableName=TABLE25&State=1&viewtitleName=COLUMN167&State=1&viewsubTitleName=COLUMN821,COLUMN170,COLUMN166&State=1&tableView=%25E5%259B%25BD%25E4%25BA%25A7%25E8%258D%25AF%25E5%2593%2581&State=1&cid=0&State=1&ytableId=0&State=1&searchType=search&State=1";


			srcUrls.add(currentPage);
		}
		System.out.println("爬取");
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(3);
		for (final String srcUrl : srcUrls) {
			cachedThreadPool.execute(new SpiderTaskMultThread(srcUrl, spiderRule));
		}
		cachedThreadPool.shutdown();
	}
}
