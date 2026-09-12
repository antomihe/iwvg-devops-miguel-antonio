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
        // ID 1 precargado en el seeder del perfil "test"
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
}