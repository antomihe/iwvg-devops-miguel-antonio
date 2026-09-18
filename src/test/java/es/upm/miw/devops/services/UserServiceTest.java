package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateSuccess() {
        User user = User.builder()
                .mobile("611222333")
                .name("Alice")
                .familyName("Smith")
                .active(true)
                .build();
        User created = this.userService.create(user);
        assertNotNull(created.getId());
        assertEquals("611222333", created.getMobile());
    }

    @Test
    void testCreateConflict() {
        User user = User.builder()
                .mobile("611222334")
                .name("Bob")
                .familyName("Jones")
                .active(true)
                .build();
        this.userService.create(user);

        User duplicateUser = User.builder()
                .mobile("611222334")
                .name("Bob")
                .familyName("Jones")
                .active(true)
                .build();

        assertThrows(ConflictException.class, () -> this.userService.create(duplicateUser));
    }

    @Test
    void testReadSuccess() {
        User user = User.builder()
                .mobile("600000001")
                .name("Read")
                .familyName("Test")
                .active(true)
                .build();
        User created = this.userService.create(user);

        User found = this.userService.read(created.getId());
        assertEquals("600000001", found.getMobile());
    }

    @Test
    void testReadNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> this.userService.read(nonExistentId));
    }

    @Test
    void testReadAll() {
        User user = User.builder()
                .mobile("600000002")
                .name("ReadAll")
                .familyName("Test")
                .active(true)
                .build();
        this.userService.create(user);

        List<User> users = this.userService.readAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void testUpdateSuccessWithSameMobile() {
        // Cobertura de rama: !existingUser.getMobile().equals(user.getMobile()) -> FALSE
        User user = User.builder()
                .mobile("600999888")
                .name("OldName")
                .familyName("OldFamily")
                .active(true)
                .build();
        User created = this.userService.create(user);

        // Mismo móvil, cambiando solo nombre
        User updateData = User.builder()
                .mobile("600999888")
                .name("NewName")
                .familyName("NewFamily")
                .active(false)
                .build();

        User updated = this.userService.update(created.getId(), updateData);
        assertEquals("NewName", updated.getName());
        assertEquals("NewFamily", updated.getFamilyName());
        assertEquals("600999888", updated.getMobile());
        assertFalse(updated.getActive());
    }

    @Test
    void testUpdateSuccessWithDifferentMobile() {
        // Cobertura de rama: !existingUser.getMobile().equals(user.getMobile()) -> TRUE
        User user = User.builder()
                .mobile("600999881")
                .name("Initial")
                .familyName("User")
                .active(true)
                .build();
        User created = this.userService.create(user);

        // Cambiando móvil a uno nuevo que no existe
        User updateData = User.builder()
                .mobile("600999882")
                .name("Initial")
                .familyName("User")
                .active(true)
                .build();

        User updated = this.userService.update(created.getId(), updateData);
        assertEquals("600999882", updated.getMobile());
    }

    @Test
    void testUpdateConflictWithExistingMobile() {
        // Provoca ConflictException al intentar actualizar al móvil de otro usuario existente
        User user1 = User.builder()
                .mobile("600111111")
                .name("User1")
                .familyName("Test")
                .active(true)
                .build();
        this.userService.create(user1);

        User user2 = User.builder()
                .mobile("600222222")
                .name("User2")
                .familyName("Test")
                .active(true)
                .build();
        User createdUser2 = this.userService.create(user2);

        // Intentamos cambiar el móvil de user2 por el de user1
        User conflictData = User.builder()
                .mobile("600111111")
                .name("User2")
                .familyName("Test")
                .active(true)
                .build();

        assertThrows(ConflictException.class, () -> this.userService.update(createdUser2.getId(), conflictData));
    }

    @Test
    void testDelete() {
        User user = User.builder()
                .mobile("600777666")
                .name("DeleteTest")
                .familyName("User")
                .active(true)
                .build();
        User created = this.userService.create(user);
        UUID id = created.getId();

        this.userService.delete(id);
        assertThrows(NotFoundException.class, () -> this.userService.read(id));
    }
}