package com.pps.domain.category;

import com.pps.controller.domain.category.CategoryEntity;
import com.pps.controller.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryRepositoryTests {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Test
    public void findCategory() {
        String category = "Nature";

        CategoryEntity categoryEntity = categoryRepository.findByName(category);

        assertThat(categoryEntity.getName()).isEqualTo(category);
    }
}
