package es.leanmind.errorhandling.users;

import es.leanmind.errorhandling.UnitTest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindUserTest extends UnitTest {

    @Test
    void finds_users_by_id() {
        var expectedUser = new User("1", "Test User");
        var users = mock(Users.class);
        when(users.findById("1")).thenReturn(Mono.just(expectedUser));
        var findUser = new FindUser(users);

        StepVerifier.create(findUser.execute("1"))
            .assertNext(user -> assertThat(user).isEqualTo(expectedUser))
            .verifyComplete();
    }

    @Test
    void produces_an_error_when_there_are_no_users_with_the_requested_id() {
        var users = mock(Users.class);
        when(users.findById("9999")).thenReturn(Mono.empty());
        var findUser = new FindUser(users);

        StepVerifier.create(findUser.execute("9999"))
            .expectErrorSatisfies(error -> assertThat(error).isInstanceOf(UserNotFound.class).hasMessage("there is no user with id = 9999"))
            .verify();
    }
}
