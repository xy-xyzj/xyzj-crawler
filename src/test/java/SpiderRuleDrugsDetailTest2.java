import com.bshf.spider.dorule.intfaces.ISpiderRule;
import com.bshf.spider.entity.GoodsPO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 */
public class SpiderRuleDrugsDetailTest2 implements ISpiderRule {
	@Override
	public void runSpider(String srcUrl, String loginCookie) {

	}

	@Override
	public void runSpider(String srcUrl, Integer num) {

	}

	@Override
	public void runSpider(GoodsPO goodsPO) throws UnsupportedEncodingException {

	}

	@Override
	public void runSpider(String srcUrl) throws UnsupportedEncodingException {
		String url = "http://xjz.cqdsrb.com.cn/plugin.php?id=votex3:vote&actid=97&appid=12964&formhash=0b6b482&mobile=2";
		//// 设置代理IP
		//MyRedis redis = new MyRedis();
		////从redis数据库中随机拿出一个IP
		//IPMessage ipMessage = redis.getIPByList();
		//redis.close();
		//String htmlSource = MyHttpResponse.getHtml(url, ipMessage.getIPAddress(), ipMessage.getIPPort());
        //
		//System.out.println(htmlSource);
		getHtml(url);
	}

	//对上一个方法的重载，使用本机ip进行网站爬取
	public static String getHtml(String url) {
		String entity = null;
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.disableCookieManagement();
		httpClientBuilder.setDefaultCookieStore(null);
		CloseableHttpClient httpClient = httpClientBuilder.build();

		//设置超时处理
		HttpGet httpGet = new HttpGet(url);




		try {

			//客户端执行httpGet方法，返回响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

			//得到服务响应状态码
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			}

			httpResponse.close();
			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return entity;
	}

	public static void main(String[] args) throws Exception {

		String url = "http://xjz.cqdsrb.com.cn/plugin.php?id=votex3:vote&actid=97&appid=12964&formhash=0b6b482&mobile=2";

		String htmlSource = getHtml(url);

		System.out.println(htmlSource);

		/*ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
		for (int i=0;i<1000;i++) {
			SpiderRuleDrugsDetailTest2 spiderRuleDrugsDetailTest2 = new SpiderRuleDrugsDetailTest2();
			cachedThreadPool.execute(new SpiderTaskMultThread("123",spiderRuleDrugsDetailTest2));
		}
		cachedThreadPool.shutdown();*/


	}
}

