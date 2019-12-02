package com.sxp.patMag.annotation;

import java.lang.annotation.*;

/**
 * @author： Jude
 * @date:2019/11/25
 * @time:16:06
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface PassToken {
    boolean required() default true;
}