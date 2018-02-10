package asierg.repositories;

import com.asierg.multimodule.domain.*;
import com.asierg.multimodule.service.repositories.*;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EquipmentBaseTest {

    @Autowired
    protected EquipmentRepository equipmentRepository;

    @Autowired
    protected FamilyRepository familyRepository;

    @Autowired
    protected FirmwareRepository firmwareRepository;

    @Autowired
    protected FirmwareModelRepository firmwareModelRepository;

    @Autowired
    protected ModelRepository modelRepository;

    protected final String productIdentifier = "3EMRU2E28Q2D4102";
    protected final String firmwareVersion = "3.33.10.36625";
    protected final String ip = "192.168.5.115";

    List<String> serialNumbers = new ArrayList<String>() {{
        add("123456100");
        add("123456200");
        add("123456300");
        add("123456400");
        add("123456500");
        add("123456600");
        add("123456700");
        add("123456800");
    }};

    @Before
    public void setUp() throws Exception {
        Family family = new Family();
        family.setName("family1");
        family.setDescription("family1 Description");
        family = familyRepository.save(family);

        Model model = new Model();
        model.setProductIdentifier(productIdentifier);
        model.setName("Router Industrial 123");
        model.setDescription("Router industrial 102WR");
        model.setFamily(family);
        model = modelRepository.save(model);

        Firmware firmware = new Firmware();
        firmware.setFirmwareVersion(firmwareVersion);
        firmware.setName("firmware 1");
        firmware.setDescription("description 1");
        firmware.setImageFile(null);
        firmware = firmwareRepository.save(firmware);

        FirmwareModel firmwareModel = new FirmwareModel();
        firmwareModel.setStatusSchema("status Schema 1");
        firmwareModel.setConfigSchema("config schema 1");
        firmwareModel.setFirmware(firmware);
        firmwareModel.setModel(model);
        firmwareModelRepository.save(firmwareModel);

        for (String serialNumber : serialNumbers) {
            equipmentRepository.save(new Equipment(serialNumber, ip, model, firmware));
        }

    }

}
