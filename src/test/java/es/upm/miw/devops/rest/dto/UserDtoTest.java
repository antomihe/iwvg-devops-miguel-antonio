package es.upm.miw.devops.rest.dto;

import es.upm.miw.devops.data.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void testUserDtoDefaultConstructorAndSettersGetters() {
        UserDto userDto = new UserDto();
        assertNull(userDto.getId());
        assertNull(userDto.getName());
        assertNull(userDto.getEmail());
        assertNull(userDto.getBillable());
        assertNull(userDto.getActive());

        userDto.setId(1L);
        userDto.setName("Antonio");
        userDto.setEmail("antonio@example.com");
        userDto.setBillable(true);
        userDto.setActive(true);

        assertEquals(1L, userDto.getId());
        assertEquals("Antonio", userDto.getName());
        assertEquals("antonio@example.com", userDto.getEmail());
        assertTrue(userDto.getBillable());
        assertTrue(userDto.getActive());
    }

    @Test
    void testUserDtoAllArgsConstructor() {
        UserDto userDto = new UserDto(1L, "Antonio", "antonio@example.com", false, true);

        assertEquals(1L, userDto.getId());
        assertEquals("Antonio", userDto.getName());
        assertEquals("antonio@example.com", userDto.getEmail());
        assertFalse(userDto.getBillable());
        assertTrue(userDto.getActive());
    }

    @Test
    void testUserDtoFromUserConstructor() {
        User user = new User(1L, "Antonio Herrero", "antonio@example.com", "Antonio", "Herrero",
                "12345678Z", "Calle Mayor 1", "Madrid", "Madrid", "28001");
        user.setActive(true);

        UserDto userDto = new UserDto(user);

        assertEquals(1L, userDto.getId());
        assertEquals("Antonio Herrero", userDto.getName());
        assertEquals("antonio@example.com", userDto.getEmail());
        assertTrue(userDto.getBillable());
        assertTrue(userDto.getActive());
    }
}