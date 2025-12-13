package com.rslakra.appsuite.spring.filter;

import java.io.Serializable;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 10:32 AM
 */
public interface Filter<E> extends Serializable {

    String ID = "id";

    /**
     * Returns true if the filter contains the provided <code>key</code>.
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * Returns true if the filter contains all the provided <code>keys</code>.
     *
     * @param keys
     * @return
     */
    boolean hasKeys(String... keys);

    /**
     * Returns the value of the provided <code>key</code>.
     *
     * @param keyName
     * @return
     */
    Object getValue(String keyName);

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>E</code>.
     *
     * @param keyName
     * @param classType
     * @param <E>
     * @return
     */
    <E> E getValue(String keyName, Class<E> classType);

    /**
     * Applies the filter on the provided <code>E</code>.
     *
     * @param e
     * @return
     */
    default boolean apply(E e) {
        return false;
    }

}
