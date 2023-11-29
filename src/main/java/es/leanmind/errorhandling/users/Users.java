package es.leanmind.errorhandling.users;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class Users {
    private final Map<String, User> database = new ConcurrentHashMap<>(
        Map.of(
            "1", new User("1", "First User"),
            "2", new User("2", "Second User")
        )
    );

    public Mono<User> findById(String userId) {
        return Mono.justOrEmpty(database.get(userId));
    }
}
