package es.upm.miw.devops.rest.dto;

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
        userDto.setName("Oscar");
        userDto.setEmail("oscar@example.com");
        userDto.setBillable(true);
        userDto.setActive(true);

        assertEquals(1L, userDto.getId());
        assertEquals("Oscar", userDto.getName());
        assertEquals("oscar@example.com", userDto.getEmail());
        assertTrue(userDto.getBillable());
        assertTrue(userDto.getActive());
    }

    @Test
    void testUserDtoAllArgsConstructor() {
        UserDto userDto = new UserDto(1L, "Oscar", "oscar@example.com", false, true);

        assertEquals(1L, userDto.getId());
        assertEquals("Oscar", userDto.getName());
        assertEquals("oscar@example.com", userDto.getEmail());
        assertFalse(userDto.getBillable());
        assertTrue(userDto.getActive());
    }
}