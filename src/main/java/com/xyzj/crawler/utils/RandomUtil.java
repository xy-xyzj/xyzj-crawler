package com.xyzj.crawler.utils;

import java.util.Random;

/**
 * 生成指定位数的随机数
 *
 * @author liuyangyang
 */
public class RandomUtil {

    public static String getRandom(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(String.valueOf(random.nextInt(10)));
        }
        return stringBuilder.toString();
    }
}
