package es.upm.miw.devops.resources.dtos;

import es.upm.miw.devops.infrastructure.data.models.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserActiveDtoTest {

    @Test
    void testUserActiveDtoConstructors() {
        UserActiveDto dto1 = new UserActiveDto(true);
        assertThat(dto1.getIsActive()).isTrue();

        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).isActive(false).build();
        UserActiveDto dto2 = new UserActiveDto(user);

        assertThat(dto2.getId()).isEqualTo(id);
        assertThat(dto2.getIsActive()).isFalse();
    }

    @Test
    void testToUser() {
        UUID id = UUID.randomUUID();
        UserActiveDto dto = UserActiveDto.builder().id(id).isActive(true).build();

        User user = dto.toUser();

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getIsActive()).isTrue();
    }
}