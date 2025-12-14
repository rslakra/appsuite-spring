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
    public AbstractFilter(Payload<String, Object> allParams) {
        payload = Payload.newBuilder().ofPayload(allParams);
    }

    /**
     * The payload is initialized with the provided <code>Map<String, V></code> params.
     *
     * @param allParams
     */
    public AbstractFilter(Map<String, Object> allParams) {
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
     * Applies the filter on the provided <code>E</code>.
     * <p>
     * This implementation checks if the element matches all filter criteria defined in the payload.
     * For each key-value pair in the filter payload, it extracts the corresponding property from
     * the element and compares it with the filter value. Returns true only if all criteria match.
     * </p>
     * <p>
     * If the element is null, returns false. If the payload is empty, returns true (no criteria means all match).
     * If a property doesn't exist on the element or cannot be read, that criterion is considered a mismatch.
     * </p>
     *
     * @param e the element to filter
     * @return true if the element matches all filter criteria, false otherwise
     */
    @Override
    public boolean apply(E e) {
        // Null element doesn't match any filter
        if (BeanUtils.isNull(e)) {
            return false;
        }

        // Empty payload means no filter criteria, so everything matches
        if (payload.isEmpty()) {
            return true;
        }

        // Check each filter criterion
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            String key = entry.getKey();
            Object filterValue = entry.getValue();

            try {
                // Try to read the property from the element
                Object elementValue = BeanUtils.readObjectProperty(e, key);

                // Compare values using BeanUtils.equals which handles null, BigDecimal, Date, etc.
                if (!BeanUtils.equals(elementValue, filterValue)) {
                    return false;
                }
            } catch (Exception ex) {
                // Property doesn't exist or cannot be read - consider it a mismatch
                // This handles cases where the element doesn't have the property
                return false;
            }
        }

        // All criteria matched
        return true;
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
    @SuppressWarnings("varargs")
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
     * Returns the value of the provided <code>key</code> as the specified type <code>T</code>.
     * <p>
     * If the key exists and the value can be cast or converted to the requested type, it is returned.
     * If the key doesn't exist and the requested type is Boolean (or a supertype of Boolean), 
     * returns Boolean.FALSE. Otherwise, returns null.
     * </p>
     *
     * @param keyName   the key name
     * @param classType the target class type
     * @param <T>       the return type (method-level generic to avoid shadowing class-level generic E)
     * @return the value cast/converted to type T, or Boolean.FALSE for missing Boolean keys, or null
     * @throws ClassCastException if the value cannot be converted to the requested type
     */
    @Override
    public <T> T getValue(String keyName, Class<T> classType) {
        if (hasKey(keyName)) {
            Object value = getValue(keyName);
            if (value == null) {
                return null;
            }
            if (BeanUtils.isKindOf(value, classType)) {
                return classType.cast(value);
            } else if (BeanUtils.isPrimitive(classType)) {
                return BeanUtils.asType(Objects.toString(value), classType);
            }

            throw new ClassCastException(errorMessage(value, classType));
        } else if (BeanUtils.isAssignable(Boolean.class, classType) || classType == Boolean.TYPE) {
            return (T) Boolean.FALSE;
        }

        return null;
    }

    /**
     * Returns the value of the provided <code>key</code> as a <code>Long</code>.
     *
     * @param keyName the key name
     * @return the Long value, or null if the key doesn't exist
     */
    @Override
    public Long getLong(String keyName) {
        return getValue(keyName, Long.class);
    }

    /**
     * Returns the value of the provided <code>key</code> as a <code>boolean</code>.
     * <p>
     * If the key doesn't exist, returns false (as per the default behavior for Boolean types).
     * </p>
     *
     * @param keyName the key name
     * @return the boolean value, or false if the key doesn't exist
     */
    @Override
    public boolean getBoolean(String keyName) {
        return getValue(keyName, Boolean.class);
    }

    /**
     * Returns the value of the provided <code>key</code> as a <code>String</code>.
     *
     * @param keyName the key name
     * @return the String value, or null if the key doesn't exist
     */
    @Override
    public String getString(String keyName) {
        return getValue(keyName, String.class);
    }

    /**
     * Rebuilds the filter with the provided <code>Payload<String, Object></code> params.
     * <p>
     * This method allows reusing the same filter object with different filter criteria.
     * The existing payload is cleared and repopulated with the new parameters.
     * </p>
     *
     * @param allParams the new filter parameters
     */
    @Override
    public void rebuild(Payload<String, Object> allParams) {
        payload.clear();
        if (BeanUtils.isNotNull(allParams)) {
            payload.putAll(allParams);
        }
    }

    /**
     * Rebuilds the filter with the provided <code>Map<String, Object></code> params.
     * <p>
     * This method allows reusing the same filter object with different filter criteria.
     * The existing payload is cleared and repopulated with the new parameters.
     * </p>
     *
     * @param allParams the new filter parameters
     */
    @Override
    public void rebuild(Map<String, Object> allParams) {
        payload.clear();
        if (BeanUtils.isNotNull(allParams)) {
            payload.putAll(allParams);
        }
    }

}
