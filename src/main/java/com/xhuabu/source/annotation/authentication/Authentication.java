package com.xhuabu.source.annotation.authentication;

import java.lang.annotation.*;


@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {
}