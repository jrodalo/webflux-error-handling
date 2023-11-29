package es.leanmind.errorhandling.errors;

import org.springframework.http.HttpStatus;

public final class HttpNotFound extends HttpError {
    private HttpNotFound(String reason, ErrorType errorType) {
        super(reason, errorType, HttpStatus.NOT_FOUND);
    }

    public static HttpNotFound fromKnownError(KnownError error) {
        return new HttpNotFound(error.getMessage(), error.getType());
    }
}
