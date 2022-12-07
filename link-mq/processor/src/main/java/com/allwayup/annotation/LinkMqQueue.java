package com.allwayup.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface LinkMqQueue {

    /**
     * 发出去的mq队列名
     */
    String out();

    /**
     * 收回来的mq队列名
     */
    String in();

    /**
     * 接收超时时间(毫秒),超过时间抛出异常
     * 默认5000毫秒
     */
    int inTimeOut() default 5000;
}
