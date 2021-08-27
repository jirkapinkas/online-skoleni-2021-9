package com.example.eshopweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableCaching
@EnableScheduling
@EnableAsync
@SpringBootApplication
// Anotace @SpringBootApplication v sobe schovava:
//@ComponentScan
//@Configuration
//@EnableAutoConfiguration
public class EshopwebApplication {

//    @Scheduled(fixedDelay = 10_000)
//    public void scheduled() {
//        log.info("scheduled is running ...");
//    }

    public static void main(String[] args) {
        SpringApplication.run(EshopwebApplication.class, args);
    }

}
