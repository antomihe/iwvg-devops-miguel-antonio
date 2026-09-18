package es.upm.miw.devops.resources.dtos;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActiveDtoTest {

    @Test
    void testActiveDtoConstructorsAndGetters() {
        ActiveDto activeDto = new ActiveDto(true);

        assertNotNull(activeDto);
        assertTrue(activeDto.getActive());

        activeDto.setActive(false);
        assertFalse(activeDto.getActive());
    }

    @Test
    void testActiveDtoWithIdAndActive() {
        UUID id = UUID.randomUUID();
        ActiveDto activeDto = new ActiveDto(id, true);

        assertEquals(id, activeDto.getId());
        assertTrue(activeDto.getActive());
    }

    @Test
    void testActiveDtoBuilder() {
        UUID id = UUID.randomUUID();
        ActiveDto activeDto = ActiveDto.builder()
                .id(id)
                .active(true)
                .build();

        assertNotNull(activeDto);
        assertEquals(id, activeDto.getId());
        assertTrue(activeDto.getActive());
    }
}