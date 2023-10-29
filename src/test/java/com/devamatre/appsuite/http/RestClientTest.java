package com.devamatre.appsuite.http;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * @author Rohtash Lakra
 * @created 5/24/23 7:28 PM
 */
public class RestClientTest {

    @Test
    public void testGetEntity() {
        RestClient restClient = new RestClientImpl();
        Object response = new Object();
        restClient.getEntity(null, null);
        assertNotNull(response);
    }

}
