package com.devamatre.appsuite.spring.filter;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 10:32 AM
 */
public interface Filter {

    public static final String ID = "id";

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
     * Returns the value of the provided <code>key</code> as the type of <code>T</code>.
     *
     * @param keyName
     * @param classType
     * @param <T>
     * @return
     */
    <T> T getValue(String keyName, Class<T> classType);

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>Long</code>.
     *
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

}
