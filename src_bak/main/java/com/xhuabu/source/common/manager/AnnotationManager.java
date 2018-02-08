package com.xhuabu.source.common.manager;


import com.xhuabu.source.annotation.config.JLAuthConfig;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * Created by lee on 17/12/21.
 * 类相关工具
 *
 * @CreatedBy lee
 * @Date 17/12/21
 */
@Component
public class AnnotationManager {


    /**
     *  获取添加了@JLAuthConfig的类
     */
    public Set<Class<?>> getConfigurationClasses(){

        Reflections reflections = new Reflections("");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(JLAuthConfig.class);
        return annotated;
    }




}
