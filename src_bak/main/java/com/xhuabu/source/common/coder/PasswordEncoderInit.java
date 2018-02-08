package com.xhuabu.source.common.coder;

import com.xhuabu.source.common.cache.CryptCache;
import com.xhuabu.source.common.manager.AnnotationManager;
import com.xhuabu.source.config.JLAuthConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by jdk on 17/12/22.
 * Authj初始化
 */
@Component
public class PasswordEncoderInit implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AnnotationManager annotationManager;

    @Autowired
    CryptCache cryptCache;

    private JLAuthConfiguration getPasswordEncoder() {

        // 获取加密类
        Set<Class<?>> classSet = annotationManager.getConfigurationClasses();

        JLAuthConfiguration config = null;
        try {
            for ( Class aClass : classSet){
                config = (JLAuthConfiguration)aClass.newInstance();
                if (config != null){
                    cryptCache.setPasswordEncoder(config);
                    break;
                }
            }

            return config;
        }catch (Exception e){
            logger.error("实例化配置类失败:{}", e);
        }

        return null;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        getPasswordEncoder();
    }
}
