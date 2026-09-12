package es.upm.miw.devops.data.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserDefaultConstructorAndSettersGetters() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getFirstName());
        assertNull(user.getFamilyName());
        assertNull(user.getIdentity());
        assertNull(user.getAddress());
        assertNull(user.getCity());
        assertNull(user.getProvince());
        assertNull(user.getPostalCode());
        assertTrue(user.getActive());

        user.setName("Antonio");
        user.setEmail("antonio@example.com");
        user.setFirstName("Antonio");
        user.setFamilyName("Herrero");
        user.setIdentity("12345678Z");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("28001");
        user.setActive(false);

        assertEquals("Antonio", user.getName());
        assertEquals("antonio@example.com", user.getEmail());
        assertEquals("Antonio", user.getFirstName());
        assertEquals("Herrero", user.getFamilyName());
        assertEquals("12345678Z", user.getIdentity());
        assertEquals("Calle Mayor 1", user.getAddress());
        assertEquals("Madrid", user.getCity());
        assertEquals("Madrid", user.getProvince());
        assertEquals("28001", user.getPostalCode());
        assertFalse(user.getActive());
    }

    @Test
    void testUserMinimalConstructor() {
        User user = new User(1L, "Antonio", "antonio@example.com");

        assertEquals(1L, user.getId());
        assertEquals("Antonio", user.getName());
        assertEquals("antonio@example.com", user.getEmail());
        assertTrue(user.getActive());
        assertFalse(user.isBillable());
    }

    @Test
    void testUserFullConstructor() {
        User user = new User(1L, "Antonio Herrero", "antonio@example.com", "Antonio", "Herrero",
                "12345678Z", "Calle Mayor 1", "Madrid", "Madrid", "28001");

        assertEquals(1L, user.getId());
        assertEquals("Antonio Herrero", user.getName());
        assertEquals("antonio@example.com", user.getEmail());
        assertEquals("Antonio", user.getFirstName());
        assertEquals("Herrero", user.getFamilyName());
        assertEquals("12345678Z", user.getIdentity());
        assertEquals("Calle Mayor 1", user.getAddress());
        assertEquals("Madrid", user.getCity());
        assertEquals("Madrid", user.getProvince());
        assertEquals("28001", user.getPostalCode());
        assertTrue(user.getActive());
        assertTrue(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenFieldIsEmptyOrNull() {
        User user = new User(1L, "Antonio Herrero", "antonio@example.com", "Antonio", "Herrero",
                "12345678Z", "Calle Mayor 1", "Madrid", "Madrid", "   ");

        assertFalse(user.isBillable());
    }
}