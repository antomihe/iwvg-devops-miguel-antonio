package es.upm.miw.devops.infrastructure.data.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testUserBuilderAndGettersSetters() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .mobile("600000001")
                .name("John")
                .familyName("Doe")
                .isActive(true)
                .build();

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getMobile()).isEqualTo("600000001");
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getFamilyName()).isEqualTo("Doe");
        assertThat(user.getIsActive()).isTrue();
    }

    @Test
    void testFullName() {
        User user = User.builder().name("John").familyName("Doe").build();
        assertThat(user.fullName()).isEqualTo("John Doe");
    }

    @Test
    void testInitialsWhenNameAndFamilyNameArePresent() {
        User user = User.builder().name("John").familyName("Doe").build();
        assertThat(user.initials()).isEqualTo("J.D.");
    }

    @Test
    void testInitialsWhenNameOrFamilyNameIsEmptyOrNull() {
        User user1 = User.builder().name("").familyName(null).build();
        assertThat(user1.initials()).isEmpty();

        User user2 = User.builder().name("John").familyName("").build();
        assertThat(user2.initials()).isEqualTo("J.");

        User user3 = User.builder().name(null).familyName("Doe").build();
        assertThat(user3.initials()).isEqualTo("D.");
    }
}