package com.devamatre.appsuite.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rohtash Lakra
 * @created 10/16/21 4:19 PM
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends ServerException {

    public AuthenticationException() {
        super();
    }

    /**
     * @param message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * @param pattern
     * @param args
     */
    public AuthenticationException(final String pattern, final Object... args) {
        this(String.format("%s!", String.format(pattern, args)));
    }

    /**
     * @param message
     * @param cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param pattern
     * @param args
     */
    public AuthenticationException(final String pattern, final Throwable cause, final Object... args) {
        this(String.format("%s!", String.format(pattern, args)), cause);
    }
}
