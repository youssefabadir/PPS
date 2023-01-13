package com.pps.domain.picture;

import com.pps.controller.domain.category.CategoryRepository;
import com.pps.controller.domain.picture.PictureEntity;
import com.pps.controller.domain.picture.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PictureRepositoryTests {

    @Autowired
    private final PictureRepository pictureRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final TestEntityManager testEntityManager;

    @Test
    public void createPicture() {
        PictureEntity picture = new PictureEntity();
        picture.setName("test.png");
        picture.setStatus("PENDING");
        picture.setDescription("test description");
        picture.setCategoryEntity(categoryRepository.findByName("Nature"));
        picture.setPath("dummy/path/test.png");

        PictureEntity savedPicture = pictureRepository.save(picture);
        PictureEntity existPicture = testEntityManager.find(PictureEntity.class, savedPicture.getId());

        assertThat(existPicture).isEqualTo(savedPicture);
    }
}
