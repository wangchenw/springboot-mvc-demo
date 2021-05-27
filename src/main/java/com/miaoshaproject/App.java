package com.miaoshaproject;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

//第一步:测试web项目运行

//@EnableAutoConfiguration //springboot会帮助我们启动内嵌的tomcat,并且加载默认的配置

//这一步将com.miaoshaproject目录下的包，搜索service component repository 等 spring特定的注解
//@Component, @Service, @Controller, @Repository是spring注解，注解后可以被spring框架所扫描并注入到spring容器来进行管理
//@Component是通用注解，其他三个注解是这个注解的拓展，并且具有了特定的功能
//@Repository注解在持久层中，具有将数据库操作抛出的原生异常翻译转化为spring的持久层异常的功能。
//@Controller层是spring-mvc的注解，具有将请求进行转发，重定向的功能。
//@Service层是业务逻辑层注解，这个注解只是标注该类处于业务逻辑层。
//        用这些注解对应用进行分层之后，就能将请求处理，义务逻辑处理，数据库操作处理分离出来，为代码解耦，也方便了以后项目的维护和开发。
//通过controller/requestmapping 映射请求 省略了Springmvc配置servlet/web.xml等

/*
补充: springboot 默认tomcat的端口是8080

我们可以再resources下新建application.properties文件
                        server.port=8090  即可更改默认端口号

      springboot会默认搜索resources目录下application.properties文件,根据key-value加载配置信息
 */

//第二步:测试数据库mybatis
//首先导入依赖
@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})
@MapperScan("com.miaoshaproject.dao")
@RestController
public class App 
{
    @Autowired
    UserDOMapper userDOMapper;


    @RequestMapping("/")
    public String home() {
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO == null) {
            return "用户对象不存在";
        } else {
            return userDO.getName();
        }

    }

    public static void main( String[] args )
    {
         SpringApplication.run(App.class,args);
    }
}
