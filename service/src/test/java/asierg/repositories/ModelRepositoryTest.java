package asierg.repositories;

import asierg.config.PersistenceConfig;
import com.asierg.multimodule.domain.Family;
import com.asierg.multimodule.domain.Model;
import com.asierg.multimodule.service.repositories.FamilyRepository;
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
public class ModelRepositoryTest {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Test
    public void find() {
        Family family = new Family();
        family.setName("family1");
        family.setDescription("description1");
        family = familyRepository.save(family);

        Model model = new Model();
        model.setProductIdentifier("3EMRU2E28Q2D4102");
        model.setName("Router Industrial 123");
        model.setDescription("Router industrial 102WR");
        model.setFamily(family);
        Model savedModel = modelRepository.save(model);

        Model foundModel = modelRepository.findOne(savedModel.getProductIdentifier());
        assertThat(foundModel.getName()).isEqualTo(savedModel.getName());
        assertThat(foundModel.getDescription()).isEqualTo(savedModel.getDescription());
        assertThat(foundModel.getFamily()).isEqualTo(savedModel.getFamily());
    }

}
