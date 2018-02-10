package asierg.config;

import com.asierg.multimodule.service.services.RcpService;
import com.asierg.multimodule.service.services.SnmpService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(
        basePackageClasses = {SnmpService.class, RcpService.class}, useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = SnmpService.class),
                @ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = RcpService.class)
        })
public class DeviceTestConfig {

}
