package es.upm.miw.devops.infrastructure.data.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        this.user = User.builder()
                .id(UUID.randomUUID())
                .mobile("666666666")
                .name("Antonio")
                .familyName("Herrero")
                .active(true)
                .build();
    }

    @Test
    void testFullName() {
        assertEquals("Antonio Herrero", this.user.fullName());
    }

    @Test
    void testInitialsComplete() {
        assertEquals("A.H.", this.user.initials());
    }

    @Test
    void testInitialsWithNullAndEmptyValues() {
        User nullUser = new User();
        assertEquals("", nullUser.initials());

        User emptyUser = User.builder()
                .name("")
                .familyName("")
                .build();
        assertEquals("", emptyUser.initials());

        User onlyNameUser = User.builder()
                .name("Antonio")
                .familyName("")
                .build();
        assertEquals("A.", onlyNameUser.initials());

        User onlyFamilyNameUser = User.builder()
                .name(null)
                .familyName("Herrero")
                .build();
        assertEquals("H.", onlyFamilyNameUser.initials());
    }

    @Test
    void testGettersAndSetters() {
        UUID newId = UUID.randomUUID();
        this.user.setId(newId);
        this.user.setMobile("777777777");
        this.user.setName("Miguel");
        this.user.setFamilyName("Jiménez");
        this.user.setActive(false);

        assertEquals(newId, this.user.getId());
        assertEquals("777777777", this.user.getMobile());
        assertEquals("Miguel", this.user.getName());
        assertEquals("Jiménez", this.user.getFamilyName());
        assertFalse(this.user.getActive());
    }

    @Test
    void testFirstNameGetterAndSetter() {
        this.user.setFirstName("Carlos");
        assertEquals("Carlos", this.user.getFirstName());
        assertEquals("Carlos", this.user.getName());
    }

    @Test
    void testEqualsHashCodeAndToString() {
        User duplicateUser = User.builder()
                .id(this.user.getId())
                .mobile("666666666")
                .name("Antonio")
                .familyName("Herrero")
                .active(true)
                .build();

        assertEquals(this.user, duplicateUser);
        assertEquals(this.user.hashCode(), duplicateUser.hashCode());
        assertNotNull(this.user.toString());
    }
}
