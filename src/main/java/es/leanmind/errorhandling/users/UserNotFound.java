package es.leanmind.errorhandling.users;

import es.leanmind.errorhandling.errors.ErrorType;
import es.leanmind.errorhandling.errors.KnownError;

public class UserNotFound extends KnownError {

    private UserNotFound(String reason) {
        super(reason, ErrorType.USER_NOT_FOUND);
    }

    public static UserNotFound because(String reason) {
        return new UserNotFound(reason);
    }
}
