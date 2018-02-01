package com.bshf.util.IPModel;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 使用httpclient调用API接口
 */
public class TestAPIHttpClient {

    private static String apiUrl = "https://www.kuaidaili.com/free/inha/1/"; //api链接

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(apiUrl);

            System.out.println("Executing request " + httpget.getURI());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine()); //获取Reponse的返回码
                System.out.println(EntityUtils.toString(response.getEntity())); //获取API返回内容
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

    }
}