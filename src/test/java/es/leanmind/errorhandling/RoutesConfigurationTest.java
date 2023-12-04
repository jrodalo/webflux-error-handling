package es.leanmind.errorhandling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class RoutesConfigurationTest extends IntegrationTest {

    @Autowired WebTestClient webTestClient;

    @Test
    void unknown_endpoints_return_a_not_found_response() {
        webTestClient.get()
            .uri("/something/something")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("code").isEqualTo("ERROR_0004")
            .jsonPath("message").isEqualTo("Invalid request");
    }
}
