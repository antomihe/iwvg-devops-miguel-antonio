package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.resources.UserResource;
import es.upm.miw.devops.resources.dtos.ActiveDto;
import es.upm.miw.devops.resources.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadAll() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertFalse(users.isEmpty()));
    }

    @Test
    void testReadSuccess() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS + "/11111111-1111-1111-1111-111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(user -> assertEquals("666666666", user.getMobile()));
    }

    @Test
    void testReadNotFound() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS + "/" + UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreate() {
        UserDto userDto = UserDto.builder()
                .mobile("600000099")
                .firstName("TestName")
                .familyName("TestFamily")
                .active(true)
                .build();

        this.webTestClient
                .post()
                .uri(UserResource.USERS)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(created -> {
                    assertNotNull(created.getId());
                    assertEquals("600000099", created.getMobile());
                });
    }

    @Test
    void testUpdate() {
        UserDto userDto = UserDto.builder()
                .mobile("666666666")
                .firstName("UpdatedFirstName")
                .familyName("UpdatedFamilyName")
                .active(true)
                .build();

        this.webTestClient
                .put()
                .uri(UserResource.USERS + "/11111111-1111-1111-1111-111111111111")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(updated -> assertEquals("UpdatedFirstName", updated.getFirstName()));
    }

    @Test
    void testUpdateActivePatch() {
        ActiveDto activeDto = new ActiveDto(false);

        this.webTestClient
                .patch()
                .uri(UserResource.USERS + "/11111111-1111-1111-1111-111111111111" + UserResource.ACTIVE)
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(updated -> assertFalse(updated.getActive()));
    }

    @Test
    void testUpdateActivePut() {
        ActiveDto activeDto = new ActiveDto(false);

        this.webTestClient
                .put()
                .uri(UserResource.USERS + "/11111111-1111-1111-1111-111111111111" + UserResource.ACTIVE)
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(updated -> assertFalse(updated.getActive()));
    }

    @Test
    void testUpdateActiveList() {
        List<ActiveDto> activeDtoList = List.of(
                new ActiveDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), false)
        );

        this.webTestClient
                .patch()
                .uri(UserResource.USERS)
                .bodyValue(activeDtoList)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertFalse(users.get(0).getActive()));
    }

    @Test
    void testDelete() {
        this.webTestClient
                .delete()
                .uri(UserResource.USERS + "/22222222-2222-2222-2222-222222222222")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindByBillable() {
        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertFalse(users.isEmpty()));
    }
}