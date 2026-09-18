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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateSuccess() {
        User userToCreate = User.builder()
                .mobile("666000000")
                .name("Test")
                .familyName("User")
                .active(true)
                .build();

        User user = this.userService.create(userToCreate);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("666000000", user.getMobile());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getFamilyName());
        assertTrue(user.getActive());

        this.userRepository.deleteById(user.getId());
    }

    @Test
    void testCreateConflictMobileExists() {
        User existingUser = this.userRepository.findAll().stream().findFirst().orElseThrow();
        User userToCreate = User.builder()
                .mobile(existingUser.getMobile())
                .name("Test")
                .build();

        assertThrows(ConflictException.class, () -> this.userService.create(userToCreate));
    }

    @Test
    void testReadAll() {
        List<User> users = this.userService.readAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void testReadSuccess() {
        User existingUser = this.userRepository.findAll().stream().findFirst().orElseThrow();
        User user = this.userService.read(existingUser.getId());

        assertNotNull(user);
        assertEquals(existingUser.getMobile(), user.getMobile());
    }

    @Test
    void testReadNotFound() {
        UUID nonExistentId = UUID.fromString("00000000-0000-0000-0000-999999999999");
        assertThrows(NotFoundException.class, () -> this.userService.read(nonExistentId));
    }

    @Test
    void testUpdateSuccessSameMobile() {
        User existingUser = this.userRepository.findAll().stream().findFirst().orElseThrow();
        User userToUpdate = User.builder()
                .mobile(existingUser.getMobile())
                .name("UpdatedName")
                .familyName("UpdatedFamily")
                .active(false)
                .build();

        User updatedUser = this.userService.update(existingUser.getId(), userToUpdate);

        assertEquals("UpdatedName", updatedUser.getFirstName());
        assertEquals("UpdatedFamily", updatedUser.getFamilyName());
        assertFalse(updatedUser.getActive());
    }

    @Test
    void testUpdateSuccessNewMobile() {
        User existingUser = this.userRepository.findAll().stream().findFirst().orElseThrow();
        String newMobile = "699000111";

        User userToUpdate = User.builder()
                .mobile(newMobile)
                .name("NewName")
                .familyName("NewFamily")
                .active(true)
                .build();

        User updatedUser = this.userService.update(existingUser.getId(), userToUpdate);

        assertEquals(newMobile, updatedUser.getMobile());
        assertEquals("NewName", updatedUser.getFirstName());
    }

    @Test
    void testUpdateNotFound() {
        UUID nonExistentId = UUID.fromString("00000000-0000-0000-0000-999999999999");
        User userToUpdate = User.builder().mobile("600000000").build();

        assertThrows(NotFoundException.class, () -> this.userService.update(nonExistentId, userToUpdate));
    }

    @Test
    void testUpdateConflictMobileExists() {
        List<User> users = this.userRepository.findAll();
        if (users.size() >= 2) {
            User firstUser = users.get(0);
            User secondUser = users.get(1);

            User userToUpdate = User.builder()
                    .mobile(firstUser.getMobile())
                    .name("Updated")
                    .build();

            assertThrows(ConflictException.class, () -> this.userService.update(secondUser.getId(), userToUpdate));
        }
    }

    @Test
    void testUpdateActive() {
        User existingUser = this.userRepository.findAll().stream().findFirst().orElseThrow();
        Boolean newActiveState = !Boolean.TRUE.equals(existingUser.getActive());

        User updatedUser = this.userService.updateActive(existingUser.getId(), newActiveState);

        assertEquals(newActiveState, updatedUser.getActive());
    }

    @Test
    void testUpdateActiveList() {
        List<User> users = this.userRepository.findAll();
        if (!users.isEmpty()) {
            User user = users.get(0);
            ActiveDto activeDto = new ActiveDto(user.getId(), false);

            List<User> updatedUsers = this.userService.updateActiveList(List.of(activeDto));

            assertNotNull(updatedUsers);
            assertFalse(updatedUsers.isEmpty());
            assertFalse(updatedUsers.get(0).getActive());
        }
    }

    @Test
    void testDeleteSuccess() {
        User tempUser = this.userService.create(User.builder().mobile("699999999").build());

        this.userService.delete(tempUser.getId());

        assertFalse(this.userRepository.existsById(tempUser.getId()));
    }

    @Test
    void testDeleteIdempotent() {
        UUID nonExistentId = UUID.fromString("00000000-0000-0000-0000-999999999999");
        assertDoesNotThrow(() -> this.userService.delete(nonExistentId));
    }

    @Test
    void testFindByBillableAndIsBillableBranches() {
        User billableUser = this.userService.create(User.builder()
                .mobile("611111111")
                .name("Billable")
                .familyName("User")
                .build());

        User nonBillableNullName = this.userService.create(User.builder()
                .mobile("622222222")
                .familyName("User")
                .build());

        User nonBillableBlankName = this.userService.create(User.builder()
                .mobile("633333333")
                .name("   ")
                .familyName("User")
                .build());

        User nonBillableNullFamily = this.userService.create(User.builder()
                .mobile("644444444")
                .name("Name")
                .build());

        User nonBillableBlankFamily = this.userService.create(User.builder()
                .mobile("655555555")
                .name("Name")
                .familyName("   ")
                .build());

        User nonBillableNullMobile = new User();
        nonBillableNullMobile.setName("Name");
        nonBillableNullMobile.setFamilyName("Family");
        nonBillableNullMobile.setMobile(null);

        User nonBillableBlankMobile = new User();
        nonBillableBlankMobile.setName("Name");
        nonBillableBlankMobile.setFamilyName("Family");
        nonBillableBlankMobile.setMobile("   ");

        List<User> billableUsers = this.userService.findByBillable(true);
        assertTrue(billableUsers.stream().anyMatch(u -> "611111111".equals(u.getMobile())));

        List<User> nonBillableUsers = this.userService.findByBillable(false);
        assertTrue(nonBillableUsers.stream().anyMatch(u -> "622222222".equals(u.getMobile())));

        this.userRepository.deleteById(billableUser.getId());
        this.userRepository.deleteById(nonBillableNullName.getId());
        this.userRepository.deleteById(nonBillableBlankName.getId());
        this.userRepository.deleteById(nonBillableNullFamily.getId());
        this.userRepository.deleteById(nonBillableBlankFamily.getId());
    }
}