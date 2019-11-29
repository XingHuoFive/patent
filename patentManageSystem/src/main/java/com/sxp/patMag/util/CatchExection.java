package com.sxp.patMag.util;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: Andy
 * @time: 2019/11/28 10:38
 */
@ControllerAdvice
public class CatchExection {

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public GeneralResult catchExection() {
        return GeneralResult.build(1, "登陆不成功");
    }

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {

        EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer = (container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/e404"));
        };
        return embeddedServletContainerCustomizer;
    }
}
