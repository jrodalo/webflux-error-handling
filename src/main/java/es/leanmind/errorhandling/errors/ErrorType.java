package es.leanmind.errorhandling.errors;

public enum ErrorType {
    INTERNAL_ERROR("ERROR_0001", "Internal Error"),
    USER_NOT_FOUND("ERROR_0002", "User not found"),
    INVALID_REQUEST("ERROR_0004", "Invalid request");

    private final String code;
    private final String message;

    ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
