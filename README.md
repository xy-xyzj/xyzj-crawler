# my-crawler

## 一、项目介绍

    爬虫?
    算不上，就是一个网页信息采集器。
    java语言实现，会java，开箱即用。
    想学习一下具体实现,和探讨一下技术的小伙伴，也可以跟我一起讨论。
    交流学习--联系lyy qq:719882551。 
    come on,fork you！




## 二、使用帮助

```shell
#第一步:克隆项目 导入idea
git clone https://github.com/lyyMyGitHub/my-crawler.git

#第二步:创建数据库、数据表
创建语句 goods.sql

#第三步:修改数据库配置
conf.properties
mysql.url=jdbc:mysql://localhost/crawler?characterEncoding=utf8&useSSL=false
mysql.username=xxx
mysql.password=xxx
    

#第四步:运行
--默认实现
1)DefaultSpiderRuleTest 

--58单页面
2）DoRule58

--58分页多页面 开启多线程爬取
3）DoCrawler58


```

   

## 三、爬取你想要的网站

```java

public static void main(String[] args) {

        //工厂取得默认实例
        ISpiderRule spiderRule = new SpiderRuleFactory().getInstance();

        //封装参数
        HashMap<String, Object> params = new HashMap<>();
    
    	//指定网站
        params.put("webUrl", "http://xxx.com");
    	//匹配需要的内容
        params.put("regexPattern", "前缀(.*?)后缀);
        spiderRule.runSpider(params);
    }
```

​        

## 四、模拟登陆

```shell
#其实已实现了。可以自己看看
params.put("headInfos",Map<String,String>);
```



## 五、IP代理

```shell
#已实现。看代码。后面写个说明
```

