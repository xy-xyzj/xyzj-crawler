package com.xyzj.crawler.utils.gethtmlstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 爬虫辅助工具类
 *
 * @author TongWei.Chen 2017-05-17 10:15:45
 */
@Slf4j
public class HttpClientHelper {
    public static String post(String url, Map<String, String> params,String charset) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (!CollectionUtils.isEmpty(params)) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
                }
            }


            CloseableHttpClient httpClient = HttpClients.custom().build();
            HttpPost httpPost = new HttpPost(url);

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nameValuePairs, charset);
            httpPost.setEntity(uefEntity);


            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, charset);
                response.close();
                httpPost.releaseConnection();
                httpClient.close();
                return result;
            }else{
                return null;
            }
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return null;
        }
    }

    /**
     * 请求一个url，返回结果
     *
     * @param url：url
     * @return
     */
    public static String get(String url,String charset) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        try {
            CloseableHttpClient httpClient = HttpClients.custom().build();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, charset);
                response.close();
                httpGet.releaseConnection();
                httpClient.close();
                return result;
            }else{
                return null;
            }
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return null;
        }
    }
}