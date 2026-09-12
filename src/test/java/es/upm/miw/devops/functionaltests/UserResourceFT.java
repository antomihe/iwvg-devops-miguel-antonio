package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.rest.UserResource;
import es.upm.miw.devops.rest.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadUser() {
        Long existingUserId = 1L;

        this.webTestClient.get()
                .uri(UserResource.USERS + "/{id}", existingUserId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(userDto -> {
                    assertThat(userDto).isNotNull();
                    assertThat(userDto.getId()).isEqualTo(existingUserId);
                    assertThat(userDto.getName()).isNotNull();
                    assertThat(userDto.getEmail()).isNotNull();
                });
    }

    @Test
    void testReadUserNotFound() {
        Long nonExistingUserId = 999999L;

        this.webTestClient.get()
                .uri(UserResource.USERS + "/{id}", nonExistingUserId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testSearchWithoutParamsReturnsAll() {
        this.webTestClient.get()
                .uri(UserResource.USERS + UserResource.SEARCH)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertThat(users).isNotEmpty());
    }

    @Test
    void testSearchByNameSuccessAndNotFound() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("name", "Oscar")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertThat(users).allMatch(u -> u.getName() != null && u.getName().contains("Oscar")));

        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("name", "NonExistingNameXYZ")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertThat(users).isEmpty());
    }

    @Test
    void testSearchByEmailIgnoreCase() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("email", "OSCAR@EXAMPLE.COM") // Ajusta según tu seeder
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);
    }

    @Test
    void testSearchByBillableTrue() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> {
                    assertThat(users).isNotNull();
                    assertThat(users).allMatch(userDto -> Boolean.TRUE.equals(userDto.getBillable()));
                });
    }

    @Test
    void testSearchByBillableFalse() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("billable", false)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> {
                    assertThat(users).isNotNull();
                    assertThat(users).allMatch(userDto -> Boolean.FALSE.equals(userDto.getBillable()));
                });
    }

    @Test
    void testSearchByAllParametersCombined() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS + UserResource.SEARCH)
                        .queryParam("name", "a")
                        .queryParam("email", "a@a.com")
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);
    }

    @Test
    void testDeleteUser() {
        // Usamos un ID existente en el seeder para verificar su eliminación
        Long existingUserId = 1L;

        this.webTestClient.delete()
                .uri(UserResource.USERS + "/{id}", existingUserId)
                .exchange()
                .expectStatus().isNoContent();

        // Verificamos que tras la eliminación el endpoint GET devuelva NOT_FOUND
        this.webTestClient.get()
                .uri(UserResource.USERS + "/{id}", existingUserId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteUserNotFound() {
        Long nonExistingUserId = 999999L;

        this.webTestClient.delete()
                .uri(UserResource.USERS + "/{id}", nonExistingUserId)
                .exchange()
                .expectStatus().isNotFound();
    }
}