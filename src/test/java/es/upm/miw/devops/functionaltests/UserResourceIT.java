package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.resources.UserResource;
import es.upm.miw.devops.resources.dtos.ActiveDto;
import es.upm.miw.devops.resources.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadSuccess() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS + "/{id}", "11111111-1111-1111-1111-111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertNotNull(userDto);
                    assertEquals(UUID.fromString("11111111-1111-1111-1111-111111111111"), userDto.getId());
                    assertEquals("666000001", userDto.getMobile());
                    assertEquals("Admin", userDto.getFirstName());
                    assertEquals("DevOps", userDto.getFamilyName());
                });
    }

    @Test
    void testReadNotFound() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS + "/{id}", "00000000-0000-0000-0000-999999999999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testReadAll() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> {
                    assertNotNull(users);
                    assertFalse(users.isEmpty());
                });
    }

    @Test
    void testCreate() {
        UserDto userDtoToCreate = UserDto.builder()
                .mobile("600000099")
                .firstName("TestName")
                .familyName("TestFamily")
                .active(true)
                .build();

        this.webTestClient
                .post()
                .uri(UserResource.USERS)
                .bodyValue(userDtoToCreate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertNotNull(userDto);
                    assertNotNull(userDto.getId());
                    assertEquals("600000099", userDto.getMobile());
                    assertEquals("TestName", userDto.getFirstName());
                    assertEquals("TestFamily", userDto.getFamilyName());
                    assertTrue(userDto.getActive());
                });
    }

    @Test
    void testUpdate() {
        UserDto userDtoToUpdate = UserDto.builder()
                .mobile("666000001")
                .firstName("UpdatedAdmin")
                .familyName("UpdatedDevOps")
                .active(true)
                .build();

        this.webTestClient
                .put()
                .uri(UserResource.USERS + "/{id}", "11111111-1111-1111-1111-111111111111")
                .bodyValue(userDtoToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertNotNull(userDto);
                    assertEquals("UpdatedAdmin", userDto.getFirstName());
                    assertEquals("UpdatedDevOps", userDto.getFamilyName());
                });
    }

    @Test
    void testUpdateActive() {
        ActiveDto activeDto = new ActiveDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), false);

        this.webTestClient
                .patch()
                .uri(UserResource.USERS + "/{id}/active", "11111111-1111-1111-1111-111111111111")
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertNotNull(userDto);
                    assertFalse(userDto.getActive());
                });
    }

    @Test
    void testUpdateActivePut() {
        ActiveDto activeDto = new ActiveDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), true);

        this.webTestClient
                .put()
                .uri(UserResource.USERS + "/{id}/active", "11111111-1111-1111-1111-111111111111")
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertNotNull(userDto);
                    assertTrue(userDto.getActive());
                });
    }

    @Test
    void testUpdateActiveList() {
        List<ActiveDto> activeDtoList = List.of(
                new ActiveDto(UUID.fromString("11111111-1111-1111-1111-111111111111"), true)
        );

        this.webTestClient
                .patch()
                .uri(UserResource.USERS + "/active")
                .bodyValue(activeDtoList)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> {
                    assertNotNull(users);
                    assertFalse(users.isEmpty());
                });
    }

    @Test
    void testDelete() {
        UserDto tempUser = UserDto.builder()
                .mobile("688888888")
                .firstName("Temp")
                .familyName("Delete")
                .active(true)
                .build();

        UserDto created = this.webTestClient
                .post()
                .uri(UserResource.USERS)
                .bodyValue(tempUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(created);

        this.webTestClient
                .delete()
                .uri(UserResource.USERS + "/{id}", created.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindByBillable() {
        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + "/search")
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);
    }
}