package com.rslakra.appsuite.spring.filter;

import com.rslakra.appsuite.core.Payload;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 10:32 AM
 */
public interface Filter<E> extends Serializable {

    String ID = "id";

    /**
     * Applies the filter on the provided <code>E</code>.
     *
     * @param e
     * @return
     */
    boolean apply(E e);

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
     * Returns the value of the provided <code>key</code> as the specified type <code>T</code>.
     * <p>
     * This method uses a method-level generic <code>T</code> to avoid shadowing the class-level generic <code>E</code>.
     * </p>
     *
     * @param keyName   the key name
     * @param classType the target class type
     * @param <T>       the return type (method-level generic to avoid shadowing class-level generic E)
     * @return the value cast/converted to type T, or Boolean.FALSE for missing Boolean keys, or null
     * @throws ClassCastException if the value cannot be converted to the requested type
     */
    <T> T getValue(String keyName, Class<T> classType);

    /**
     * @param keyName
     * @return
     */
    Long getLong(String keyName);

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>Boolean</code>.
     *
     * @param keyName
     * @return
     */
    boolean getBoolean(String keyName);

    /**
     * Returns the value of the provided <code>key</code> as a <code>String</code>.
     *
     * @param keyName the key name
     * @return the String value, or null if the key doesn't exist
     */
    String getString(String keyName);

    /**
     * Rebuilds the filter with the provided <code>Payload<String, Object></code> params.
     * <p>
     * This method allows reusing the same filter object with different filter criteria.
     * The existing payload is cleared and repopulated with the new parameters.
     * </p>
     *
     * @param allParams the new filter parameters
     */
    void rebuild(Payload<String, Object> allParams);

    /**
     * Rebuilds the filter with the provided <code>Map<String, Object></code> params.
     * <p>
     * This method allows reusing the same filter object with different filter criteria.
     * The existing payload is cleared and repopulated with the new parameters.
     * </p>
     *
     * @param allParams the new filter parameters
     */
    void rebuild(Map<String, Object> allParams);

}
