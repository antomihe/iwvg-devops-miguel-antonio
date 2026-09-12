package es.upm.miw.devops.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveDtoTest {

    @Test
    void testActiveDtoConstructorsAndGettersSetters() {
        ActiveDto activeDtoEmpty = new ActiveDto();
        assertNull(activeDtoEmpty.getActive());

        activeDtoEmpty.setActive(true);
        assertTrue(activeDtoEmpty.getActive());

        ActiveDto activeDtoParam = new ActiveDto(false);
        assertFalse(activeDtoParam.getActive());
    }
}