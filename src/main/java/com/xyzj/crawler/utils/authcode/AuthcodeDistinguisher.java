package com.xyzj.crawler.utils.authcode;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * 验证码图片识别工具
 * 使用方法如下：
 * <lu>
 *     <li>将本项目下的docs/authcode的tessdata.zip解压至任意目录如z:/tessddta</li>
 *     <li>自己生成验证码图片，或者使用docs/authcode/**.png图片，复制至任意目录，如z:/abcd.png</li>
 *     <li>设置tesseract的datapath为步骤1中的目录</li>
 *     <li>修改main()中的tesseract的doOCR()方法的参数为步骤2中的图片路径</li>
 *     <li>运行即可</li>
 * </lu>
 *
 * @author liulei@bshf360.com
 * @since 2017-07-19 14:26
 */
public class AuthcodeDistinguisher {

    private static  void downloadImage() throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        for (int i = 0; i < 10; i++) {
            String url = "http://beijing.qd8.com.cn/jobs/ajax/showphone.ashx?v=2JG4ozQd13oYUdXFs0YrOQ%3d%3d";
            HttpGet getMethod = new HttpGet(url);
            try {
                HttpResponse response = httpClient.execute(getMethod, new BasicHttpContext());
                HttpEntity entity = response.getEntity();
                InputStream instream = entity.getContent();
                OutputStream outstream = new FileOutputStream(new File("d:/", i + ".gif"));
                int l = -1;
                byte[] tmp = new byte[2048];
                while ((l = instream.read(tmp)) != -1) {
                    outstream.write(tmp);
                }
                outstream.close();
            } finally {
                getMethod.releaseConnection();
            }
        }

        System.out.println("下载验证码完毕！");
    }

    public static String getString(String url) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\java\\workspace\\learn\\crawler\\tessdata");
        try {
            //"http://beijing.qd8.com.cn/jobs/ajax/showphone.ashx?v=2JG4ozQd13oYUdXFs0YrOQ%3d%3d"
            File file = new File(url);
            String result = tesseract.doOCR(file);
            System.out.println(result);
            return result;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


    public static void main(String[] a) throws  Exception {
        downloadImage();

       /* Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\java\\workspace\\learn\\crawler\\tessdata");
        try {
            //String result = tesseract.doOCR(new File("D:\\java\\workspace\\learn\\crawler\\yzm\\showphone.gif"));
            URL url = new URL("http://beijing.qd8.com.cn/jobs/ajax/showphone.ashx?v=2JG4ozQd13oYUdXFs0YrOQ%3d%3d");


            String file = url.getFile();
            String result = tesseract.doOCR(file);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }*/
    }

    /**
     * 将图片的base64字符串生成到指定位置的文件中
     * @param imgStr 图片的base64编码字符串
     * @param imgFilePath 目标文件位置
     */
    public static boolean generateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null){ // 图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
