package com.sxp.patmag.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Author： Jude
 * Date:2019/11/27
 * Time:13:08
 */
@Configuration
public class LoggerConfigration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoggerHandler());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //添加不拦截路径
        registration.excludePathPatterns(
                "/login/login",
                "/**/*.html",
                "/**/*.js" ,
                "/**/*.css"
        );
    }
}
