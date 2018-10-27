# my-crawler

## 一、项目介绍

    爬虫?
    算不上，就是一个网页信息采集器。
    java语言实现，会java，开箱即用。
    想学习一下具体实现,和探讨一下技术的小伙伴，也可以跟我一起讨论。
    



## 二、使用帮助

```shell
#第一步:克隆项目
git clone https://github.com/lyyMyGitHub/my-crawler.git

#第二步:创建数据库、数据表

修改数据库地址
    mysql.url=jdbc:mysql://localhost/crawler?characterEncoding=utf8&useSSL=false
    mysql.username=xxx
    mysql.password=xxx
    
保存到mysql goods_drugs_detail_2对应数据库表名称
    ServiceImpl goodsPOServiceImpl = new ServiceImpl();
    goodsPOServiceImpl.add("goods_drugs_detail_2", goods);    
```


​    
    ip代理 用到redis 
        jedis.addr=127.0.0.1
        jedis.port=6379
        jedis.passwd=
        
        运行SaveFromHtml类即可


​                
	使用代理IP
	    MyRedis redis = new MyRedis();
	    //从redis数据库中随机拿出一个IP
	    IPMessage ipMessage = redis.getIPByList();
	    redis.close();
	    String htmlSource = MyHttpResponse.getHtml(goods.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());
	
	交流学习 
	    联系qq:719882551        

