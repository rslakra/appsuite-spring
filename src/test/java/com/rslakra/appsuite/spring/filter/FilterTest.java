package com.rslakra.appsuite.spring.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rslakra.appsuite.core.Payload;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Comprehensive tests for Filter interface.
 *
 * @author Rohtash Lakra
 * @created 1/1/25
 */
public class FilterTest {

    /**
     * Tests the apply method of Filter interface with custom TestUser type.
     */
    @Test
    public void testFilterApplyWithCustomUserType() {
        // Create a filter that applies to TestUser
        Filter<TestUser> userFilter = new DefaultFilter<>(createUserPayload());
        
        // Create test users
        TestUser activeUser = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 30, true);
        TestUser inactiveUser = new TestUser(2L, "Jane Doe", "jane@example.com", 25, false);
        
        // Default apply method returns false
        assertFalse(userFilter.apply(activeUser));
        assertFalse(userFilter.apply(inactiveUser));
    }

    /**
     * Tests the apply method with TestProduct type.
     */
    @Test
    public void testFilterApplyWithCustomProductType() {
        Filter<TestProduct> productFilter = new DefaultFilter<>(createProductPayload());
        
        TestProduct product = new TestProduct("P001", "Test Product", null, 10, null, true);
        
        // Default apply method returns false
        assertFalse(productFilter.apply(product));
    }

    /**
     * Tests Filter with String type.
     */
    @Test
    public void testFilterApplyWithStringType() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        map.put("value", "value1");
        
        Filter<String> stringFilter = new DefaultFilter<>(map);
        
        // Default apply method returns false
        assertFalse(stringFilter.apply("test"));
    }

    /**
     * Tests Filter with Integer type.
     */
    @Test
    public void testFilterApplyWithIntegerType() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 100);
        map.put("count", 50);
        
        Filter<Integer> integerFilter = new DefaultFilter<>(map);
        
        // Default apply method returns false
        assertFalse(integerFilter.apply(100));
        assertFalse(integerFilter.apply(200));
    }

    /**
     * Tests Filter interface methods with various types.
     */
    @Test
    public void testFilterInterfaceMethods() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", 123);
        map.put("key3", true);
        
        Filter<Object> filter = new DefaultFilter<>(map);
        
        assertNotNull(filter);
        assertTrue(filter.hasKey("key1"));
        assertTrue(filter.hasKey("key2"));
        assertTrue(filter.hasKey("key3"));
        assertFalse(filter.hasKey("nonExistent"));
        
        assertTrue(filter.hasKeys("key1", "key2", "key3"));
        assertFalse(filter.hasKeys("key1", "nonExistent"));
        
        assertEquals("value1", filter.getValue("key1"));
        assertEquals(123, filter.getValue("key2"));
        assertEquals(true, filter.getValue("key3"));
    }

    /**
     * Creates a payload for TestUser.
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
     * Creates a payload for TestProduct.
     */
    private Payload<String, Object> createProductPayload() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("productId", "P001");
        payload.ofPair("productName", "Test Product");
        payload.ofPair("quantity", 10);
        payload.ofPair("inStock", true);
        return payload;
    }
}

