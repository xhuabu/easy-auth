package com.xhuabu.source.annotation.authorization;

import java.lang.annotation.*;

/**
 * Created by jdk on 17/12/22.
 * 表记该方法受到Authj 监管
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authj {

    /**
     * value 其实是标记该接口的唯一标识
     * 接口名
     * @return
     */
    String value();

    /**
     * 标记该接口是否需要鉴权
     * 默认是
     * @return
     */
    boolean auth() default true;
}
