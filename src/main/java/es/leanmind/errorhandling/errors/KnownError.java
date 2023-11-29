package es.leanmind.errorhandling.errors;

public class KnownError extends RuntimeException {

    private final ErrorType errorType;

    public KnownError(String reason, ErrorType errorType) {
        super(reason);
        this.errorType = errorType;
    }

    public ErrorType getType() {
        return errorType;
    }
}
