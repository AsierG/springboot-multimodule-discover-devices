package asierg.integration;

import asierg.config.DeviceTestConfig;
import com.asierg.multimodule.service.config.PropertySourceConfiguration;
import com.asierg.multimodule.service.config.RcpConfig;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.enums.RcpContentType;
import com.asierg.multimodule.service.services.RcpService;
import com.asierg.multimodule.service.services.SnmpService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PropertySourceConfiguration.class, RcpConfig.class, DeviceTestConfig.class})
@ActiveProfiles("default")
public class DeviceTest {

    @Autowired
    private SnmpService snmpService;

    @Autowired
    private RcpService rcpService;

    @Test
    @Ignore
    public void getDevice() {
        String testIP = "192.168.5.15";
        DeviceDTO deviceDTOSnmp = snmpService.getDevice(testIP);
        DeviceDTO deviceDTORcp = rcpService.getDeviceData(testIP, RcpContentType.REMOTE_CONF);
        assertEquals(deviceDTOSnmp.getProductIdentifier(), deviceDTORcp.getProductIdentifier());
        assertEquals(deviceDTOSnmp.getFirmwareVersion(), deviceDTORcp.getFirmwareVersion());
        assertEquals(deviceDTOSnmp.getSerialNumber(), deviceDTORcp.getSerialNumber());

    }

}
