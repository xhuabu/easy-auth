package com.xhuabu.source.annotation.authorization;

import java.lang.annotation.*;


@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

    public String adminIdFieldName() default "adminId";

}