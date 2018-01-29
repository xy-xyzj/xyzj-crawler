package com.bshf.util;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
  
/**  
 * @author Jason 
 * @date  Oct 27, 2010 
 * @version 1.0  
 */  
public class TestProxyIp  {  
    private static final Logger log = Logger.getLogger(TestProxyIp.class);  
      
    public static void main(String[] args) throws IOException {  
        System.setProperty("http.maxRedirects", "50");  
        System.getProperties().setProperty("proxySet", "true");  
        // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以  
        String ip = "93.91.200.146";  
        ip = "221.130.18.5";  
        ip = "221.130.23.135";  
        ip = "221.130.18.78";  
        ip = "221.130.23.134";  
        ip = "221.130.18.49";  
        ip = "111.1.32.36";  
        ip = "221.130.18.49";  
        ip = "221.130.18.49";  
        System.getProperties().setProperty("http.proxyHost", ip);  
        System.getProperties().setProperty("http.proxyPort", "80");  
          
        //确定代理是否设置成功  
        log.info(getHtml("http://www.ip138.com/ip2city.asp"));  
          
    }  
      
    private static String getHtml(String address){  
        StringBuffer html = new StringBuffer();  
        String result = null;  
        try{  
            URL url = new URL(address);  
            URLConnection conn = url.openConnection();  
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");  
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());  
              
            try{  
                String inputLine;  
                byte[] buf = new byte[4096];  
                int bytesRead = 0;  
                while (bytesRead >= 0) {  
                    inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");  
                    html.append(inputLine);  
                    bytesRead = in.read(buf);  
                    inputLine = null;  
                }  
                buf = null;  
            }finally{  
                in.close();  
                conn = null;  
                url = null;  
            }  
            result = new String(html.toString().trim().getBytes("ISO-8859-1"), "gb2312").toLowerCase();  
              
        }catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }finally{  
            html = null;              
        }  
        return result;  
    }  
} 