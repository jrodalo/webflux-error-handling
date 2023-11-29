package es.leanmind.errorhandling.errors;

import org.springframework.http.HttpStatus;

public sealed class HttpError extends RuntimeException permits HttpNotFound {
    private final ErrorType errorType;
    private final HttpStatus httpStatus;

    protected HttpError(String reason, ErrorType errorType, HttpStatus httpStatus) {
        super(reason);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }

    private HttpError(Throwable throwable, ErrorType errorType, HttpStatus httpStatus) {
        super(throwable);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }

    public static HttpError fromUnknown(Throwable throwable) {
        return new HttpError(throwable, ErrorType.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Error getBody() {
        return new Error(errorType.getCode(), errorType.getMessage());
    }

    public record Error(String code, String message) {
    }
}
