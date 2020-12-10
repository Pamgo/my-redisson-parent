package com.okay.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyCacheable {
    /**缓存名称*/
    String cacheName() default "mycache";
    /**缓存key值*/
    String[] key();
    /**有效期*/
    long expire() default 1L;
    /**有效期时间单位*/
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
