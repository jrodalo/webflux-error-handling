package es.leanmind.errorhandling.errors;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ErrorHttpHandler {

    public Mono<ServerResponse> handle(ServerRequest request, HandlerFunction<ServerResponse> handler) {
        return handler.handle(request)
            .onErrorResume(HttpError.class, this::toServerResponse)
            .onErrorResume(Throwable.class, error -> toServerResponse(HttpError.fromUnknown(error)));
    }

    private Mono<ServerResponse> toServerResponse(HttpError httpError) {
        return ServerResponse
            .status(httpError.getHttpStatus())
            .bodyValue(httpError.getBody());
    }
}
