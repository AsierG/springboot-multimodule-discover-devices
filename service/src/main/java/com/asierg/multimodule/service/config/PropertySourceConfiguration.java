package com.asierg.multimodule.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class PropertySourceConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value = "classpath:service-default.properties")
    static class DefaultProfile {
    }

    @Configuration
    @Profile("dev")
    @PropertySource(value = "classpath:service-dev.properties")
    static class DevProfile {
    }

    @Configuration
    @Profile("prod")
    @PropertySource(value = "classpath:service-prod.properties")
    static class ProdProfile {
    }

}
