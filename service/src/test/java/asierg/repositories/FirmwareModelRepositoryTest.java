package asierg.repositories;

import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.Family;
import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.domain.FirmwareModel;
import com.asierg.multimodule.domain.Model;
import com.asierg.multimodule.service.repositories.FamilyRepository;
import com.asierg.multimodule.service.repositories.FirmwareModelRepository;
import com.asierg.multimodule.service.repositories.FirmwareRepository;
import com.asierg.multimodule.service.repositories.ModelRepository;
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
public class FirmwareModelRepositoryTest {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private FirmwareModelRepository firmwareModelRepository;

    @Autowired
    private FirmwareRepository firmwareRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Test
    public void find() {
        Firmware firmware = new Firmware();
        firmware.setFirmwareVersion("3.33.10.36625");
        firmware.setName("firmware 1");
        firmware.setDescription("description 1");
        firmware.setImageFile(null);
        firmware = firmwareRepository.save(firmware);

        Family family = new Family();
        family.setName("family1");
        family.setDescription("family1 Description");
        family = familyRepository.save(family);

        Model model = new Model();
        model.setProductIdentifier("3EMRU2E28Q2D4102");
        model.setName("Router Industrial 123");
        model.setDescription("Router industrial 102WR");
        model.setFamily(family);
        model = modelRepository.save(model);

        FirmwareModel firmwareModel = new FirmwareModel();
        firmwareModel.setStatusSchema("status Schema 1");
        firmwareModel.setConfigSchema("config schema 1");
        firmwareModel.setFirmware(firmware);
        firmwareModel.setModel(model);
        FirmwareModel savedFirmwareModel = firmwareModelRepository.save(firmwareModel);

        FirmwareModel foundFirmwareModel = firmwareModelRepository.findOne(savedFirmwareModel.getId());
        assertThat(foundFirmwareModel.getStatusSchema()).isEqualTo(firmwareModel.getStatusSchema());
        assertThat(foundFirmwareModel.getConfigSchema()).isEqualTo(firmwareModel.getConfigSchema());
        assertThat(foundFirmwareModel.getFirmware()).isEqualTo(firmwareModel.getFirmware());
        assertThat(foundFirmwareModel.getModel()).isEqualTo(firmwareModel.getModel());
    }

}
