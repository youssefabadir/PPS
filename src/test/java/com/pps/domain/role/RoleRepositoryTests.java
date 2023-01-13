package com.pps.domain.role;

import static org.assertj.core.api.Assertions.assertThat;

import com.pps.controller.domain.role.RoleEntity;
import com.pps.controller.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleRepositoryTests {

    @Autowired
    private final RoleRepository roleRepository;

    @Test
    public void findRole() {
        String role = "ROLE_ADMIN";

        RoleEntity roleEntity = roleRepository.findByName(role);

        assertThat(roleEntity.getName()).isEqualTo(role);
    }
}
