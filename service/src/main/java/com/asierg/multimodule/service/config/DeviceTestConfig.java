package com.asierg.multimodule.service.config;

import com.asierg.multimodule.service.services.impl.EquipmentServiceImpl;
import com.asierg.multimodule.service.services.impl.ProcessServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.asierg.multimodule.service.services"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EquipmentServiceImpl.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ProcessServiceImpl.class)
        })
public class DeviceTestConfig {

    @Configuration
    @PropertySource(value = "classpath:service-default.properties")
    static class DefaultProfile {
    }

}
