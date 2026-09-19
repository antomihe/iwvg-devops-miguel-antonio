package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UUID id;

    @BeforeEach
    void setUp() {
        this.id = UUID.randomUUID();
        this.user = User.builder()
                .id(this.id)
                .mobile("600000001")
                .name("John")
                .familyName("Doe")
                .isActive(true)
                .build();
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @Test
    void testCreateSuccess() {
        when(this.userRepository.existsByMobile("600000001")).thenReturn(false);
        when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User created = this.userService.create(this.user);

        assertThat(created).isNotNull();
        assertThat(created.getMobile()).isEqualTo("600000001");
        verify(this.userRepository).save(any(User.class));
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @Test
    void testCreateConflict() {
        when(this.userRepository.existsByMobile("600000001")).thenReturn(true);

        assertThatThrownBy(() -> this.userService.create(this.user))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("600000001");
    }

    @Test
    void testReadAll() {
        when(this.userRepository.findAll()).thenReturn(List.of(this.user));

        List<User> users = this.userService.readAll();

        assertThat(users).hasSize(1).contains(this.user);
    }

    @Test
    void testReadSuccess() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.of(this.user));

        User found = this.userService.read(this.id);

        assertThat(found).isEqualTo(this.user);
    }

    @Test
    void testReadNotFound() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.userService.read(this.id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(this.id.toString());
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @Test
    void testUpdateSuccessSameMobile() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.of(this.user));
        when(this.userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User updatedUser = User.builder().mobile("600000001").name("Jane").familyName("Doe").isActive(false).build();
        User result = this.userService.update(this.id, updatedUser);

        assertThat(result.getName()).isEqualTo("Jane");
        assertThat(result.getId()).isEqualTo(this.id);
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @Test
    void testUpdateSuccessDifferentMobile() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.of(this.user));
        when(this.userRepository.existsByMobile("600000002")).thenReturn(false);
        when(this.userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User updatedUser = User.builder().mobile("600000002").name("Jane").familyName("Doe").build();
        User result = this.userService.update(this.id, updatedUser);

        assertThat(result.getMobile()).isEqualTo("600000002");
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @Test
    void testUpdateConflictDifferentMobile() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.of(this.user));
        when(this.userRepository.existsByMobile("600000002")).thenReturn(true);

        User updatedUser = User.builder().mobile("600000002").build();

        assertThatThrownBy(() -> this.userService.update(this.id, updatedUser))
                .isInstanceOf(ConflictException.class);
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @Test
    void testUpdateActive() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.of(this.user));
        when(this.userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = this.userService.updateActive(this.id, false);

        assertThat(result.getIsActive()).isFalse();
    }

    @Test
    void testUpdateActiveList() {
        when(this.userRepository.findById(this.id)).thenReturn(Optional.of(this.user));
        when(this.userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        List<User> result = this.userService.updateActiveList(List.of(this.user));

        assertThat(result).hasSize(1);
    }

    @Test
    void testDeleteWhenExists() {
        when(this.userRepository.existsById(this.id)).thenReturn(true);

        this.userService.delete(this.id);

        verify(this.userRepository).deleteById(this.id);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(this.userRepository.existsById(this.id)).thenReturn(false);

        this.userService.delete(this.id);

        verify(this.userRepository, never()).deleteById(any());
    }

    @Test
    void testFindByCriteriaIsBillable() {
        User billableUser = User.builder().mobile("600000001").name("A").familyName("B").build();
        User nonBillableUser = User.builder().mobile("").name("A").familyName("B").build();

        when(this.userRepository.findAll()).thenReturn(List.of(billableUser, nonBillableUser));

        List<User> billables = this.userService.findByCriteria(UserFindCriteria.builder().isBillable(true).build());
        assertThat(billables).containsExactly(billableUser);

        List<User> nonBillables = this.userService.findByCriteria(UserFindCriteria.builder().isBillable(false).build());
        assertThat(nonBillables).containsExactly(nonBillableUser);

        List<User> all = this.userService.findByCriteria(UserFindCriteria.builder().isBillable(null).build());
        assertThat(all).hasSize(2);
    }
}