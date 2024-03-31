package com.devamatre.appsuite.spring.filter;

import com.devamatre.appsuite.core.Payload;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 8/11/23 8:14 PM
 */
public class DefaultFilter<E> extends AbstractFilter<E> {

    /**
     * The payload is initialized with the provided <code>Payload<String, V></code> params.
     *
     * @param allParams
     */
    public DefaultFilter(Payload<String, Object> allParams) {
        super(allParams);
    }

    /**
     * The payload is initialized with the provided <code>Map<String, V></code> params.
     *
     * @param allParams
     */
    public DefaultFilter(Map<String, Object> allParams) {
        super(allParams);
    }
}
