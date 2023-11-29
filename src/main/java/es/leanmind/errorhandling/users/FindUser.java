package es.leanmind.errorhandling.users;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindUser {
    private final Users users;

    public FindUser(Users users) {
        this.users = users;
    }

    public Mono<User> execute(String userId) {
        return users.findById(userId)
            .switchIfEmpty(Mono.error(UserNotFound.because("there is no user with id = " + userId)));
    }
}
