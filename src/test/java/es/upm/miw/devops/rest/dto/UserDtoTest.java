package es.upm.miw.devops.rest.dto;

import es.upm.miw.devops.data.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDtoTest {

    @Test
    void testUserDtoDefaultConstructor() {
        UserDto userDto = new UserDto();
        assertNull(userDto.getId());
        assertNull(userDto.getName());
        assertNull(userDto.getEmail());
    }

    @Test
    void testUserDtoFromUserConstructor() {
        User user = new User(1L, "Test User", "test@example.com");
        UserDto userDto = new UserDto(user);

        assertEquals(1L, userDto.getId());
        assertEquals("Test User", userDto.getName());
        assertEquals("test@example.com", userDto.getEmail());
    }
}