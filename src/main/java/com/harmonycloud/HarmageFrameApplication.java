package com.harmonycloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
@MapperScan("com/harmonycloud/dao")
public class HarmageFrameApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarmageFrameApplication.class, args);
    }

}
