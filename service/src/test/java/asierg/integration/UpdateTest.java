package asierg.integration;

import asierg.config.DeviceTestConfig;
import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.service.config.PropertySourceConfiguration;
import com.asierg.multimodule.service.config.RcpConfig;
import com.asierg.multimodule.service.enums.RcpContentType;
import com.asierg.multimodule.service.enums.ReflashResultCode;
import com.asierg.multimodule.service.repositories.FirmwareRepository;
import com.asierg.multimodule.service.services.RcpService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PropertySourceConfiguration.class, RcpConfig.class, DeviceTestConfig.class, PersistenceConfig.class})
@ActiveProfiles("default")
@DataJpaTest
public class UpdateTest {

    @Autowired
    private RcpService rcpService;

    @Autowired
    private FirmwareRepository firmwareRepository;

    @Test
    @Ignore
    public void reflashDevice() {
        try {
            String testIP = "192.168.5.200";

            Path fileLocation10 = Paths.get("./src/test/resources/firmware1");
            Path fileLocation11 = Paths.get("./src/test/resources/firmware2");
            byte[] firmwareData10 = Files.readAllBytes(fileLocation10);
            byte[] firmwareData11 = Files.readAllBytes(fileLocation11);

            String firmwareVersion10 = "3.33.10.36625";
            Firmware firmware10 = new Firmware();
            firmware10.setFirmwareVersion(firmwareVersion10);
            firmware10.setName("firmware 1");
            firmware10.setDescription("description 1");
            firmware10.setImageFile(firmwareData10);
            firmwareRepository.save(firmware10);

            String firmwareVersion11 = "3.33.11.38620";
            Firmware firmware11 = new Firmware();
            firmware11.setFirmwareVersion(firmwareVersion11);
            firmware11.setName("firmware 2");
            firmware11.setDescription("description 2");
            firmware11.setImageFile(firmwareData11);
            firmwareRepository.save(firmware11);

            Firmware firmware = firmwareRepository.findOne(firmwareVersion10);
            ReflashResultCode result = rcpService.reflashDevice(testIP, RcpContentType.REMOTE_CONF, firmware);

            assertEquals(result, ReflashResultCode.OK_RESPONSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
