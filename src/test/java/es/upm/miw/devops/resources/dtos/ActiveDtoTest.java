package es.upm.miw.devops.resources.dtos;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActiveDtoTest {

    @Test
    void testActiveDto() {
        UUID id = UUID.randomUUID();
        ActiveDto dto = ActiveDto.builder()
                .id(id)
                .active(true)
                .build();

        assertEquals(id, dto.getId());
        assertTrue(dto.getActive());
    }
}