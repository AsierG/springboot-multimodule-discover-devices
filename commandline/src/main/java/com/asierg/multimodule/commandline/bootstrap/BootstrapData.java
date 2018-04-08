package com.asierg.multimodule.commandline.bootstrap;

import com.asierg.multimodule.domain.*;
import com.asierg.multimodule.service.repositories.FamilyRepository;
import com.asierg.multimodule.service.repositories.FirmwareModelRepository;
import com.asierg.multimodule.service.repositories.FirmwareRepository;
import com.asierg.multimodule.service.repositories.ModelRepository;
import com.asierg.multimodule.service.services.EquipmentService;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapData.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private FamilyRepository familyRepository;
    private FirmwareRepository firmwareRepository;
    private FirmwareModelRepository firmwareModelRepository;
    private ModelRepository modelRepository;

    @Autowired
    public BootstrapData(FamilyRepository familyRepository,
                         FirmwareRepository firmwareRepository,
                         FirmwareModelRepository firmwareModelRepository,
                         ModelRepository modelRepository) {
        this.familyRepository = familyRepository;
        this.firmwareRepository = firmwareRepository;
        this.firmwareModelRepository = firmwareModelRepository;
        this.modelRepository = modelRepository;
    }


    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!activeProfile.equals("prod")) {
            loadMasterData();
        }
    }

    @Transactional
    public void loadMasterData() {

        logger.info("Loading data...");

        List<Family> familyList = IteratorUtils.toList(familyRepository.findAll().iterator());
        List<Model> modelList = IteratorUtils.toList(modelRepository.findAll().iterator());
        List<Firmware> firmwareList = IteratorUtils.toList(firmwareRepository.findAll().iterator());
        List<FirmwareModel> firmwareModelList = IteratorUtils.toList(firmwareModelRepository.findAll().iterator());

        if (CollectionUtils.isEmpty(familyList) && CollectionUtils.isEmpty(modelList)
                && CollectionUtils.isEmpty(firmwareList) && CollectionUtils.isEmpty(firmwareModelList)) {

            logger.info("start");

            Family family1 = new Family();
            family1.setName("family1");
            family1.setDescription("family1 Description");

            Family family2 = new Family();
            family2.setName("family2");
            family2.setDescription("family2 Description");

            family1 = familyRepository.save(family1);
            family2 = familyRepository.save(family2);

            Model model1 = new Model();
            model1.setProductIdentifier("3EMRU2E28Q2D4102");
            model1.setName("Device 123");
            model1.setDescription("Device 102WR");
            model1.setFamily(family1);
            model1 = modelRepository.save(model1);

            Model model2 = new Model();
            model2.setProductIdentifier("3EMRU2E284222");
            model2.setName("Device  456");
            model2.setDescription("Device 222BK");
            model2.setFamily(family2);
            model2 = modelRepository.save(model2);

            Model model3 = new Model();
            model3.setProductIdentifier("3EMRU2EQ2D4333");
            model3.setName("Device 789");
            model3.setDescription("Device 333XS");
            model3.setFamily(family1);
            modelRepository.save(model3);

            byte[] firmwareData10 = null;
            byte[] firmwareData11 = null;


            Firmware firmware1 = new Firmware();
            firmware1.setFirmwareVersion("5.12.10.325");
            firmware1.setName("firmware 1");
            firmware1.setDescription("description 1");
            firmware1.setImageFile(firmwareData10);
            firmware1 = firmwareRepository.save(firmware1);

            Firmware firmware2 = new Firmware();
            firmware2.setFirmwareVersion("6.10.11.38620");
            firmware2.setName("firmware 2");
            firmware2.setDescription("description 2");
            firmware2.setImageFile(firmwareData11);
            firmware2 = firmwareRepository.save(firmware2);

            FirmwareModel firmwareModel1 = new FirmwareModel();
            firmwareModel1.setStatusSchema("status Schema 1");
            firmwareModel1.setConfigSchema("config schema 1");
            firmwareModel1.setFirmware(firmware1);
            firmwareModel1.setModel(model1);
            firmwareModelRepository.save(firmwareModel1);

            FirmwareModel firmwareModel2 = new FirmwareModel();
            firmwareModel2.setStatusSchema("status Schema 2");
            firmwareModel2.setConfigSchema("config schema 2");
            firmwareModel2.setFirmware(firmware2);
            firmwareModel2.setModel(model2);
            firmwareModelRepository.save(firmwareModel2);

            FirmwareModel firmwareModel3 = new FirmwareModel();
            firmwareModel3.setStatusSchema("status Schema 3");
            firmwareModel3.setConfigSchema("config schema 3");
            firmwareModel3.setFirmware(firmware1);
            firmwareModel3.setModel(model2);
            firmwareModelRepository.save(firmwareModel3);

        }


    }


}
