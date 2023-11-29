package es.leanmind.errorhandling;

import es.leanmind.errorhandling.errors.ErrorHttpHandler;
import es.leanmind.errorhandling.users.UserHttpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

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
            .filter(errorHttpHandler::handle)
            .build();
    }
}
