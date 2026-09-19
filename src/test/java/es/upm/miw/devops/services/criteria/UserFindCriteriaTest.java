package es.upm.miw.devops.services.criteria;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserFindCriteriaTest {

    @Test
    void testIsAllNull() {
        UserFindCriteria criteriaEmpty = new UserFindCriteria();
        assertThat(criteriaEmpty.isAllNull()).isTrue();

        UserFindCriteria criteriaWithMobile = UserFindCriteria.builder().mobile("600000000").build();
        assertThat(criteriaWithMobile.isAllNull()).isFalse();
    }

    @Test
    void testHasMobile() {
        UserFindCriteria criteriaNull = new UserFindCriteria();
        assertThat(criteriaNull.hasMobile()).isFalse();

        UserFindCriteria criteriaBlank = UserFindCriteria.builder().mobile("   ").build();
        assertThat(criteriaBlank.hasMobile()).isFalse();

        UserFindCriteria criteriaValid = UserFindCriteria.builder().mobile("600000000").build();
        assertThat(criteriaValid.hasMobile()).isTrue();
    }

    @Test
    void testHasFirstName() {
        UserFindCriteria criteriaNull = new UserFindCriteria();
        assertThat(criteriaNull.hasFirstName()).isFalse();

        UserFindCriteria criteriaBlank = UserFindCriteria.builder().firstName("").build();
        assertThat(criteriaBlank.hasFirstName()).isFalse();

        UserFindCriteria criteriaValid = UserFindCriteria.builder().firstName("John").build();
        assertThat(criteriaValid.hasFirstName()).isTrue();
    }

    @Test
    void testHasFamilyName() {
        UserFindCriteria criteriaNull = new UserFindCriteria();
        assertThat(criteriaNull.hasFamilyName()).isFalse();

        UserFindCriteria criteriaBlank = UserFindCriteria.builder().familyName("  ").build();
        assertThat(criteriaBlank.hasFamilyName()).isFalse();

        UserFindCriteria criteriaValid = UserFindCriteria.builder().familyName("Doe").build();
        assertThat(criteriaValid.hasFamilyName()).isTrue();
    }

    @Test
    void testHasIsBillable() {
        UserFindCriteria criteriaNull = new UserFindCriteria();
        assertThat(criteriaNull.hasIsBillable()).isFalse();

        UserFindCriteria criteriaValid = UserFindCriteria.builder().isBillable(true).build();
        assertThat(criteriaValid.hasIsBillable()).isTrue();
    }
}