package es.upm.miw.devops.data.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    void testUserConstructorAndGettersSetters() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());

        user.setName("Antonio");
        user.setEmail("antonio@example.com");

        assertEquals("Antonio", user.getName());
        assertEquals("antonio@example.com", user.getEmail());

        User fullUser = new User(10L, "Miguel", "miguel@example.com");
        assertEquals(10L, fullUser.getId());
        assertEquals("Miguel", fullUser.getName());
        assertEquals("miguel@example.com", fullUser.getEmail());
    }
}