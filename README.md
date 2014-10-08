# my-finances

[my-finances](https://github.com/luowei/my-finances)是一个基础的采用spring mvc的maven web骨架项目，
已配置好了`spring-data-jpa`，和`spring jdbcTeimplate`的自定义的实现。

-------------------------------------------------------------

1. 采用spring mvc框架;
2. 也有对spring jdbctemplate的应用;


##后台菜单链接
[首页](http://localhost:8080/my-finances)http://localhost:8080/my-finances;

[消费管理](http://localhost:8080/my-finances/finance/listDaytip)http://localhost:8080/my-finances/finance/listDaytip;

[消费明细管理](http://localhost:8080/my-finances/finance/listDaytip?tableName=daytip_detail)http://localhost:8080/my-finances/finance/listDaytip?tableName=daytip_detail

[帐号管理](http://localhost:8080/my-finances/account/list)http://localhost:8080/my-finances/account/list

[正则列表](http://localhost:8080/my-finances/regex/list)http://localhost:8080/my-finances/regex/list

[其他工具](http://localhost:8080/my-finances/other/toTools)http://localhost:8080/my-finances/other/toTools

[清除InnerCache](http://localhost:8080/my-finances/cache/clear)http://localhost:8080/my-finances/cache/clear



在`com.rootls.common`中是一些常用的类;

**注意**：打包发布时要i注意修改`dataSource.properties`;

> 例如：百度云引擎BAE jdbc.url=jdbc:mysql://sqld.duapp.com:4050/mBdnxDHLrxXOcdhZNeWs

###运行构建项目:
- 打成war包后放到webapps目录中
* intellij 截图: (前面加个感叹号就成了图片链接)
[my-finances运行效果](https://raw.github.com/luowei/my-finances/master/doc/img/main.png)



- [我的博客](http://blog.163.com/luowei505050@126):http://blog.163.com/luowei505050@126
- [论坛](http://rootls.com):http://rootls.com

- create with help of : [prose](http://prose.io):http://prose.io


**视频讲解**

[my-finances示例讲解](http://www.tudou.com/programs/view/yu3ngrW7N_A)
