package es.upm.miw.devops.infrastructure.data.daos;

import es.upm.miw.devops.infrastructure.data.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByMobile() {
        User user = User.builder()
                .mobile("600111222")
                .name("Test")
                .familyName("User")
                .active(true)
                .build();
        this.userRepository.save(user);

        Optional<User> foundUser = this.userRepository.findByMobile("600111222");
        assertTrue(foundUser.isPresent());
    }

    @Test
    void testExistsByMobile() {
        User user = User.builder()
                .mobile("600333444")
                .name("Test2")
                .familyName("User2")
                .active(true)
                .build();
        this.userRepository.save(user);

        assertTrue(this.userRepository.existsByMobile("600333444"));
        assertFalse(this.userRepository.existsByMobile("999999999"));
    }
}