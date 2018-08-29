package com.xyzj.crawler.utils.proxyip.database;


import com.xyzj.crawler.utils.proxyip.IPModel.IPMessage;
import com.xyzj.crawler.utils.proxyip.IPModel.SerializeUtil;
import java.util.List;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Created by hg_yi on 17-8-9.
 *
 *
 */
@Slf4j
public class MyRedis  {


    Jedis jedis = RedisDB.getJedis();

    //将ip信息保存在Redis列表中
    public void setIpToList(List<IPMessage> ipMessages) {
        for (IPMessage ipMessage : ipMessages) {
            //首先将ipMessage进行序列化
            byte[] bytes = SerializeUtil.serialize(ipMessage);
            jedis.rpush("IPPool".getBytes(), bytes);
        }
    }

    //将Redis中保存的对象进行反序列化
    public IPMessage getIpByList() {
        int rand = (int)(Math.random()*jedis.llen("IpPool"));
        Object o = SerializeUtil.unserialize(jedis.lindex("IPPool".getBytes(), rand));
        if (o instanceof IPMessage) {
            return (IPMessage)o;
        } else {
            log.info("不是IPMessage的一个实例~");
            return null;
        }
    }

    public void deleteKey(String key) {
        jedis.del(key);
    }
    public void close() {
        RedisDB.close(jedis);
    }
}
