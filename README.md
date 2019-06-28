# xyzj-crawler

## 一、项目介绍

    爬虫? no no no
    就是一个网页信息采集器。
    
    java语言实现，会java，开箱即用。
    
    想学习一下具体实现,和探讨一下技术的小伙伴，也可以跟我团队一起讨论。
    交流学习--
    联系lyy qq:719882551。 



### 商务合作

有偿提供公开资源爬取，有需求的朋友请加微信。

![](.github/my.jpg)




## 二、使用帮助

### 2-1 页面有用信息提取

```shell
#第一步:克隆项目 导入idea
git clone https://github.com/xy-xyzj/xyzj-crawler

#第二步:创建数据库、数据表
创建语句 goods.sql

#第三步:修改数据库配置
conf.properties
mysql.url=jdbc:mysql://localhost/crawler?characterEncoding=utf8&useSSL=false
mysql.username=xxx
mysql.password=xxx
    

#第四步:熟悉代码
--默认实现
1)DefaultSpiderRuleTest 

--58单页面
2）DoRule58

--58分页多页面 开启多线程爬取
3）DoCrawler58

```

   

### 2-2 m3u8视频下载

```shell
--m3u8规则实现类
1)com.xyzj.crawler.framework.factory.M3u8SpiderRule

--m3u8下载实例
2）com.xyzj.crawler.spidertask.dorule.DoRule51Cto

```



## 三、爬取你想要的网站

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



