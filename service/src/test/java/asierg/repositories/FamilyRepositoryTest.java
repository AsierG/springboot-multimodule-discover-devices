package asierg.repositories;

import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.Family;
import com.asierg.multimodule.service.repositories.FamilyRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersistenceConfig.class)
public class FamilyRepositoryTest {

    @Autowired
    private FamilyRepository familyRepository;

    @Test
    public void findOne() {
        // given
        Family family1 = new Family();
        family1.setName("family1");
        family1.setDescription("description1");

        Family savedFamily = familyRepository.save(family1);
        Family foundFamily = familyRepository.findOne(savedFamily.getId());

        // then
        assertThat(foundFamily.getName()).isEqualTo(family1.getName());
        assertThat(foundFamily.getDescription()).isEqualTo(family1.getDescription());
    }

    @Test
    public void findAll() {

        Family family1 = new Family();
        family1.setName("family1");
        family1.setDescription("description1");

        Family family2 = new Family();
        family2.setName("family2");
        family2.setDescription("description2");

        familyRepository.save(family1);
        familyRepository.save(family2);
        List<Family> familyList = IteratorUtils.toList(familyRepository.findAll().iterator());

        assert familyList.size() == 2;
    }

}
