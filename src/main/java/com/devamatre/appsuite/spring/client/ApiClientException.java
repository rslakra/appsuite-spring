package com.devamatre.appsuite.spring.client;

import org.springframework.web.client.RestClientException;

/**
 * @author Rohtash Lakra
 * @created 7/12/23 9:34 AM
 */
public class ApiClientException extends RestClientException {

    public ApiClientException(String msg) {
        super(msg);
    }

    public ApiClientException(String msg, Throwable ex) {
        super(msg, ex);
    }

}
