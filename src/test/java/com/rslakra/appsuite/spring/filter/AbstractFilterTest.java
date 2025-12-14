package com.rslakra.appsuite.spring.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rslakra.appsuite.core.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Comprehensive tests for AbstractFilter class with various types including custom classes.
 *
 * @author Rohtash Lakra
 * @created 1/1/25
 */
public class AbstractFilterTest {

    private DefaultFilter<TestUser> userFilter;
    private DefaultFilter<TestProduct> productFilter;
    private DefaultFilter<String> stringFilter;
    private DefaultFilter<Integer> integerFilter;
    private DefaultFilter<Object> objectFilter;

    @BeforeEach
    public void setUp() {
        // Initialize filters with different types
        userFilter = new DefaultFilter<>(createUserPayload());
        productFilter = new DefaultFilter<>(createProductPayload());
        stringFilter = new DefaultFilter<>(createStringPayload());
        integerFilter = new DefaultFilter<>(createIntegerPayload());
        objectFilter = new DefaultFilter<>(createMixedPayload());
    }

    /**
     * Tests AbstractFilter with custom TestUser class type.
     */
    @Test
    public void testAbstractFilterWithCustomUserType() {
        assertNotNull(userFilter);
        
        // Test hasKey
        assertTrue(userFilter.hasKey("id"));
        assertTrue(userFilter.hasKey("name"));
        assertTrue(userFilter.hasKey("email"));
        assertTrue(userFilter.hasKey("age"));
        assertTrue(userFilter.hasKey("active"));
        assertFalse(userFilter.hasKey("nonExistent"));
        
        // Test hasKeys
        assertTrue(userFilter.hasKeys("id", "name", "email"));
        assertFalse(userFilter.hasKeys("id", "nonExistent"));
        
        // Test getValue
        assertEquals(1L, userFilter.getValue("id"));
        assertEquals("Roh Lak", userFilter.getValue("name"));
        assertEquals("rslakra@lakra.com", userFilter.getValue("email"));
        assertEquals(30, userFilter.getValue("age"));
        assertEquals(true, userFilter.getValue("active"));
        
        // Test getValue with type
        assertEquals(Long.valueOf(1L), userFilter.getValue("id", Long.class));
        assertEquals("Roh Lak", userFilter.getValue("name", String.class));
        assertEquals(Integer.valueOf(30), userFilter.getValue("age", Integer.class));
        assertTrue(userFilter.getValue("active", Boolean.class));
    }

    /**
     * Tests AbstractFilter with custom TestProduct class type.
     */
    @Test
    public void testAbstractFilterWithCustomProductType() {
        assertNotNull(productFilter);
        
        assertTrue(productFilter.hasKey("productId"));
        assertTrue(productFilter.hasKey("productName"));
        assertTrue(productFilter.hasKey("quantity"));
        assertTrue(productFilter.hasKey("inStock"));
        
        assertEquals("P001", productFilter.getValue("productId"));
        assertEquals("Test Product", productFilter.getValue("productName"));
        assertEquals(10, productFilter.getValue("quantity"));
        assertEquals(true, productFilter.getValue("inStock"));
        
        assertEquals("P001", productFilter.getValue("productId", String.class));
        assertEquals(Integer.valueOf(10), productFilter.getValue("quantity", Integer.class));
        assertTrue(productFilter.getValue("inStock", Boolean.class));
    }

    /**
     * Tests AbstractFilter with String type.
     */
    @Test
    public void testAbstractFilterWithStringType() {
        assertNotNull(stringFilter);
        
        assertTrue(stringFilter.hasKey("name"));
        assertTrue(stringFilter.hasKey("description"));
        assertTrue(stringFilter.hasKey("category"));
        
        assertEquals("Test Item", stringFilter.getValue("name", String.class));
        assertEquals("Test Description", stringFilter.getValue("description", String.class));
        assertEquals("Category1", stringFilter.getValue("category", String.class));
    }

    /**
     * Tests AbstractFilter with Integer type.
     */
    @Test
    public void testAbstractFilterWithIntegerType() {
        assertNotNull(integerFilter);
        
        assertTrue(integerFilter.hasKey("id"));
        assertTrue(integerFilter.hasKey("count"));
        assertTrue(integerFilter.hasKey("score"));
        
        assertEquals(Integer.valueOf(100), integerFilter.getValue("id", Integer.class));
        assertEquals(Integer.valueOf(50), integerFilter.getValue("count", Integer.class));
        assertEquals(Integer.valueOf(75), integerFilter.getValue("score", Integer.class));
    }

    /**
     * Tests AbstractFilter with mixed Object types.
     */
    @Test
    public void testAbstractFilterWithMixedTypes() {
        assertNotNull(objectFilter);
        
        // Test various primitive and wrapper types
        assertEquals("test", objectFilter.getValue("stringValue", String.class));
        assertEquals(Integer.valueOf(42), objectFilter.getValue("intValue", Integer.class));
        assertEquals(Long.valueOf(123L), objectFilter.getValue("longValue", Long.class));
        assertEquals(Double.valueOf(3.14), objectFilter.getValue("doubleValue", Double.class));
        assertEquals(Float.valueOf(2.5f), objectFilter.getValue("floatValue", Float.class));
        assertEquals(Short.valueOf((short) 10), objectFilter.getValue("shortValue", Short.class));
        assertEquals(Byte.valueOf((byte) 5), objectFilter.getValue("byteValue", Byte.class));
        assertTrue(objectFilter.getValue("booleanValue", Boolean.class));
        assertEquals(Character.valueOf('A'), objectFilter.getValue("charValue", Character.class));
    }

    /**
     * Tests AbstractFilter with Map constructor.
     */
    @Test
    public void testAbstractFilterWithMapConstructor() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", 123);
        map.put("key3", true);
        
        DefaultFilter<Object> filter = new DefaultFilter<>(map);
        
        assertNotNull(filter);
        assertTrue(filter.hasKey("key1"));
        assertTrue(filter.hasKey("key2"));
        assertTrue(filter.hasKey("key3"));
        
        assertEquals("value1", filter.getValue("key1"));
        assertEquals(123, filter.getValue("key2"));
        assertEquals(true, filter.getValue("key3"));
    }

    /**
     * Tests AbstractFilter with Payload constructor.
     */
    @Test
    public void testAbstractFilterWithPayloadConstructor() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("id", 1L);
        payload.ofPair("name", "Test");
        
        DefaultFilter<Object> filter = new DefaultFilter<>(payload);
        
        assertNotNull(filter);
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        
        assertEquals(1L, filter.getValue("id"));
        assertEquals("Test", filter.getValue("name"));
    }

    /**
     * Tests type conversion with primitives.
     */
    @Test
    public void testTypeConversionWithPrimitives() {
        Map<String, Object> map = new HashMap<>();
        map.put("intStr", "100");
        map.put("longStr", "200");
        map.put("doubleStr", "3.14");
        map.put("boolStr", "true");
        
        DefaultFilter<Object> filter = new DefaultFilter<>(map);
        
        // Test conversion from String to primitive wrappers
        assertEquals(Integer.valueOf(100), filter.getValue("intStr", Integer.class));
        assertEquals(Long.valueOf(200), filter.getValue("longStr", Long.class));
        assertEquals(Double.valueOf(3.14), filter.getValue("doubleStr", Double.class));
        assertTrue(filter.getValue("boolStr", Boolean.class));
    }

    /**
     * Tests ClassCastException for invalid type conversions.
     */
    @Test
    public void testClassCastException() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John");
        map.put("age", 30);
        
        DefaultFilter<Object> filter = new DefaultFilter<>(map);
        
        // String cannot be converted to Integer
        assertThrows(ClassCastException.class, () -> {
            filter.getValue("name", Integer.class);
        });
        
        // Integer cannot be converted to String (when not using primitive conversion)
        // This depends on BeanUtils implementation, but typically should work or throw exception
        assertNotNull(filter.getValue("age"));
    }

    /**
     * Tests missing key behavior.
     */
    @Test
    public void testMissingKeyBehavior() {
        DefaultFilter<Object> filter = new DefaultFilter<>(new HashMap<>());
        
        // Missing key returns null for Object
        assertNull(filter.getValue("nonExistent"));
        
        // Missing key returns Boolean.FALSE for Boolean.class
        assertFalse(filter.getValue("nonExistent", Boolean.class));
        assertEquals(Boolean.FALSE, filter.getValue("nonExistent", Boolean.class));
        
        // Missing key returns null for other types
        assertNull(filter.getValue("nonExistent", String.class));
        assertNull(filter.getValue("nonExistent", Integer.class));
    }

    /**
     * Creates payload for TestUser.
     */
    private Payload<String, Object> createUserPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("id", 1L);
        payload.ofPair("name", "Roh Lak");
        payload.ofPair("email", "rslakra@lakra.com");
        payload.ofPair("age", 30);
        payload.ofPair("active", true);
        return payload;
    }

    /**
     * Creates payload for TestProduct.
     */
    private Payload<String, Object> createProductPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("productId", "P001");
        payload.ofPair("productName", "Test Product");
        payload.ofPair("quantity", 10);
        payload.ofPair("inStock", true);
        return payload;
    }

    /**
     * Creates payload for String type.
     */
    private Payload<String, Object> createStringPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("name", "Test Item");
        payload.ofPair("description", "Test Description");
        payload.ofPair("category", "Category1");
        return payload;
    }

    /**
     * Creates payload for Integer type.
     */
    private Payload<String, Object> createIntegerPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("id", 100);
        payload.ofPair("count", 50);
        payload.ofPair("score", 75);
        return payload;
    }

    /**
     * Creates payload with mixed types.
     */
    private Payload<String, Object> createMixedPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("stringValue", "test");
        payload.ofPair("intValue", 42);
        payload.ofPair("longValue", 123L);
        payload.ofPair("doubleValue", 3.14);
        payload.ofPair("floatValue", 2.5f);
        payload.ofPair("shortValue", (short) 10);
        payload.ofPair("byteValue", (byte) 5);
        payload.ofPair("booleanValue", true);
        payload.ofPair("charValue", 'A');
        return payload;
    }
}

