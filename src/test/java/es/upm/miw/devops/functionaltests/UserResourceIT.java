package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.resources.UserResource;
import es.upm.miw.devops.resources.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateAndReadUser() {
        UserDto userDto = UserDto.builder()
                .mobile("699888777")
                .name("Integration")
                .familyName("Test")
                .active(true)
                .build();

        UserDto createdUser = this.webTestClient
                .post()
                .uri(UserResource.USERS)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());

        this.webTestClient
                .get()
                .uri(UserResource.USERS + "/" + createdUser.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class);
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
    void testReadAll() {
        this.webTestClient
                .get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);
    }

    @Test
    void testUpdateUser() {
        UserDto userDto = UserDto.builder()
                .mobile("688777666")
                .name("ToUpdate")
                .familyName("User")
                .active(true)
                .build();

        UserDto createdUser = this.webTestClient
                .post()
                .uri(UserResource.USERS)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        createdUser.setName("UpdatedName");

        this.webTestClient
                .put()
                .uri(UserResource.USERS + "/" + createdUser.getId())
                .bodyValue(createdUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class);
    }

    @Test
    void testDeleteUser() {
        UserDto userDto = UserDto.builder()
                .mobile("677666555")
                .name("ToDelete")
                .familyName("User")
                .active(true)
                .build();

        UserDto createdUser = this.webTestClient
                .post()
                .uri(UserResource.USERS)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);

        this.webTestClient
                .delete()
                .uri(UserResource.USERS + "/" + createdUser.getId())
                .exchange()
                .expectStatus().isOk();
    }
}