package es.leanmind.errorhandling.users;

import es.leanmind.errorhandling.RoutesConfiguration;
import es.leanmind.errorhandling.UnitTest;
import es.leanmind.errorhandling.errors.ErrorHttpHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserHttpHandler.class)
@Import({RoutesConfiguration.class, ErrorHttpHandler.class})
class UserHttpHandlerTest extends UnitTest {

    @Autowired WebTestClient webTestClient;
    @MockBean FindUser findUser;

    @Test
    void returns_a_user_when_user_is_found() {
        when(findUser.execute("1")).thenReturn(Mono.just(new User("1", "First User")));

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
        when(findUser.execute("99999")).thenReturn(Mono.error(UserNotFound.because("Something went wrong")));

        webTestClient.get()
            .uri("/api/v1/users/99999")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("code").isEqualTo("ERROR_0002")
            .jsonPath("message").isEqualTo("User not found");
    }

    @Test
    void returns_internal_error_when_something_unexpected_happens() {
        when(findUser.execute("1")).thenReturn(Mono.error(new RuntimeException("Boom!")));

        webTestClient.get()
            .uri("/api/v1/users/1")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
            .expectBody()
            .jsonPath("code").isEqualTo("ERROR_0001")
            .jsonPath("message").isEqualTo("Internal Error");
    }
}
