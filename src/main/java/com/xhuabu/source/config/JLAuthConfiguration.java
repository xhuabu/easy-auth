package com.xhuabu.source.config;

/**
 * Created by lee on 17/12/20.
 * 权限组件相关配置
 *
 * @CreatedBy lee
 * @Date 17/12/20
 */
public abstract class JLAuthConfiguration {
    

    /**
     *  自定义加密的方法
     *  @param password 原始密码
     *  @param salt 盐
     *  @return 返回null则采用默认加密方式： md5(password + {salt})
     */
    public abstract String cryptPassword(String password, String salt);

}
