package es.upm.miw.devops.data.daos;

import es.upm.miw.devops.data.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByIdSuccess() {
        Long existingUserId = 1L;

        Optional<User> userOptional = this.userRepository.findById(existingUserId);

        assertTrue(userOptional.isPresent(), "El usuario con ID " + existingUserId + " debería existir en la BD");
        assertEquals(existingUserId, userOptional.get().getId());
    }

    @Test
    void testFindByIdNotFound() {
        Long nonExistingUserId = 999999L;

        Optional<User> userOptional = this.userRepository.findById(nonExistingUserId);

        assertTrue(userOptional.isEmpty());
    }

    @Test
    void testFindAll() {
        List<User> users = this.userRepository.findAll();
        assertNotNull(users);
        assertFalse(users.isEmpty(), "La lista de usuarios no debería estar vacía");
    }

    @Test
    void testSaveAndExistsById() {
        Long existingUserId = 1L;
        assertTrue(this.userRepository.existsById(existingUserId));

        // Actualizar una entidad existente prueba el método save() sin chocar con la secuencia autoincremental de H2
        User user = this.userRepository.findById(existingUserId).orElseThrow();
        user.setName("UpdatedTestName");
        User savedUser = this.userRepository.save(user);

        assertEquals("UpdatedTestName", savedUser.getName());
        assertTrue(this.userRepository.existsById(savedUser.getId()));
    }

    @Test
    void testDeleteById() {
        Long existingUserId = 1L;
        assertTrue(this.userRepository.existsById(existingUserId));

        this.userRepository.deleteById(existingUserId);

        assertFalse(this.userRepository.existsById(existingUserId));
        assertTrue(this.userRepository.findById(existingUserId).isEmpty());
    }
}