package com.xyzj.bigdata.in;

import com.alibaba.excel.EasyExcel;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读的常见写法
 *
 * @author Jiaju Zhuang
 */

public class ReadTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTest.class);

    private static String fileName ;
    public void simpleRead() {
        // 写法1：
        if (fileName == null) {
            fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        }
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        LOGGER.info("文件路径是{}",fileName);
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    public static void main(String[] args) {
        if (args.length!=0) {
            fileName = args[0];
        }
        long l = System.currentTimeMillis();
        ReadTest readTest = new ReadTest();
        readTest.simpleRead();
        long l1 = System.currentTimeMillis() - l;
        LOGGER.info("总耗时{}毫秒 ",l1);
    }


}
