package com.bshf.util.orther;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class LoginUtil {

	private LoginUtil() {
	};

	public static String login(String loginUrl) {

		HttpClient httpClient = new HttpClient();
		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		PostMethod postMethod = new PostMethod(loginUrl);
		// 设置登陆时要求的信息，用户名和密码
		NameValuePair[] data = { new NameValuePair("username", "18223171125"), new NameValuePair("pwd", "fmfrtkoi"),
				new NameValuePair("formhash", "AF6B2FB6EC") };

		postMethod.setRequestBody(data);
		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(postMethod);
			// 获得登陆后的 Cookie
			Cookie[] cookies = httpClient.getState().getCookies();
			StringBuffer tmpcookies = new StringBuffer();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
			}
			return tmpcookies.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// 登陆 Url
		String loginUrl = "https://www.yaozh.com/login";
		// 需登陆后访问的 Url
		String dataUrl = "https://db.yaozh.com/zhbc/1.html";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(dataUrl);
		PostMethod postMethod = new PostMethod(dataUrl);
		getMethod.setRequestHeader("cookie", LoginUtil.login(loginUrl));
		postMethod.setRequestHeader("Referer", "https://www.yaozh.com");
		try {
			httpClient.executeMethod(getMethod);
			String htmlSource = getMethod.getResponseBodyAsString();
			// 打印出返回数据，检验一下是否成功
			System.out.println(htmlSource);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
