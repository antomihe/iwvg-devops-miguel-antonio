package es.upm.miw.devops.resources.dtos;

import es.upm.miw.devops.infrastructure.data.models.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest {

    @Test
    void testUserDtoConstructorWithUser() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .mobile("600000000")
                .name("Alice")
                .familyName("Smith")
                .isActive(true)
                .build();

        UserDto dto = new UserDto(user);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getMobile()).isEqualTo("600000000");
        assertThat(dto.getFirstName()).isEqualTo("Alice");
        assertThat(dto.getFamilyName()).isEqualTo("Smith");
        assertThat(dto.getIsActive()).isTrue();
    }

    @Test
    void testToUser() {
        UUID id = UUID.randomUUID();
        UserDto dto = UserDto.builder()
                .id(id)
                .mobile("600000000")
                .firstName("Bob")
                .familyName("Martin")
                .isActive(false)
                .build();

        User user = dto.toUser();

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getMobile()).isEqualTo("600000000");
        assertThat(user.getName()).isEqualTo("Bob");
        assertThat(user.getFamilyName()).isEqualTo("Martin");
        assertThat(user.getIsActive()).isFalse();
    }
}