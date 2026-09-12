package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.rest.SystemResource;
import es.upm.miw.devops.rest.UserResource;
import es.upm.miw.devops.rest.dto.ActiveDto;
import es.upm.miw.devops.rest.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class SystemResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadBadge() {
        webTestClient.get()
                .uri(SystemResource.VERSION_BADGE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .isNotNull()
                        .startsWith("<svg"));
    }

    @Test
    void testReadInfo() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .isNotNull()
                        .isNotEmpty());
    }

    @Test
    void testUpdateActiveSuccess() {
        Long existingUserId = 1L;
        ActiveDto activeDto = new ActiveDto(false);

        this.webTestClient.put()
                .uri(UserResource.USERS + "/{id}/active", existingUserId)
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertEquals(existingUserId, userDto.getId());
                    assertFalse(userDto.getActive());
                });
    }

    @Test
    void testUpdateActiveNotFound() {
        Long nonExistingUserId = 999999L;
        ActiveDto activeDto = new ActiveDto(true);

        this.webTestClient.put()
                .uri(UserResource.USERS + "/{id}/active", nonExistingUserId)
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateActiveBadRequestMissingBody() {
        Long existingUserId = 1L;

        this.webTestClient.put()
                .uri(UserResource.USERS + "/{id}/active", existingUserId)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
