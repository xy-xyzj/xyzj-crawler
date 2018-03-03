# my-crawler
    java语言实现公开网页数据采集
    

## 使用说明
    //克隆项目
      git clone https://github.com/lyyMyGitHub/my-crawler.git
    
    //maven导入
    
    //修改数据库地址
        mysql.url=jdbc:mysql://localhost/crawler?characterEncoding=utf8&useSSL=false
        mysql.username=xxx
        mysql.password=xxx
        
        //保存到mysql goods_drugs_detail_2对应数据库表名称
        ServiceImpl goodsPOServiceImpl = new ServiceImpl();
        goodsPOServiceImpl.add("goods_drugs_detail_2", goodsPO);    
            
    
    //ip代理 用到redis 
        jedis.addr=127.0.0.1
        jedis.port=6379
        jedis.passwd=
        
        运行SaveFromHtml类即可
        
                
	    // 使用代理IP
        MyRedis redis = new MyRedis();
        //从redis数据库中随机拿出一个IP
        IPMessage ipMessage = redis.getIPByList();
        redis.close();
        String htmlSource = MyHttpResponse.getHtml(goodsPO.getWebUrl(), ipMessage.getIPAddress(), ipMessage.getIPPort());
    
    //交流学习 联系qq:719882551        

