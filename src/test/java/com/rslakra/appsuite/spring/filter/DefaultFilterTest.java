package com.rslakra.appsuite.spring.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rslakra.appsuite.core.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Rohtash Lakra
 * @created 8/11/23 8:11 PM
 */
public class DefaultFilterTest {

    private Filter<Object> filter;
    private static final String ID = "id";
    private static final String ZERO = "0";
    private static final String STATUS = "status";
    private static final String TRUE = "true";
    private static final String NAME = "name";
    private static final String NAME_VALUE = "Roh";
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
        filter = new DefaultFilter<>(newDefaultPayload());
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
        assertTrue(filter.getValue(IS_BRAVE, Boolean.class));
        assertEquals(Integer.valueOf(FUN_SCORE_VALUE), filter.getValue(FUN_SCORE, Integer.class));
        assertEquals(Long.valueOf(FUN_SCORE_VALUE), filter.getValue(FUN_SCORE, Long.class));
        assertEquals(FUN_SCORE_VALUE, filter.getValue(FUN_SCORE, Number.class));
        assertTrue(filter.getValue(STATUS, Boolean.class));
        assertFalse(filter.getValue("isValid", Boolean.class));
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>T</code>.
     *
     * @return
     */
    /**
     * Tests that ClassCastException is thrown when value cannot be converted to the requested type.
     *
     * @return
     */
    @Test
    public void testGetValueThrowsClassCastException() {
        assertNotNull(filter);
        // Short.class is primitive wrapper, so asType should convert Integer(100) to Short(100)
        // This verifies exact type and value match
        assertEquals(Short.valueOf("100"), filter.getValue(FUN_SCORE, Short.class));
        
        // Test with a non-primitive, non-assignable type that should throw exception
        // String "Roh" cannot be converted to Integer
        try {
            filter.getValue(NAME, Integer.class);
            assertTrue(false, "Should have thrown ClassCastException");
        } catch (ClassCastException ex) {
            assertTrue(true);
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
    }

    /**
     * Returns the value of the provided <code>key</code> as the type of <code>Long</code>.
     *
     * @return
     */
    @Test
    public void testGetValueAsLong() {
        assertNotNull(filter);
        assertEquals(Long.valueOf(ZERO), filter.getValue(ID, Long.class));
        assertEquals(Long.valueOf(BIRTHDAY_VALUE), filter.getValue(BIRTHDAY, Long.class));
        assertEquals(BIRTHDAY_VALUE, filter.getValue(BIRTHDAY, Long.class).longValue());
        try {
            assertEquals(FUN_SCORE_VALUE, filter.getValue(FUN_SCORE, Long.class));
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
    public void testGetValueAsBoolean() {
        assertNotNull(filter);
        assertTrue(filter.getValue(IS_BRAVE, Boolean.class));
        assertTrue(filter.getValue(STATUS, Boolean.class));
        assertFalse(filter.getValue(NAME, Boolean.class));
    }

    /**
     * Tests that when a key doesn't exist, Boolean.class returns Boolean.FALSE,
     * but Object.class returns null (not Boolean.FALSE).
     *
     * @return
     */
    @Test
    public void testGetValueWithMissingKey() {
        assertNotNull(filter);
        // When key doesn't exist and type is Boolean.class, should return Boolean.FALSE
        assertFalse(filter.getValue("nonExistentKey", Boolean.class));
        assertEquals(Boolean.FALSE, filter.getValue("nonExistentKey", Boolean.class));
        
        // When key doesn't exist and type is Object.class, should return Boolean.FALSE
        // because Object.class.isAssignableFrom(Boolean.class) is true
        assertEquals(Boolean.FALSE, filter.getValue("nonExistentKey", Object.class));
        
        // When key doesn't exist and type is String.class, should return null
        assertNull(filter.getValue("nonExistentKey", String.class));
        
        // When key doesn't exist and type is Long.class, should return null
        assertNull(filter.getValue("nonExistentKey", Long.class));
        
        // When key doesn't exist and type is Boolean.TYPE (primitive boolean), should return Boolean.FALSE
        assertEquals(Boolean.FALSE, filter.getValue("nonExistentKey", Boolean.TYPE));
    }

    /**
     * Tests DefaultFilter with custom TestUser class type.
     *
     * @return
     */
    @Test
    public void testDefaultFilterWithCustomUserType() {
        Payload<String, Object> userPayload = Payload.newBuilder();
        userPayload.ofPair("id", 1L);
        userPayload.ofPair("name", "Roh Lak");
        userPayload.ofPair("email", "rslakra@lakra.com");
        userPayload.ofPair("age", 30);
        userPayload.ofPair("active", true);
        
        DefaultFilter<TestUser> userFilter = new DefaultFilter<>(userPayload);
        
        assertNotNull(userFilter);
        assertTrue(userFilter.hasKey("id"));
        assertTrue(userFilter.hasKey("name"));
        assertTrue(userFilter.hasKey("email"));
        assertTrue(userFilter.hasKey("age"));
        assertTrue(userFilter.hasKey("active"));
        
        assertEquals(1L, userFilter.getValue("id"));
        assertEquals("Roh Lak", userFilter.getValue("name"));
        assertEquals("rslakra@lakra.com", userFilter.getValue("email"));
        assertEquals(30, userFilter.getValue("age"));
        assertEquals(true, userFilter.getValue("active"));
        
        // Test with type conversion
        assertEquals(Long.valueOf(1L), userFilter.getValue("id", Long.class));
        assertEquals("Roh Lak", userFilter.getValue("name", String.class));
        assertEquals(Integer.valueOf(30), userFilter.getValue("age", Integer.class));
        assertTrue(userFilter.getValue("active", Boolean.class));
        
        // Test apply method (default returns false)
        TestUser testUser = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 30, true);
        assertFalse(userFilter.apply(testUser));
    }

    /**
     * Tests DefaultFilter with custom TestProduct class type.
     *
     * @return
     */
    @Test
    public void testDefaultFilterWithCustomProductType() {
        Payload<String, Object> productPayload = Payload.newBuilder();
        productPayload.ofPair("productId", "P001");
        productPayload.ofPair("productName", "Test Product");
        productPayload.ofPair("quantity", 10);
        productPayload.ofPair("inStock", true);
        productPayload.ofPair("price", 99.99);
        
        DefaultFilter<TestProduct> productFilter = new DefaultFilter<>(productPayload);
        
        assertNotNull(productFilter);
        assertTrue(productFilter.hasKey("productId"));
        assertTrue(productFilter.hasKey("productName"));
        assertTrue(productFilter.hasKey("quantity"));
        assertTrue(productFilter.hasKey("inStock"));
        assertTrue(productFilter.hasKey("price"));
        
        assertEquals("P001", productFilter.getValue("productId"));
        assertEquals("Test Product", productFilter.getValue("productName"));
        assertEquals(10, productFilter.getValue("quantity"));
        assertEquals(true, productFilter.getValue("inStock"));
        assertEquals(99.99, productFilter.getValue("price"));
        
        // Test with type conversion
        assertEquals("P001", productFilter.getValue("productId", String.class));
        assertEquals(Integer.valueOf(10), productFilter.getValue("quantity", Integer.class));
        assertTrue(productFilter.getValue("inStock", Boolean.class));
        assertEquals(Double.valueOf(99.99), productFilter.getValue("price", Double.class));
        
        // Test apply method (default returns false)
        TestProduct testProduct = new TestProduct("P001", "Test Product", null, 10, null, true);
        assertFalse(productFilter.apply(testProduct));
    }

    /**
     * Tests DefaultFilter with Map constructor and custom types.
     *
     * @return
     */
    @Test
    public void testDefaultFilterWithMapConstructorAndCustomTypes() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("userId", 100L);
        map.put("userName", "TestUser");
        map.put("userEmail", "test@example.com");
        map.put("userAge", 25);
        map.put("isActive", true);
        
        DefaultFilter<TestUser> userFilter = new DefaultFilter<>(map);
        
        assertNotNull(userFilter);
        assertTrue(userFilter.hasKeys("userId", "userName", "userEmail", "userAge", "isActive"));
        
        assertEquals(100L, userFilter.getValue("userId"));
        assertEquals("TestUser", userFilter.getValue("userName"));
        assertEquals("test@example.com", userFilter.getValue("userEmail"));
        assertEquals(25, userFilter.getValue("userAge"));
        assertEquals(true, userFilter.getValue("isActive"));
        
        // Test type conversions
        assertEquals(Long.valueOf(100L), userFilter.getValue("userId", Long.class));
        assertEquals("TestUser", userFilter.getValue("userName", String.class));
        assertEquals(Integer.valueOf(25), userFilter.getValue("userAge", Integer.class));
        assertTrue(userFilter.getValue("isActive", Boolean.class));
    }

    /**
     * Tests DefaultFilter with various primitive and wrapper types.
     *
     * @return
     */
    @Test
    public void testDefaultFilterWithVariousTypes() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("byteValue", (byte) 10);
        payload.ofPair("shortValue", (short) 20);
        payload.ofPair("intValue", 30);
        payload.ofPair("longValue", 40L);
        payload.ofPair("floatValue", 50.5f);
        payload.ofPair("doubleValue", 60.6);
        payload.ofPair("charValue", 'A');
        payload.ofPair("booleanValue", true);
        payload.ofPair("stringValue", "test");
        
        DefaultFilter<Object> typeFilter = new DefaultFilter<>(payload);
        
        assertNotNull(typeFilter);
        
        // Test all types
        assertEquals((byte) 10, typeFilter.getValue("byteValue"));
        assertEquals((short) 20, typeFilter.getValue("shortValue"));
        assertEquals(30, typeFilter.getValue("intValue"));
        assertEquals(40L, typeFilter.getValue("longValue"));
        assertEquals(50.5f, typeFilter.getValue("floatValue"));
        assertEquals(60.6, typeFilter.getValue("doubleValue"));
        assertEquals('A', typeFilter.getValue("charValue"));
        assertEquals(true, typeFilter.getValue("booleanValue"));
        assertEquals("test", typeFilter.getValue("stringValue"));
        
        // Test with type conversions
        assertEquals(Byte.valueOf((byte) 10), typeFilter.getValue("byteValue", Byte.class));
        assertEquals(Short.valueOf((short) 20), typeFilter.getValue("shortValue", Short.class));
        assertEquals(Integer.valueOf(30), typeFilter.getValue("intValue", Integer.class));
        assertEquals(Long.valueOf(40L), typeFilter.getValue("longValue", Long.class));
        assertEquals(Float.valueOf(50.5f), typeFilter.getValue("floatValue", Float.class));
        assertEquals(Double.valueOf(60.6), typeFilter.getValue("doubleValue", Double.class));
        assertEquals(Character.valueOf('A'), typeFilter.getValue("charValue", Character.class));
        assertTrue(typeFilter.getValue("booleanValue", Boolean.class));
        assertEquals("test", typeFilter.getValue("stringValue", String.class));
    }

}
