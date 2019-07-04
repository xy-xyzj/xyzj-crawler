package com.xyzj.crawler.utils.proxyip.config;


import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.proxyip.IPModel.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;


@Slf4j
public class RedisUtil {
    public static Jedis jedis = RedisConfig.getJedis();

    /**
     * ========================================
     *
     * @description: 保存ip信息到redis队列
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/7/4 10:10
     * <p>
     * ========================================
     */
    public static void setOneIp(IPMessage ipMessage) {
        //首先将ipMessage进行序列化
        byte[] bytes = SerializeUtil.serialize(ipMessage);
        jedis.rpush("IpPool".getBytes(), bytes);
    }

    /**
     * ========================================
     *
     * @description: 从队列中取出ip信息
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/7/4 10:11
     * <p>
     * ========================================
     */
    public static IPMessage getOneIp() {
        byte[] bytes = jedis.lpop("IpPool".getBytes());
        if (bytes != null) {
            Object o = SerializeUtil.unSerialize(bytes);
            if (o instanceof IPMessage) {
                return (IPMessage) o;
            }
        }
        return null;
    }

    public static void deleteKey(String key) {
        jedis.del(key);
    }

    public static void close() {
        RedisConfig.close(jedis);
    }
}
