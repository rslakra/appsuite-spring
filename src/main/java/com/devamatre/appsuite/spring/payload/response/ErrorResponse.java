package com.devamatre.appsuite.spring.payload.response;

import com.devamatre.appsuite.spring.SpringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 6/17/20 2:33 PM
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String detail;
    private String help;

    /**
     * @param timestamp
     * @param status
     * @param error
     * @param message
     * @param path
     * @param detail
     * @param help
     */
    protected ErrorResponse(final LocalDateTime timestamp, final int status, final String error,
                            final String message, final String path, final String detail, final String help) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.detail = detail;
        this.help = help;
    }

    protected ErrorResponse() {
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }


    /**
     * @param timestamp
     * @param status
     * @param error
     * @param message
     * @param path
     * @param detail
     * @param help
     * @return
     */
    public static ErrorResponse of(final LocalDateTime timestamp, final HttpStatus status, final String error,
                                   final String message, final String path, final String detail, final String help) {
        return new ErrorResponse(timestamp, status.value(), error, message, path, detail, help);
    }

    /**
     * @param status
     * @param error
     * @param message
     * @param path
     * @param detail
     * @param help
     * @return
     */
    public static ErrorResponse of(final HttpStatus status, final String error, final String message, final String path,
                                   final String detail, final String help) {
        return of(LocalDateTime.now(), status, error, message, path, detail, help);
    }

    /**
     * @param status
     * @param error
     * @param message
     * @param path
     * @return
     */
    public static ErrorResponse of(final HttpStatus status, final String error, final String message,
                                   final String path) {
        return of(LocalDateTime.now(), status, error, message, path, null, null);
    }

    /**
     * @param status
     * @param error
     * @param message
     * @return
     */
    public static ErrorResponse of(final HttpStatus status, final String error, final String message) {
        return of(LocalDateTime.now(), status, error, message, null, null, null);
    }

    /**
     * @param status
     * @param error
     * @param allErrors
     * @return
     */
    public static ErrorResponse of(final HttpStatus status, final String error, final List<ObjectError> allErrors) {
        return of(status, error, SpringUtils.toMessage(allErrors), null, null, null);
    }
}
