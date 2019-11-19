package com.sxp.patMag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sxp.patMag.dao")
@SpringBootApplication
public class SystemApplication {
        public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}
