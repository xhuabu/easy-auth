easy-auth

## 简介

easy-auth是一个基于springboot，mybatis, 并和特定权限表结构关联的权限框架，采用GPL开源协议。

此框架可以帮你高效管理管理员与群组，群组与菜单、群组与权限间的相互关系，它已实现以下功能

* 支持自动生成权限URI

* 支持对用户/群组进行增删改查

* 支持对菜单进行增删改查

* 支持关联用户与群组

* 支持关联群组与菜单

* 支持关联群组与权限

* 支持鉴定管理员权限

* 支持配置IP白名单

* 支持自定义用户加密方式


## 用法


### 参考doc/auth.sql文件生成指定的权限表结构

```
mysql –u用户名 –p密码 –D数据库 < auth.sql
```

### 依赖导入

```
添加maven仓库地址： 

maven{
        url "http://maven.xhuabu.com/repository/xhb-public/"
    }

添加依赖
compile group: 'com.xhuabu.source', name: 'easy-auth', version: '1.1.0-20180130.044509-7'
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

### 配置@Controller方法所在包的路径
在application.properties中添加属性:

```
com.xhuabu.source.auth.contollerPackagePath=com.xhuabu.admin.controller
```



### 配置白名单(可选)

在application.properties中添加属性, 多个ip地址用逗号隔开

```
com.xhuabu.source.auth.ipWhiteList=127.0.0.1,10.0.0.49
```


### 配置加密方式(可选)

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

### 鉴权

在需要鉴权的控制器方法上添加注解@Authj, 其中value是权限名，auth决定是否进行鉴权，默认true

容器启动后会自动将权限url加载到内存当中

```
	@Authj(value = "修改支付渠道", auth = true)
    @RequestMapping(value = "/api/admin/pay/channel", method = RequestMethod.POST)
    public YQZResponse apiCreateProduct(@RequestParam(value = "status", required = true) Integer status,
                                        @RequestParam(value = "payWayId", required = true) Integer payWayId) {
        try {
            int ret = payService.updatePayWayStatus(payWayId,status);
            if (ret > 0) {
                return YQZResponse.successResponse();
            }
            return YQZResponse.errorResponse(ResponseCode.ERROR_EDIT_PAY_WAY_STATUS_FAIL, null);
        } catch (Exception e) {
            logger.info("插入合约异常：{}", e);
            return YQZResponse.errorResponse(ResponseCode.ERROR_EDIT_PAY_WAY_STATUS_FAIL, null);
        }
    }
```




