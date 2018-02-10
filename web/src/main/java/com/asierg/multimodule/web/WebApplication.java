package com.asierg.multimodule.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.asierg.multimodule.service"})
@EntityScan(basePackages = {"com.asierg.multimodule.domain"})
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.asierg.multimodule.service",
        "com.asierg.multimodule.domain",
        "com.asierg.multimodule.web"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }

}
