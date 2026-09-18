package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.ActiveDto;
import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNull(created.getId());
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
        User user = User.builder()
                .mobile("600999888")
                .name("OldName")
                .familyName("OldFamily")
                .active(true)
                .build();
        User created = this.userService.create(user);

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
        User user = User.builder()
                .mobile("600999881")
                .name("Initial")
                .familyName("User")
                .active(true)
                .build();
        User created = this.userService.create(user);

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

        User conflictData = User.builder()
                .mobile("600111111")
                .name("User2")
                .familyName("Test")
                .active(true)
                .build();

        assertThrows(ConflictException.class, () -> this.userService.update(createdUser2.getId(), conflictData));
    }

    @Test
    void testUpdateActive() {
        User user = User.builder()
                .mobile("600333444")
                .name("ActiveTest")
                .familyName("User")
                .active(true)
                .build();
        User created = this.userService.create(user);

        User updated = this.userService.updateActive(created.getId(), false);
        assertFalse(updated.getActive());
    }

    @Test
    void testUpdateActiveList() {
        User user1 = User.builder().mobile("600888111").name("U1").familyName("T1").active(true).build();
        User user2 = User.builder().mobile("600888222").name("U2").familyName("T2").active(true).build();
        User created1 = this.userService.create(user1);
        User created2 = this.userService.create(user2);

        List<ActiveDto> activeDtoList = List.of(
                new ActiveDto(created1.getId(), false),
                new ActiveDto(created2.getId(), false)
        );

        List<User> updatedUsers = this.userService.updateActiveList(activeDtoList);
        assertEquals(2, updatedUsers.size());
        assertFalse(updatedUsers.get(0).getActive());
        assertFalse(updatedUsers.get(1).getActive());
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

    @Test
    void testFindByBillable() {
        User billableUser = User.builder()
                .mobile("600555111")
                .name("ValidName")
                .familyName("ValidFamily")
                .active(true)
                .build();
        this.userService.create(billableUser);

        User nonBillableUser = User.builder()
                .mobile("600555222")
                .name("")
                .familyName("OnlyFamily")
                .active(true)
                .build();
        this.userService.create(nonBillableUser);

        List<User> billables = this.userService.findByBillable(true);
        assertTrue(billables.stream().anyMatch(u -> "600555111".equals(u.getMobile())));
        assertFalse(billables.stream().anyMatch(u -> "600555222".equals(u.getMobile())));
    }
}