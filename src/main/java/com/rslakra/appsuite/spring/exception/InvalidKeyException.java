package com.rslakra.appsuite.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rohtash Lakra
 * @created 10/16/21 4:19 PM
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Key!")
public class InvalidKeyException extends ServerException {

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may
     * subsequently be initialized by a call to {@link #initCause}.
     */
    public InvalidKeyException() {
        super();
    }

    /**
     * @param pattern
     * @param args
     */
    public InvalidKeyException(final String pattern, final Object... args) {
        this(String.format("%s!", String.format(pattern, args)));
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     * @param message
     */
    public InvalidKeyException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * exception's detail message.
     *
     * @param message
     * @param cause
     */
    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains
     * the class and detail message of <tt>cause</tt>). This constructor is useful for exceptions that are little more
     * than wrappers for other throwables (for example, {@link java.security.PrivilegedActionException}).
     *
     * @param cause
     */
    public InvalidKeyException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, suppression enabled or disabled, and
     * writable stack trace enabled or disabled.
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected InvalidKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
