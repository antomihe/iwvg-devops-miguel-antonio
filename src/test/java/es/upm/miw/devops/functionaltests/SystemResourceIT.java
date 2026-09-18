package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.resources.SystemResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class SystemResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadVersion() {
        this.webTestClient
                .get()
                .uri(SystemResource.SYSTEM + SystemResource.VERSION)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(SystemResource.VERSION_BADGE);
    }
}