package com.devamatre.appsuite.spring.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devamatre.framework.core.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Rohtash Lakra
 * @created 8/11/23 8:11 PM
 */
public class DefaultFilterTest {

    private Filter filter;
    private static final String ID = "id";
    private static final String ZERO = "0";
    private static final String STATUS = "status";
    private static final String TRUE = "true";
    private static final String NAME = "name";
    private static final String NAME_VALUE = "Rohtash";
    private static final String BIRTHDAY = "birthDay";
    private static final long BIRTHDAY_VALUE = Long.valueOf(16);
    private static final String IS_BRAVE = "isBrave";
    private static final String FUN_SCORE = "funScore";
    private static final int FUN_SCORE_VALUE = 100;
    private static final String NO_KEY = "noKey";

    /**
     * @return
     */
    public static Payload<String, Object> newDefaultPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair(ID, ZERO);
        payload.ofPair(NAME, NAME_VALUE);
        payload.ofPair(BIRTHDAY, BIRTHDAY_VALUE);
        payload.ofPair(IS_BRAVE, true);
        payload.ofPair(FUN_SCORE, FUN_SCORE_VALUE);
        payload.ofPair(STATUS, TRUE);
        return payload;
    }

    /**
     * Initializes the <code>DefaultFilter</code> object. payload.ofPair(FUN_SCORE, FUN_SCORE_VALUE);
     */
    @BeforeEach
    public void initFilter() {
        filter = new DefaultFilter(newDefaultPayload());
    }

    /**
     * @return
     */
    @Test
    public void testHasKey() {
        assertNotNull(filter);
        assertTrue(filter.hasKey(ID));
        assertTrue(filter.hasKey(NAME));
        assertTrue(filter.hasKey(BIRTHDAY));
        assertTrue(filter.hasKey(IS_BRAVE));
        assertTrue(filter.hasKey(FUN_SCORE));
        assertTrue(filter.hasKey(STATUS));
        assertFalse(filter.hasKey(NO_KEY));
    }

    /**
     * Returns true if the filter contains all the provided <code>keys</code>.
     *
     * @return
     */
    @Test
    public void testHasKeys() {
        assertNotNull(filter);
        assertTrue(filter.hasKeys(ID, NAME, BIRTHDAY, IS_BRAVE, FUN_SCORE, STATUS));
    }

    /**
     * Returns the value of the provided <code>key</code>.
     *
     * @return
     */
    @Test
    public void testGetValue() {
        assertNotNull(filter);
        assertEquals(ZERO, filter.getValue(ID));
        assertEquals(NAME_VALUE, filter.getValue(NAME));
        assertEquals(BIRTHDAY_VALUE, filter.getValue(BIRTHDAY));
        assertEquals(Boolean.TRUE, filter.getValue(IS_BRAVE));
        assertEquals(FUN_SCORE_VALUE, filter.getValue(FUN_SCORE));
        assertEquals(TRUE, filter.getValue(STATUS));
        assertNull(filter.getValue(NO_KEY));
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>T</code>.
     *
     * @return
     */
    @Test
    public void testGetValueWithClassType() {
        assertNotNull(filter);
        assertEquals(ZERO, filter.getValue(ID));
        assertEquals(NAME_VALUE, filter.getValue(NAME, String.class));
        String name = filter.getValue(NAME, String.class);
        assertEquals(NAME_VALUE, name);
        assertEquals(Long.valueOf(BIRTHDAY_VALUE), filter.getValue(BIRTHDAY, Long.class));
        assertEquals(Boolean.TRUE, filter.getValue(IS_BRAVE, Boolean.class));
        assertEquals(Integer.valueOf(FUN_SCORE_VALUE), filter.getValue(FUN_SCORE, Integer.class));
        assertEquals(Integer.valueOf(FUN_SCORE_VALUE), filter.getValue(FUN_SCORE, Number.class));
        assertEquals(FUN_SCORE_VALUE, filter.getValue(FUN_SCORE, Number.class));
        assertEquals(Boolean.TRUE, filter.getValue(STATUS, Boolean.class));
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>T</code>.
     *
     * @return
     */
    @Test
    public void testGetValueThrowsClassCastException() {
        assertNotNull(filter);
        try {
            assertEquals(Short.valueOf("100"), filter.getValue(FUN_SCORE, Short.class));
        } catch (ClassCastException ex) {
            assertTrue(true);
            assertEquals(
                "The object <value=100, class=class java.lang.Integer> is not an instance of return type <class java.lang.Short>!",
                ex.getMessage());
        }
        try {
            assertEquals(Long.valueOf("100"), filter.getValue(FUN_SCORE, Long.class));
        } catch (ClassCastException ex) {
            assertEquals(
                "The object <value=100, class=class java.lang.Integer> is not an instance of return type <class java.lang.Long>!",
                ex.getMessage());
        }
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>Long</code>.
     *
     * @return
     */
    @Test
    public void testGetLong() {
        assertNotNull(filter);
        assertEquals(Long.valueOf(ZERO), filter.getLong(ID));
        assertEquals(Long.valueOf(BIRTHDAY_VALUE), filter.getLong(BIRTHDAY));
        assertEquals(BIRTHDAY_VALUE, filter.getLong(BIRTHDAY).longValue());
        try {
            assertEquals(FUN_SCORE_VALUE, filter.getLong(FUN_SCORE));
        } catch (ClassCastException ex) {
            assertEquals(
                "The object <value=100, class=class java.lang.Integer> is not an instance of return type <class java.lang.Long>!",
                ex.getMessage());
        }
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>Boolean</code>.
     *
     * @return
     */
    @Test
    public void testGetBoolean() {
        assertNotNull(filter);
        assertTrue(filter.getBoolean(IS_BRAVE));
        assertTrue(filter.getBoolean(STATUS));
        assertFalse(filter.getBoolean(NAME));
    }

}
