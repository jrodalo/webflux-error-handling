package es.leanmind.errorhandling;

import es.leanmind.errorhandling.errors.ErrorHttpHandler;
import es.leanmind.errorhandling.errors.HttpNotFound;
import es.leanmind.errorhandling.users.UserHttpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfiguration {

    private final UserHttpHandler userHttpHandler;
    private final ErrorHttpHandler errorHttpHandler;

    public RoutesConfiguration(UserHttpHandler userHttpHandler, ErrorHttpHandler errorHttpHandler) {
        this.userHttpHandler = userHttpHandler;
        this.errorHttpHandler = errorHttpHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> httpRoutes() {
        return route()
            .GET("/api/v1/users/{id}", userHttpHandler::find)
            .route(all(), RoutesConfiguration::defaultFallbackError)
            .filter(errorHttpHandler::handle)
            .build();
    }

    private static Mono<ServerResponse> defaultFallbackError(ServerRequest request) {
        return Mono.error(HttpNotFound.because("The requested path does not exist: " + request.path()));
    }
}
