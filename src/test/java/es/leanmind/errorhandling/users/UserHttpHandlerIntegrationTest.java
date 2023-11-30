package es.leanmind.errorhandling.users;

import es.leanmind.errorhandling.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserHttpHandlerIntegrationTest extends IntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void returns_a_user_when_user_is_found() {
        webTestClient.get()
            .uri("/api/v1/users/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("id").isEqualTo("1")
            .jsonPath("name").isEqualTo("First User");
    }

    @Test
    void returns_not_found_when_there_are_no_users_with_the_requested_id() {
        webTestClient.get()
            .uri("/api/v1/users/99999")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("code").isEqualTo("ERROR_0002")
            .jsonPath("message").isEqualTo("User not found");
    }
}
