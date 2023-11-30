package es.leanmind.errorhandling.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ErrorHttpHandler {
    private final Logger logger = LoggerFactory.getLogger(ErrorHttpHandler.class);

    public Mono<ServerResponse> handle(ServerRequest request, HandlerFunction<ServerResponse> handler) {
        return handler.handle(request)
            .doOnError(this::logError)
            .onErrorResume(HttpError.class, this::toServerResponse)
            .onErrorResume(Throwable.class, error -> toServerResponse(HttpError.fromUnknown(error)));
    }

    private Mono<ServerResponse> toServerResponse(HttpError httpError) {
        return ServerResponse
            .status(httpError.getHttpStatus())
            .bodyValue(httpError.getBody());
    }

    private void logError(Throwable error) {
        logger.error(error.getMessage(), error);
    }
}
