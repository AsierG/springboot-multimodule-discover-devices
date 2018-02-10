package asierg.repositories;

import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.Equipment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersistenceConfig.class)
public class EquipmentRepositoryTest extends EquipmentBaseTest {

    @Test
    public void findAlEquipmentsByIp() {
        List<Equipment> equipmentList = equipmentRepository.findAllByIpAddress(ip);
        Assert.assertTrue(equipmentList.size() == serialNumbers.size());
    }

    @Test
    public void findAllEquipmentsByIpAndSerialNumberIsNotEqualThanGivenSerialNumber() {
        String serialNumber = serialNumbers.get(0);
        List<Equipment> equipmentList = equipmentRepository.findAllByIpAddressAndSerialNumberIsNot(ip, serialNumber);
        Assert.assertTrue(equipmentList.size() == (serialNumbers.size() - 1));
        for (Equipment equipment : equipmentList) {
            assertThat(equipment.getIpAddress()).isEqualTo(ip);
            assertThat(equipment.getSerialNumber()).isNotEqualTo(serialNumber);
        }
    }

}
