package com.rslakra.appsuite.spring.filter;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.core.Payload;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 10:33 AM
 */
public abstract class AbstractFilter<E> implements Filter<E> {

    private static final String
        INVALID_TYPE_MESSAGE =
        "The object <value=%s, class=%s> is not an instance of return type <%s>!";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String LAST_NAME = "lastName";
    public static final String NAME = "name";

    private final Payload<String, Object> payload;

    /**
     * The payload is initialized with the provided <code>Payload<String, V></code> params.
     *
     * @param allParams
     */
    public AbstractFilter(final Payload<String, Object> allParams) {
        payload = Payload.newBuilder().ofPayload(allParams);
    }

    /**
     * The payload is initialized with the provided <code>Map<String, V></code> params.
     *
     * @param allParams
     */
    public AbstractFilter(final Map<String, Object> allParams) {
        payload = Payload.newBuilder(allParams);
    }

    /**
     * @param value
     * @param classType
     * @param <T>
     * @return
     */
    private <T> String errorMessage(Object value, Class<T> classType) {
        return String.format(INVALID_TYPE_MESSAGE, value, value.getClass(), classType);
    }

    /**
     * Returns true if the filter contains the provided <code>key</code>.
     *
     * @param key
     * @return
     */
    @Override
    public boolean hasKey(String key) {
        return payload.containsKey(key);
    }

    /**
     * Returns true if the filter contains all the provided <code>keys</code>.
     *
     * @param keys
     * @return
     */
    @Override
    public boolean hasKeys(String... keys) {
        return (BeanUtils.isNotEmpty(keys) && payload.keySet().containsAll(Arrays.asList(keys)));
    }

    /**
     * Returns the value of the provided <code>key</code>.
     *
     * @param keyName
     * @return
     */
    @Override
    public Object getValue(String keyName) {
        return payload.get(keyName);
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>E</code>.
     *
     * @param keyName
     * @param classType
     * @param <T>
     * @return
     * @throws ClassCastException
     */
    @Override
    public <T> T getValue(String keyName, Class<T> classType) {
        if (hasKey(keyName)) {
            Object value = getValue(keyName);
            if (BeanUtils.isKindOf(value, classType)) {
                return (T) value;
            } else if (BeanUtils.isPrimitive(classType)) {
                return BeanUtils.asType(Objects.toString(value), classType);
            }

            throw new ClassCastException(errorMessage(value, classType));
        } else if (classType.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.FALSE;
        }

        return (T) null;
    }

    /**
     * @param keyName
     * @return
     */
    @Override
    public Long getLong(String keyName) {
        return getValue(keyName, Long.class);
    }

    /**
     * @param keyName
     * @return
     */
    @Override
    public boolean getBoolean(String keyName) {
        return getValue(keyName, Boolean.class);
    }
}
