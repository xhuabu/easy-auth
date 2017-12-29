jlauth

## 简介

JLAuth是一个基于springboot，mybatis, 并和特定权限表结构关联的权限框架，它实现了以下功能：

	* 已经帮你实现了权限相关表对应的业务模型和操作接口
	* 提供了权限操作相关业务操作类
	* 通过封装类快速实现授权和解除授权
	* 通过注解实现鉴权
	* 可以配置权限白名单和登录加密方式

你可以使用JLAuth的业务操作类实现大多数业务需求，也可以基于JLAuth提供的dao层实现自定义的业务操作类


## 用法

### 依赖导入

```
compile group: 'com.xhuabu.source', name: 'JLAuth', version: '1.0.0-20171226.023800-10'
```

### 装配spring bean

在启动类中：

1 通过注解@SpringBootApplication添加包“com.xhuabu.source”

2 通过注解@MapperScan添加包”com.xhuabu.source.dao”

```
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.xhuabu.admin", "com.xhuabu.source"})
@MapperScan(value = {"com.xhuabu.admin.mapper", "com.xhuabu.admin.generator.mapper", "com.xhuabu.source.dao"})
public class GalaxyApplication {



    public static  ApplicationContext appCtx;

    /**
     * Springboot应用程序入口
     */
    public static void main(String[] args) {
        appCtx = SpringApplication.run(GalaxyApplication.class, args);
    }


}
```


### 配置白名单(可选)

在application.properties中添加属性, 多个ip地址用逗号隔开

```
com.xhuabu.source.auth.ipWhiteList=127.0.0.1,10.0.0.49
```





###配置加密方式(可选)

1 配置类需要@JLAuthConfig注解
2 配置类需要继承JLAuthConfiguration实现抽象方法

```
@JLAuthConfig
public class AuthConfig extends JLAuthConfiguration {


    /**
     *  自定义加密方式
     */
    @Override
    public String cryptPassword(String password, String salt) {

        //在这里实现加密逻辑


        return null;
    }
}
```


### 注册鉴权拦截器

继承WebMvcConfigurerAdapter并注册权限拦截器

```
@Configuration
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor);
        super.addInterceptors(registry);
    }

}
```


### 登录授权

登录成功后，需要获取admin的id并通过JLAuthManager完成授权

```
 authManager.grant(request, admin.getId());
```

### 退出登录解除授权

```
authManager.delGrant(request);
```
