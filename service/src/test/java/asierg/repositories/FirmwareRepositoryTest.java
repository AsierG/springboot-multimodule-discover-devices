package asierg.repositories;

import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.service.repositories.FirmwareRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersistenceConfig.class)
public class FirmwareRepositoryTest {

    @Autowired
    private FirmwareRepository firmwareRepository;

    @Test
    public void prueba() {
        String firmwareVersion = "3.33.10.36625";
        Firmware firmware = new Firmware();
        firmware.setFirmwareVersion(firmwareVersion);
        firmware.setName("firmware 1");
        firmware.setDescription("description 1");
        firmware.setImageFile(null);
        Firmware savedFirmware = firmwareRepository.save(firmware);
        Firmware foundFirmware = firmwareRepository.findOne(firmwareVersion);

        assertThat(foundFirmware.getName()).isEqualTo(savedFirmware.getName());
        assertThat(foundFirmware.getDescription()).isEqualTo(savedFirmware.getDescription());
        assertThat(foundFirmware.getImageFile()).isEqualTo(savedFirmware.getImageFile());
    }

}
