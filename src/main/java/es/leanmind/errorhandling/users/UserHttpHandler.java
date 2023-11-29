package es.leanmind.errorhandling.users;

import es.leanmind.errorhandling.errors.HttpNotFound;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHttpHandler {
    private final FindUser findUser;

    public UserHttpHandler(FindUser findUser) {
        this.findUser = findUser;
    }

    public Mono<ServerResponse> find(ServerRequest serverRequest) {
        return findUser
            .execute(serverRequest.pathVariable("id"))
            .flatMap(this::toSuccessResponse)
            .onErrorMap(UserNotFound.class, HttpNotFound::fromKnownError);
    }

    private Mono<ServerResponse> toSuccessResponse(User user) {
        return ServerResponse.ok().bodyValue(user);
    }
}
