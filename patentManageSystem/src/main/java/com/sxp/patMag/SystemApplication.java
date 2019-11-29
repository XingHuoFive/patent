package com.sxp.patMag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.sxp.patMag.dao")
@SpringBootApplication
//@EnableTransactionManagement
public class SystemApplication {
        public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}
