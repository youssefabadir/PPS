package com.pps.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.pps.controller.domain.role.RoleRepository;
import com.pps.controller.domain.user.UserEntity;
import com.pps.controller.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryTests {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final TestEntityManager testEntityManager;


    @Test
    public void createUser() {
        UserEntity user = new UserEntity();
        user.setPassword("joe");
        user.setEmail("joe@mail.com");
        user.setRoleEntity(roleRepository.findByName("ROLE_USER"));

        UserEntity savedUser = userRepository.save(user);
        UserEntity existUser = testEntityManager.find(UserEntity.class, savedUser.getId());

        assertThat(existUser).isEqualTo(user);
    }

    @Test
    public void getUserByEmail() {
        UserEntity user = new UserEntity();
        user.setPassword("joe");
        user.setEmail("joe@mail.com");
        user.setRoleEntity(roleRepository.findByName("ROLE_USER"));

        UserEntity savedUsed = userRepository.save(user);
        UserEntity existUser = userRepository.findByEmail(user.getEmail());

        assertThat(existUser).isEqualTo(savedUsed);
    }
}
