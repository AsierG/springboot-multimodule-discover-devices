package asierg.repositories;

import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.DiscoverError;
import com.asierg.multimodule.domain.Equipment;
import com.asierg.multimodule.domain.enums.ErrorType;
import com.asierg.multimodule.service.repositories.DiscoverErrorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersistenceConfig.class)
public class ErrorRepositoryTest extends EquipmentBaseTest {

    @Autowired
    private DiscoverErrorRepository discoverErrorRepository;

    @Test
    public void findByEquipmentSerialNumber() {
        String equipmentSerialNumber = serialNumbers.get(0);
        createDiscoverErrors(equipmentSerialNumber);

        Equipment foundEquipment = equipmentRepository.findOne(equipmentSerialNumber);
        List<DiscoverError> discoverErrorList = discoverErrorRepository.
                findAllByEquipmentSerialNumber(equipmentSerialNumber);

        Assert.assertTrue(foundEquipment.getDiscoverErrors().size() == 2);
        Assert.assertTrue(foundEquipment.getDiscoverErrors().size() == discoverErrorList.size());

    }

    @Test
    public void countByEquipmentSerialNumber() {
        String equipmentSerialNumber = serialNumbers.get(0);
        createDiscoverErrors(equipmentSerialNumber);

        Equipment foundEquipment = equipmentRepository.findOne(equipmentSerialNumber);
        Long count = discoverErrorRepository.countByEquipmentSerialNumber(equipmentSerialNumber);

        Assert.assertTrue(foundEquipment.getDiscoverErrors().size() == 2);
        Assert.assertTrue(foundEquipment.getDiscoverErrors().size() == count);

    }

    @Test
    public void findByEquipmentSerialNumberAndErrorType() {
        String equipmentSerialNumber = serialNumbers.get(0);
        createDiscoverErrors(equipmentSerialNumber);

        Equipment foundEquipment = equipmentRepository.findOne(equipmentSerialNumber);

        List<DiscoverError> discoverFirmwareErrorList = discoverErrorRepository.
                findAllByEquipmentSerialNumberAndErrorType(equipmentSerialNumber, ErrorType.FIRMWARE);
        List<DiscoverError> discoverModelErrorList = discoverErrorRepository.
                findAllByEquipmentSerialNumberAndErrorType(equipmentSerialNumber, ErrorType.MODEL);

        Assert.assertTrue(discoverFirmwareErrorList.size() == 1);
        Assert.assertTrue(discoverModelErrorList.size() == 1);
        Assert.assertTrue(foundEquipment.getDiscoverErrors().size() ==
                (discoverFirmwareErrorList.size() + discoverFirmwareErrorList.size()));

    }

    private void createDiscoverErrors(String equipmentSerialNumber) {
        Equipment equipment = equipmentRepository.findOne(equipmentSerialNumber);

        DiscoverError discoverErrorFirmware = new DiscoverError(equipment, ErrorType.FIRMWARE);
        DiscoverError discoverErrorModel = new DiscoverError(equipment, ErrorType.MODEL);
        equipment.getDiscoverErrors().add(discoverErrorFirmware);
        equipment.getDiscoverErrors().add(discoverErrorModel);
        equipmentRepository.save(equipment);
    }


}
