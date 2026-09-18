package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.resources.SystemResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class SystemResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testApplicationInfo() {
        this.webTestClient
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertTrue(response.contains("/version-badge"));
                });
    }

    @Test
    void testGenerateBadge() {
        this.webTestClient
                .get()
                .uri(SystemResource.VERSION_BADGE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("image/svg+xml"))
                .expectBody(byte[].class)
                .value(bytes -> {
                    assertNotNull(bytes);
                    assertTrue(bytes.length > 0);
                });
    }
}