package com.sxp.patMag.annotation;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:19:32
 */

import java.lang.annotation.*;

/**
 * 自定义注解类
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface Monitor {
    String value() default "";
}