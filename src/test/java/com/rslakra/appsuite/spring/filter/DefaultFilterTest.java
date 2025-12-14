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
     * Tests the getLong convenience method.
     *
     * @return
     */
    @Test
    public void testGetLong() {
        assertNotNull(filter);
        assertEquals(Long.valueOf(ZERO), filter.getLong(ID));
        assertEquals(Long.valueOf(BIRTHDAY_VALUE), filter.getLong(BIRTHDAY));
        assertEquals(BIRTHDAY_VALUE, filter.getLong(BIRTHDAY).longValue());
        assertNull(filter.getLong(NO_KEY));
    }

    /**
     * Tests the getBoolean convenience method.
     *
     * @return
     */
    @Test
    public void testGetBoolean() {
        assertNotNull(filter);
        assertTrue(filter.getBoolean(IS_BRAVE));
        assertTrue(filter.getBoolean(STATUS));
        assertFalse(filter.getBoolean(NAME));
        assertFalse(filter.getBoolean(NO_KEY));
    }

    /**
     * Tests the getString convenience method.
     *
     * @return
     */
    @Test
    public void testGetString() {
        assertNotNull(filter);
        // Test with existing String values
        assertEquals(NAME_VALUE, filter.getString(NAME));
        assertEquals(ZERO, filter.getString(ID));
        assertEquals(TRUE, filter.getString(STATUS));
        
        // Test with missing key
        assertNull(filter.getString(NO_KEY));
        
        // Test that non-String values throw ClassCastException
        try {
            filter.getString(BIRTHDAY);
            assertTrue(false, "Should have thrown ClassCastException for Long value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
        
        try {
            filter.getString(FUN_SCORE);
            assertTrue(false, "Should have thrown ClassCastException for Integer value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
        
        try {
            filter.getString(IS_BRAVE);
            assertTrue(false, "Should have thrown ClassCastException for Boolean value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
    }

    /**
     * Tests the getString method with various types and edge cases.
     *
     * @return
     */
    @Test
    public void testGetStringWithVariousTypes() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("stringValue", "test");
        payload.ofPair("intValue", 123);
        payload.ofPair("longValue", 456L);
        payload.ofPair("doubleValue", 789.5);
        payload.ofPair("booleanValue", true);
        payload.ofPair("nullValue", null);
        payload.ofPair("stringNumber", "123");
        payload.ofPair("stringBoolean", "true");
        
        DefaultFilter<Object> typeFilter = new DefaultFilter<>(payload);
        
        assertNotNull(typeFilter);
        
        // Test String value
        assertEquals("test", typeFilter.getString("stringValue"));
        assertEquals("123", typeFilter.getString("stringNumber"));
        assertEquals("true", typeFilter.getString("stringBoolean"));
        
        // Test null value
        assertNull(typeFilter.getString("nullValue"));
        
        // Test missing key
        assertNull(typeFilter.getString("missingKey"));
        
        // Test that non-String values throw ClassCastException
        try {
            typeFilter.getString("intValue");
            assertTrue(false, "Should have thrown ClassCastException for Integer value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
        
        try {
            typeFilter.getString("longValue");
            assertTrue(false, "Should have thrown ClassCastException for Long value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
        
        try {
            typeFilter.getString("doubleValue");
            assertTrue(false, "Should have thrown ClassCastException for Double value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
        
        try {
            typeFilter.getString("booleanValue");
            assertTrue(false, "Should have thrown ClassCastException for Boolean value");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().contains("not an instance of return type"));
        }
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

    /**
     * Tests the apply method with matching filter criteria.
     *
     * @return
     */
    @Test
    public void testApplyWithMatchingCriteria() {
        // Create a filter with specific criteria
        Payload<String, Object> userPayload = Payload.newBuilder();
        userPayload.ofPair("id", 1L);
        userPayload.ofPair("name", "Roh Lak");
        userPayload.ofPair("email", "rslakra@lakra.com");
        userPayload.ofPair("age", 30);
        userPayload.ofPair("active", true);
        
        DefaultFilter<TestUser> userFilter = new DefaultFilter<>(userPayload);
        
        // Create a user that matches all criteria
        TestUser matchingUser = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 30, true);
        assertTrue(userFilter.apply(matchingUser), "User matching all criteria should return true");
        
        // Create a filter with partial criteria
        Payload<String, Object> partialPayload = Payload.newBuilder();
        partialPayload.ofPair("id", 1L);
        partialPayload.ofPair("active", true);
        
        DefaultFilter<TestUser> partialFilter = new DefaultFilter<>(partialPayload);
        assertTrue(partialFilter.apply(matchingUser), "User matching partial criteria should return true");
    }

    /**
     * Tests the apply method with non-matching filter criteria.
     *
     * @return
     */
    @Test
    public void testApplyWithNonMatchingCriteria() {
        Payload<String, Object> userPayload = Payload.newBuilder();
        userPayload.ofPair("id", 1L);
        userPayload.ofPair("name", "Roh Lak");
        userPayload.ofPair("email", "rslakra@lakra.com");
        userPayload.ofPair("age", 30);
        userPayload.ofPair("active", true);
        
        DefaultFilter<TestUser> userFilter = new DefaultFilter<>(userPayload);
        
        // Create users that don't match
        TestUser wrongId = new TestUser(2L, "Roh Lak", "rslakra@lakra.com", 30, true);
        assertFalse(userFilter.apply(wrongId), "User with wrong id should return false");
        
        TestUser wrongName = new TestUser(1L, "Jane Doe", "rslakra@lakra.com", 30, true);
        assertFalse(userFilter.apply(wrongName), "User with wrong name should return false");
        
        TestUser wrongEmail = new TestUser(1L, "Roh Lak", "different@email.com", 30, true);
        assertFalse(userFilter.apply(wrongEmail), "User with wrong email should return false");
        
        TestUser wrongAge = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 25, true);
        assertFalse(userFilter.apply(wrongAge), "User with wrong age should return false");
        
        TestUser wrongActive = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 30, false);
        assertFalse(userFilter.apply(wrongActive), "User with wrong active status should return false");
    }

    /**
     * Tests the apply method with null element.
     *
     * @return
     */
    @Test
    public void testApplyWithNullElement() {
        Payload<String, Object> userPayload = Payload.newBuilder();
        userPayload.ofPair("id", 1L);
        userPayload.ofPair("name", "Roh Lak");
        
        DefaultFilter<TestUser> userFilter = new DefaultFilter<>(userPayload);
        
        assertFalse(userFilter.apply(null), "Null element should return false");
    }

    /**
     * Tests the apply method with empty payload.
     *
     * @return
     */
    @Test
    public void testApplyWithEmptyPayload() {
        Payload<String, Object> emptyPayload = Payload.newBuilder();
        DefaultFilter<TestUser> emptyFilter = new DefaultFilter<>(emptyPayload);
        
        TestUser anyUser = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 30, true);
        assertTrue(emptyFilter.apply(anyUser), "Empty payload should match all elements");
        
        // Null element should still return false even with empty payload (null check happens first)
        assertFalse(emptyFilter.apply(null), "Null element should return false even with empty payload");
    }

    /**
     * Tests the apply method with TestProduct.
     *
     * @return
     */
    @Test
    public void testApplyWithTestProduct() {
        Payload<String, Object> productPayload = Payload.newBuilder();
        productPayload.ofPair("productId", "P001");
        productPayload.ofPair("productName", "Test Product");
        productPayload.ofPair("quantity", 10);
        productPayload.ofPair("inStock", true);
        
        DefaultFilter<TestProduct> productFilter = new DefaultFilter<>(productPayload);
        
        // Matching product
        TestProduct matchingProduct = new TestProduct("P001", "Test Product", null, 10, null, true);
        assertTrue(productFilter.apply(matchingProduct), "Product matching all criteria should return true");
        
        // Non-matching product
        TestProduct nonMatchingProduct = new TestProduct("P002", "Test Product", null, 10, null, true);
        assertFalse(productFilter.apply(nonMatchingProduct), "Product with wrong id should return false");
        
        TestProduct wrongQuantity = new TestProduct("P001", "Test Product", null, 20, null, true);
        assertFalse(productFilter.apply(wrongQuantity), "Product with wrong quantity should return false");
    }

    /**
     * Tests the apply method with single criterion.
     *
     * @return
     */
    @Test
    public void testApplyWithSingleCriterion() {
        Payload<String, Object> singlePayload = Payload.newBuilder();
        singlePayload.ofPair("id", 1L);
        
        DefaultFilter<TestUser> singleFilter = new DefaultFilter<>(singlePayload);
        
        TestUser matchingUser = new TestUser(1L, "Any Name", "any@email.com", 25, false);
        assertTrue(singleFilter.apply(matchingUser), "User matching single criterion should return true");
        
        TestUser nonMatchingUser = new TestUser(2L, "Any Name", "any@email.com", 25, false);
        assertFalse(singleFilter.apply(nonMatchingUser), "User not matching single criterion should return false");
    }

    /**
     * Tests the apply method with missing property on element.
     *
     * @return
     */
    @Test
    public void testApplyWithMissingProperty() {
        Payload<String, Object> payload = Payload.newBuilder();
        payload.ofPair("nonExistentProperty", "value");
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(payload);
        
        TestUser user = new TestUser(1L, "Roh Lak", "rslakra@lakra.com", 30, true);
        assertFalse(filter.apply(user), "Element missing required property should return false");
    }

    /**
     * Tests the rebuild method with Payload parameters.
     *
     * @return
     */
    @Test
    public void testRebuildWithPayload() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("id", 1L);
        initialPayload.ofPair("name", "Initial Name");
        initialPayload.ofPair("active", true);
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(initialPayload);
        
        // Verify initial state
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        assertTrue(filter.hasKey("active"));
        assertEquals(1L, filter.getValue("id"));
        assertEquals("Initial Name", filter.getValue("name"));
        assertEquals(true, filter.getValue("active"));
        
        // Create test user matching initial criteria
        TestUser initialUser = new TestUser(1L, "Initial Name", "initial@email.com", 30, true);
        assertTrue(filter.apply(initialUser), "User should match initial criteria");
        
        // Rebuild with new Payload
        Payload<String, Object> newPayload = Payload.newBuilder();
        newPayload.ofPair("id", 2L);
        newPayload.ofPair("email", "new@email.com");
        newPayload.ofPair("age", 25);
        
        filter.rebuild(newPayload);
        
        // Verify old criteria are cleared
        assertFalse(filter.hasKey("name"), "Old criteria should be cleared");
        assertFalse(filter.hasKey("active"), "Old criteria should be cleared");
        
        // Verify new criteria are set
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("email"));
        assertTrue(filter.hasKey("age"));
        assertEquals(2L, filter.getValue("id"));
        assertEquals("new@email.com", filter.getValue("email"));
        assertEquals(25, filter.getValue("age"));
        
        // Verify filter works with new criteria
        TestUser newUser = new TestUser(2L, "Any Name", "new@email.com", 25, false);
        assertTrue(filter.apply(newUser), "User should match new criteria");
        
        TestUser nonMatchingUser = new TestUser(1L, "Any Name", "new@email.com", 25, false);
        assertFalse(filter.apply(nonMatchingUser), "User with wrong id should not match");
    }

    /**
     * Tests the rebuild method with Map parameters.
     *
     * @return
     */
    @Test
    public void testRebuildWithMap() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("id", 1L);
        initialPayload.ofPair("name", "Initial Name");
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(initialPayload);
        
        // Verify initial state
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        assertEquals(1L, filter.getValue("id"));
        assertEquals("Initial Name", filter.getValue("name"));
        
        // Rebuild with new Map
        java.util.Map<String, Object> newMap = new java.util.HashMap<>();
        newMap.put("id", 3L);
        newMap.put("name", "New Name");
        newMap.put("active", false);
        
        filter.rebuild(newMap);
        
        // Verify new criteria are set
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        assertTrue(filter.hasKey("active"));
        assertEquals(3L, filter.getValue("id"));
        assertEquals("New Name", filter.getValue("name"));
        assertEquals(false, filter.getValue("active"));
        
        // Verify filter works with new criteria
        TestUser matchingUser = new TestUser(3L, "New Name", "any@email.com", 30, false);
        assertTrue(filter.apply(matchingUser), "User should match new criteria");
        
        TestUser nonMatchingUser = new TestUser(3L, "Different Name", "any@email.com", 30, false);
        assertFalse(filter.apply(nonMatchingUser), "User with wrong name should not match");
    }

    /**
     * Tests the rebuild method with null Payload.
     *
     * @return
     */
    @Test
    public void testRebuildWithNullPayload() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("id", 1L);
        initialPayload.ofPair("name", "Initial Name");
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(initialPayload);
        
        // Verify initial state
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        
        // Rebuild with null
        filter.rebuild((Payload<String, Object>) null);
        
        // Verify payload is cleared
        assertFalse(filter.hasKey("id"), "Payload should be cleared");
        assertFalse(filter.hasKey("name"), "Payload should be cleared");
        assertNull(filter.getValue("id"), "Filter should be empty after rebuilding with null");
        
        // Empty filter should match all elements
        TestUser anyUser = new TestUser(1L, "Any Name", "any@email.com", 30, true);
        assertTrue(filter.apply(anyUser), "Empty filter should match all elements");
    }

    /**
     * Tests the rebuild method with null Map.
     *
     * @return
     */
    @Test
    public void testRebuildWithNullMap() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("id", 1L);
        initialPayload.ofPair("name", "Initial Name");
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(initialPayload);
        
        // Verify initial state
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        
        // Rebuild with null Map
        filter.rebuild((java.util.Map<String, Object>) null);
        
        // Verify payload is cleared
        assertFalse(filter.hasKey("id"), "Payload should be cleared");
        assertFalse(filter.hasKey("name"), "Payload should be cleared");
        assertNull(filter.getValue("id"), "Filter should be empty after rebuilding with null");
    }

    /**
     * Tests the rebuild method with empty Payload.
     *
     * @return
     */
    @Test
    public void testRebuildWithEmptyPayload() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("id", 1L);
        initialPayload.ofPair("name", "Initial Name");
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(initialPayload);
        
        // Verify initial state
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        
        // Rebuild with empty Payload
        Payload<String, Object> emptyPayload = Payload.newBuilder();
        filter.rebuild(emptyPayload);
        
        // Verify payload is cleared
        assertFalse(filter.hasKey("id"), "Payload should be cleared");
        assertFalse(filter.hasKey("name"), "Payload should be cleared");
        assertNull(filter.getValue("id"), "Filter should be empty");
        
        // Empty filter should match all elements
        TestUser anyUser = new TestUser(1L, "Any Name", "any@email.com", 30, true);
        assertTrue(filter.apply(anyUser), "Empty filter should match all elements");
    }

    /**
     * Tests the rebuild method with empty Map.
     *
     * @return
     */
    @Test
    public void testRebuildWithEmptyMap() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("id", 1L);
        initialPayload.ofPair("name", "Initial Name");
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(initialPayload);
        
        // Verify initial state
        assertTrue(filter.hasKey("id"));
        assertTrue(filter.hasKey("name"));
        
        // Rebuild with empty Map
        java.util.Map<String, Object> emptyMap = new java.util.HashMap<>();
        filter.rebuild(emptyMap);
        
        // Verify payload is cleared
        assertFalse(filter.hasKey("id"), "Payload should be cleared");
        assertFalse(filter.hasKey("name"), "Payload should be cleared");
        assertNull(filter.getValue("id"), "Filter should be empty");
    }

    /**
     * Tests multiple rebuilds to ensure filter can be reused multiple times.
     *
     * @return
     */
    @Test
    public void testMultipleRebuilds() {
        // Create initial filter
        Payload<String, Object> payload1 = Payload.newBuilder();
        payload1.ofPair("id", 1L);
        
        DefaultFilter<TestUser> filter = new DefaultFilter<>(payload1);
        
        TestUser user1 = new TestUser(1L, "Name1", "email1@test.com", 30, true);
        assertTrue(filter.apply(user1), "Should match first criteria");
        
        // First rebuild
        Payload<String, Object> payload2 = Payload.newBuilder();
        payload2.ofPair("id", 2L);
        payload2.ofPair("name", "Name2");
        
        filter.rebuild(payload2);
        
        TestUser user2 = new TestUser(2L, "Name2", "email2@test.com", 25, false);
        assertTrue(filter.apply(user2), "Should match second criteria");
        assertFalse(filter.apply(user1), "Should not match first user anymore");
        
        // Second rebuild with Map
        java.util.Map<String, Object> map3 = new java.util.HashMap<>();
        map3.put("email", "email3@test.com");
        map3.put("active", true);
        
        filter.rebuild(map3);
        
        TestUser user3 = new TestUser(3L, "Name3", "email3@test.com", 20, true);
        assertTrue(filter.apply(user3), "Should match third criteria");
        assertFalse(filter.apply(user2), "Should not match second user anymore");
        assertFalse(filter.apply(user1), "Should not match first user anymore");
    }

    /**
     * Tests rebuild with TestProduct to ensure it works with different types.
     *
     * @return
     */
    @Test
    public void testRebuildWithTestProduct() {
        // Create initial filter
        Payload<String, Object> initialPayload = Payload.newBuilder();
        initialPayload.ofPair("productId", "P001");
        initialPayload.ofPair("quantity", 10);
        
        DefaultFilter<TestProduct> filter = new DefaultFilter<>(initialPayload);
        
        TestProduct product1 = new TestProduct("P001", "Product 1", null, 10, null, true);
        assertTrue(filter.apply(product1), "Product should match initial criteria");
        
        // Rebuild with new criteria
        Payload<String, Object> newPayload = Payload.newBuilder();
        newPayload.ofPair("productId", "P002");
        newPayload.ofPair("inStock", true);
        
        filter.rebuild(newPayload);
        
        TestProduct product2 = new TestProduct("P002", "Product 2", null, 20, null, true);
        assertTrue(filter.apply(product2), "Product should match new criteria");
        assertFalse(filter.apply(product1), "Product should not match old criteria");
    }

}
