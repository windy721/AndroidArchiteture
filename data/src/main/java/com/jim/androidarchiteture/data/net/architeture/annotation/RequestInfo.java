package com.jim.androidarchiteture.data.net.architeture.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JimGong on 2016/5/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestInfo {
    int taskId() default 0;
    String url();
    Class<?> responseType();
}