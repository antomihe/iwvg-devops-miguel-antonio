package es.upm.miw.devops.resources.dtos;

import es.upm.miw.devops.infrastructure.data.models.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("Carlos", dto.getFirstName());
        assertEquals("López", dto.getFamilyName());
        assertTrue(dto.getActive());

        User domainUser = dto.toUser();
        assertEquals(id, domainUser.getId());
        assertEquals("655443322", domainUser.getMobile());
        assertEquals("Carlos", domainUser.getName());
        assertEquals("López", domainUser.getFamilyName());
        assertTrue(domainUser.getActive());
    }

    @Test
    void testGettersAndSetters() {
        UserDto dto = new UserDto();
        UUID id = UUID.randomUUID();

        dto.setId(id);
        dto.setMobile("666777888");
        dto.setFirstName("Ana");
        dto.setFamilyName("García");
        dto.setActive(false);

        assertEquals(id, dto.getId());
        assertEquals("666777888", dto.getMobile());
        assertEquals("Ana", dto.getFirstName());
        assertEquals("García", dto.getFamilyName());
        assertFalse(dto.getActive());
    }

    @Test
    void testEqualsHashCodeAndToString() {
        UUID id = UUID.randomUUID();
        UserDto dto1 = UserDto.builder()
                .id(id)
                .mobile("600000000")
                .firstName("Test")
                .familyName("User")
                .active(true)
                .build();

        UserDto dto2 = UserDto.builder()
                .id(id)
                .mobile("600000000")
                .firstName("Test")
                .familyName("User")
                .active(true)
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }
}
