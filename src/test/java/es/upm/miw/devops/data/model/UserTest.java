package es.upm.miw.devops.data.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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

        user.setName("Antonio");
        user.setEmail("antonio@example.com");
        user.setFirstName("Antonio");
        user.setFamilyName("Herrero");
        user.setIdentity("12345678Z");
        user.setAddress("Calle Mayor 1");
        user.setCity("Madrid");
        user.setProvince("Madrid");
        user.setPostalCode("28001");

        assertEquals("Antonio", user.getName());
        assertEquals("antonio@example.com", user.getEmail());
        assertEquals("Antonio", user.getFirstName());
        assertEquals("Herrero", user.getFamilyName());
        assertEquals("12345678Z", user.getIdentity());
        assertEquals("Calle Mayor 1", user.getAddress());
        assertEquals("Madrid", user.getCity());
        assertEquals("Madrid", user.getProvince());
        assertEquals("28001", user.getPostalCode());
    }

    @Test
    void testUserPartialConstructor() {
        User user = new User(1L, "Antonio", "antonio@example.com");

        assertAll("Verificación de constructor parcial",
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("Antonio", user.getName()),
                () -> assertEquals("antonio@example.com", user.getEmail()),
                () -> assertNull(user.getFirstName()),
                () -> assertNull(user.getFamilyName()),
                () -> assertNull(user.getIdentity()),
                () -> assertNull(user.getAddress()),
                () -> assertNull(user.getCity()),
                () -> assertNull(user.getProvince()),
                () -> assertNull(user.getPostalCode())
        );
    }

    @Test
    void testUserAllArgsConstructor() {
        User user = new User(1L, "Antonio Herrero", "antonio@example.com", "Antonio", "Herrero",
                "12345678Z", "Calle Mayor 1", "Madrid", "Madrid", "28001");

        assertAll("Verificación de constructor completo",
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("Antonio Herrero", user.getName()),
                () -> assertEquals("antonio@example.com", user.getEmail()),
                () -> assertEquals("Antonio", user.getFirstName()),
                () -> assertEquals("Herrero", user.getFamilyName()),
                () -> assertEquals("12345678Z", user.getIdentity()),
                () -> assertEquals("Calle Mayor 1", user.getAddress()),
                () -> assertEquals("Madrid", user.getCity()),
                () -> assertEquals("Madrid", user.getProvince()),
                () -> assertEquals("28001", user.getPostalCode())
        );
    }

    @Test
    void testIsBillableTrue() {
        User user = new User(1L, "Antonio Herrero", "antonio@example.com", "Antonio", "Herrero",
                "12345678Z", "Calle Mayor 1", "Madrid", "Madrid", "28001");

        assertTrue(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenFirstNameInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", null, "Herrero", "12345678Z", "Calle 1", "Madrid", "Madrid", "28001");
        assertFalse(user.isBillable());
        user.setFirstName("   ");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenFamilyNameInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", "Antonio", null, "12345678Z", "Calle 1", "Madrid", "Madrid", "28001");
        assertFalse(user.isBillable());
        user.setFamilyName("");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenEmailInvalid() {
        User user = new User(1L, "Antonio", null, "Antonio", "Herrero", "12345678Z", "Calle 1", "Madrid", "Madrid", "28001");
        assertFalse(user.isBillable());
        user.setEmail("  ");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenIdentityInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", "Antonio", "Herrero", null, "Calle 1", "Madrid", "Madrid", "28001");
        assertFalse(user.isBillable());
        user.setIdentity("");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenAddressInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", "Antonio", "Herrero", "12345678Z", null, "Madrid", "Madrid", "28001");
        assertFalse(user.isBillable());
        user.setAddress("  ");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenCityInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", "Antonio", "Herrero", "12345678Z", "Calle 1", null, "Madrid", "28001");
        assertFalse(user.isBillable());
        user.setCity("");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenProvinceInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", "Antonio", "Herrero", "12345678Z", "Calle 1", "Madrid", null, "28001");
        assertFalse(user.isBillable());
        user.setProvince("   ");
        assertFalse(user.isBillable());
    }

    @Test
    void testIsBillableFalseWhenPostalCodeInvalid() {
        User user = new User(1L, "Antonio", "antonio@example.com", "Antonio", "Herrero", "12345678Z", "Calle 1", "Madrid", "Madrid", null);
        assertFalse(user.isBillable());
        user.setPostalCode("");
        assertFalse(user.isBillable());
    }
}