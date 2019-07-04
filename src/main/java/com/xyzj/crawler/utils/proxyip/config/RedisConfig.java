package com.xyzj.crawler.utils.proxyip.config;

import java.util.ResourceBundle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
public class RedisConfig {

    private static GenericObjectPoolConfig config = null;
    private static String addr;
    private static int port;

    private static JedisPool jedisPool ;

    //加载配置文件
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("conf");

    //初始化连接
    static {
        addr = resourceBundle.getString("jedis.addr");
        port = Integer.parseInt(resourceBundle.getString("jedis.port"));
        try {
            //先进行redis数据的参数配置
            config = new GenericObjectPoolConfig();
            //链接耗尽时是否阻塞，false时抛出异常，默认是true，阻塞超时之后抛出异常
            config.setBlockWhenExhausted(true);
            //逐出策略类名，当连接超过最大空闲时间或最大空闲数抛出异常
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            //是否启用pool的jmx管理功能，默认是true
            config.setJmxEnabled(true);
            //最大空闲数，默认为8，一个pool最多有多少空闲的Jedis实例
            config.setMaxIdle(8);
            //最大连接数
            config.setMaxTotal(100);
            //当引入一个Jedis实例时，最大的等待时间，如果超过等待时间，抛出异常
            config.setMaxWaitMillis(1000 * 10);
            //获得一个jedis实例的时候是否检查连接可用性（ping()
            config.setTestOnBorrow(true);
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }
    }

    /**
     * ========================================
     *
     * @description: 取得Jedis实例
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/7/4 10:06
     * <p>
     * ========================================
     */
    public synchronized static Jedis getJedis() {
        if (jedisPool == null) {
            jedisPool = new JedisPool(config, addr, port);
        }
        return jedisPool.getResource();
    }

    /**
     * ========================================
     *
     * @description: 释放Jedis连接
     * @author: lyy
     * @param:
     * @return:
     * @exception:
     * @create: 2019/7/4 10:06
     * <p>
     * ========================================
     */
    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}