package es.upm.miw.devops.resources.dto;

import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

    @Test
    void testUserDtoToDomainAndViceVersa() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .mobile("655443322")
                .name("Carlos")
                .familyName("López")
                .active(true)
                .build();

        UserDto dto = new UserDto(user);
        assertEquals(id, dto.getId());
        assertEquals("655443322", dto.getMobile());

        User domainUser = dto.toDomain();
        assertEquals(id, domainUser.getId());
        assertEquals("Carlos", domainUser.getName());
    }
}