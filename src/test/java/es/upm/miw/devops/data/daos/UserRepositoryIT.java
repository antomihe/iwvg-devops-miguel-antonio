package es.upm.miw.devops.data.daos;

import es.upm.miw.devops.data.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test") // <-- CAMBIAR DE "dev" A "test"
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByIdSuccess() {
        Long existingUserId = 1L;

        Optional<User> userOptional = this.userRepository.findById(existingUserId);

        assertTrue(userOptional.isPresent());
        assertEquals(existingUserId, userOptional.get().getId());
    }

    @Test
    void testFindByIdNotFound() {
        Long nonExistingUserId = 999999L;

        Optional<User> userOptional = this.userRepository.findById(nonExistingUserId);

        assertTrue(userOptional.isEmpty());
    }
}