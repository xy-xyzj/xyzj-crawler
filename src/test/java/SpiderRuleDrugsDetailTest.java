import com.bshf.util.httpbrowser.MyHttpResponse;

/**
 * 用药参考网
 * http://drugs.medlive.cn/index.jsp
 * 根据关键字 取得链接 并取得其内容
 * */
public class SpiderRuleDrugsDetailTest  {



	public static void main(String[] args)  throws Exception{
		String url = "http://xjz.cqdsrb.com.cn/plugin.php?id=votex3:vote&actid=97&appid=12964&formhash=0b6b482&mobile=2";
		String result = MyHttpResponse.getHtml(url);
		System.out.println(result);




	}
}

