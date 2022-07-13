package com.example.fxpro.test.project;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "com.example.fxpro.test.project.*")
@EnableScheduling
public class FxProTestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxProTestProjectApplication.class, args);
    }

}
