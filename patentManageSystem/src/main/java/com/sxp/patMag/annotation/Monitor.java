package com.sxp.patMag.annotation;

/**
 * @author： Jude
 * @date:2019/11/20
 * @time:19:32
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