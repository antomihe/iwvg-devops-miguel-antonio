package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.resources.UserResource;
import es.upm.miw.devops.resources.dtos.UserActiveDto;
import es.upm.miw.devops.resources.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateReadUpdateDeleteUserFlow() {
        String uniqueMobile = "6" + (System.currentTimeMillis() % 100000000);
        UserDto userDto = UserDto.builder()
                .mobile(uniqueMobile)
                .firstName("TestName")
                .familyName("TestFamily")
                .isActive(true)
                .build();

        // 1. POST /users
        UserDto createdUser = this.webTestClient.post()
                .uri(UserResource.USERS)
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getMobile()).isEqualTo(uniqueMobile);

        UUID userId = createdUser.getId();

        // 2. GET /users
        this.webTestClient.get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertThat(users).isNotEmpty());

        // 3. GET /users/{id}
        this.webTestClient.get()
                .uri(UserResource.USERS + UserResource.ID_ID, userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(found -> assertThat(found.getFirstName()).isEqualTo("TestName"));

        // 4. PUT /users/{id}
        createdUser.setFirstName("UpdatedName");
        this.webTestClient.put()
                .uri(UserResource.USERS + UserResource.ID_ID, userId)
                .bodyValue(createdUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(updated -> assertThat(updated.getFirstName()).isEqualTo("UpdatedName"));

        // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
        // 5. PATCH /users/{id}/active
        UserActiveDto activeDto = new UserActiveDto(false);
        this.webTestClient.patch()
                .uri(UserResource.USERS + UserResource.ID_ACTIVE, userId)
                .bodyValue(activeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(patched -> assertThat(patched.getIsActive()).isFalse());

        // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
        // 6. PATCH /users/active
        UserActiveDto activeItem = UserActiveDto.builder().id(userId).isActive(true).build();
        this.webTestClient.patch()
                .uri(UserResource.USERS + UserResource.ACTIVE)
                .bodyValue(List.of(activeItem))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(list -> assertThat(list).isNotEmpty());

        // 7. GET /users/search
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("isBillable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);

        // 8. DELETE /users/{id}
        this.webTestClient.delete()
                .uri(UserResource.USERS + UserResource.ID_ID, userId)
                .exchange()
                .expectStatus().isOk();

        // 9. Verify 404
        this.webTestClient.get()
                .uri(UserResource.USERS + UserResource.ID_ID, userId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateValidationError() {
        UserDto invalidDto = UserDto.builder().mobile("").firstName("").familyName("").build();

        this.webTestClient.post()
                .uri(UserResource.USERS)
                .bodyValue(invalidDto)
                .exchange()
                .expectStatus().isBadRequest();
    }
}