package com.xhuabu.source.common.coder;


import com.xhuabu.source.common.cache.CryptCache;
import com.xhuabu.source.common.tool.Md5;
import com.xhuabu.source.config.JLAuthConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 17/9/1.
 * 密码加密器
 *
 * @CreatedBy lee
 * @Date 17/9/1
 */
@Component
public class PasswordEncoder {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    CryptCache cryptCache;

    /**
     * 采用MD5+salt的方式进行加密
     *
     * @param password 原始密码
     * @param salt     盐
     */
    public static String encode(String password, String salt) {

        //1 拼接salt
        String sourcePwd = password + "{" + salt + "}";

        //2 MD5加密
        return Md5.md5(sourcePwd);

    }


    /**
     * 加密
     *
     * @param password 原始密码
     * @param salt     盐
     */
    public String crypt(String password, String salt) {

//        Set<Class<?>> classSet = annotationManager.getConfigurationClasses();
//
//        //找不到加密配置则使用默认加密
//        if (classSet == null){
//            return PasswordEncoder.defaulCrypt(password, salt);
//        }
//
//        JLAuthConfiguration config = null;
//        try {
//            for ( Class aClass : classSet){
//                 config = (JLAuthConfiguration)aClass.newInstance();
//                if (config != null){
//                    break;
//                }
//            }
//        }catch (Exception e){
//            logger.error("实例化配置类失败:{}", e);
//        }

        JLAuthConfiguration config = cryptCache.getPasswordEncoder();
        //如果添加了配置类，则按照配置类的方式加密
        if (config == null) {
            return PasswordEncoder.encode(password, salt);
        } else {
            return config.cryptPassword(password, salt);
        }

    }


}
